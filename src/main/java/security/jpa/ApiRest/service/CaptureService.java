package security.jpa.ApiRest.service;



import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.jpa.ApiRest.dto.CaptureDto;
import security.jpa.ApiRest.dto.CaptureReturnDto;
import security.jpa.ApiRest.dto.catalogsCaptureDto;
import security.jpa.ApiRest.dto.catalogsCaptureElasticDto;
import security.jpa.ApiRest.model.CaptureDocument;
import security.jpa.ApiRest.model.CaptureModel;
import security.jpa.ApiRest.repository.CaptureElasticRepository;
import security.jpa.ApiRest.repository.CaptureRepository;
import security.jpa.ApiRest.repository.SubCatalogCaptureRepository;
import security.jpa.ApiRest.repository.UserRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor // <-- Lombok: Genera el constructor para la inyección de dependencias
public class CaptureService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubCatalogCaptureRepository subCatalogCaptureRepository;
    private final CaptureRepository captureRepository; // Habla con MySQL
    private final CaptureElasticRepository captureElasticRepository; // Habla con Elasticsearch

    @Transactional // <-- Vital: Si algo falla en MySQL, no se manda nada a Elastic
    public CaptureModel registrarOActualizarCaptura(CaptureDto captureDto) {

        // 1. Guardar primero en MySQL. Esto asegura la consistencia de datos y nos da el ID autogenerado.
        CaptureModel captureModelData = new CaptureModel();
        captureModelData.setReceiptDate(captureDto.getReceiptDate());
        captureModelData.setUserCreateApplication(userRepository.findByUsername(captureDto.getUserCreateApplication()).get().getId());
        captureModelData.setUserLastUpdateApplication(userRepository.findByUsername(captureDto.getUserLastUpdateApplication()).get().getId());
        captureModelData.setApplicationNumber(captureDto.getApplicationNumber());
        captureModelData.setIdTypeApplication(captureDto.getIdTypeApplication());
        captureModelData.setIdStatusApplication(captureDto.getIdStatusApplication());
        captureModelData.setPaymentType(captureDto.getPaymentType());
        captureModelData.setPaymentComplement(captureDto.getPaymentComplement());
        captureModelData.setPatrimonialRegistration(captureDto.getPatrimonialRegistration());
        captureModelData.setTravelFormat(captureDto.getTravelFormat());
        captureModelData.setApplicationConcept(captureDto.getApplicationConcept());
        captureModelData.setBeneficiaryName(captureDto.getBeneficiaryName());
        captureModelData.setPaymentMethod(captureDto.getPaymentMethod());
        captureModelData.setP3e(captureDto.getP3e());
        captureModelData.setProject(captureDto.getProject());
        captureModelData.setIdFundApplication(captureDto.getFundApplication());
        captureModelData.setIdStockNumber(captureDto.getStockNumber());
        captureModelData.setIdNumberUre(captureDto.getNumberUre());
        captureModelData.setNumberDepcon(captureDto.getNumberDepcon());
        captureModelData.setDepcon(captureDto.getDepcon());
        captureModelData.setRequestAmount(captureDto.getRequestAmount());
        captureModelData.setIsrRetention(captureDto.getIsrRetention());
        captureModelData.setTotalAmountPaid(captureDto.getTotalAmountPaid());
        captureModelData.setNumberPackage(captureDto.getNumberPackage());
        captureModelData.setNamePackage(captureDto.getNamePackage());
        captureModelData.setDeleteApplication(captureDto.getDeleteApplication());
        captureModelData.setUserDeleted(userRepository.findByUsername(captureDto.getUserDeleted()).get().getId());


        // 2. Construir el documento aplanado usando el patrón Builder de Lombok
        CaptureModel capturaGuardada =
                captureRepository.save(captureModelData);

        CaptureDocument documento =
                convertirADocumento(capturaGuardada);

        captureElasticRepository.save(documento);

        // Retornamos la entidad guardada en MySQL por si tu controlador la necesita
        return capturaGuardada;
    }
    public Page<CaptureDocument> buscarCapturas(String palabraClave, int pagina, int tamano) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pagina, tamano);

        if (palabraClave == null || palabraClave.trim().isEmpty()) {
            return captureElasticRepository.findAll(pageable);
        }

        // Le pasamos la palabra clave a los tres criterios de búsqueda
        return captureElasticRepository.findBySearchTextContaining(
                palabraClave, pageable);
    }

    private CaptureDocument convertirADocumento(CaptureModel captura) {

        CaptureDocument doc = CaptureDocument.builder()
                .id(String.valueOf(captura.getId()))
                .applicationNumber(captura.getApplicationNumber())
                .receiptDate(captura.getReceiptDate())
                .applicationConcept(captura.getApplicationConcept())
                .beneficiaryName(captura.getBeneficiaryName())
                .depcon(captura.getDepcon())
                .namePackage(captura.getNamePackage())
                .numberDepcon(captura.getNumberDepcon())
                .numberPackage(captura.getNumberPackage())
                .paymentMethod(captura.getPaymentMethod())
                .paymentType(captura.getPaymentType())
                .p3e(captura.getP3e())
                .project(captura.getProject())
                .patrimonialRegistration(captura.getPatrimonialRegistration())
                .paymentComplement(captura.getPaymentComplement())
                .travelFormat(captura.getTravelFormat())
                .requestAmount(toDouble(captura.getRequestAmount()))
                .isrRetention(toDouble(captura.getIsrRetention()))
                .totalAmountPaid(toDouble(captura.getTotalAmountPaid()))
                .build();

        if (captura.getIdTypeApplication() != null) {
            subCatalogCaptureRepository
                    .findById(captura.getIdTypeApplication().intValue())
                    .ifPresent(cat -> doc.setTypeApplication(
                            new catalogsCaptureElasticDto(
                                    cat.getId(),
                                    cat.getIdentifier(),
                                    cat.getDescription()
                            )
                    ));
        }

        if (captura.getIdStatusApplication() != null) {
            subCatalogCaptureRepository
                    .findById(captura.getIdStatusApplication().intValue())
                    .ifPresent(cat -> doc.setStatusApplication(
                            new catalogsCaptureElasticDto(
                                    cat.getId(),
                                    cat.getIdentifier(),
                                    cat.getDescription()
                            )
                    ));
        }

        if (captura.getIdNumberUre() != null) {
            subCatalogCaptureRepository
                    .findById(captura.getIdNumberUre().intValue())
                    .ifPresent(cat -> doc.setNumberUre(
                            new catalogsCaptureElasticDto(
                                    cat.getId(),
                                    cat.getIdentifier(),
                                    cat.getDescription()
                            )
                    ));
        }

        if (captura.getIdStockNumber() != null) {
            subCatalogCaptureRepository
                    .findById(captura.getIdStockNumber().intValue())
                    .ifPresent(cat -> doc.setStockNumber(
                            new catalogsCaptureElasticDto(
                                    cat.getId(),
                                    cat.getIdentifier(),
                                    cat.getDescription()
                            )
                    ));
        }

        if (captura.getIdFundApplication() != null) {
            subCatalogCaptureRepository
                    .findById(captura.getIdFundApplication().intValue())
                    .ifPresent(cat -> doc.setFundApplication(
                            new catalogsCaptureElasticDto(
                                    cat.getId(),
                                    cat.getIdentifier(),
                                    cat.getDescription()
                            )
                    ));
        }

        doc.rebuildSearchText();

        return doc;
    }

    private Double toDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }

    private CaptureReturnDto convertirADto(CaptureDocument doc) {

        return new CaptureReturnDto(
                Integer.valueOf(doc.getId()),
                doc.getReceiptDate(),
                doc.getApplicationConcept(),
                doc.getApplicationNumber(),
                doc.getBeneficiaryName(),

                doc.getFundApplication() != null
                        ? doc.getFundApplication().getIdentifier()
                        : null,

                doc.getStatusApplication() != null
                        ? doc.getStatusApplication().getDescription()
                        : null,

                doc.getTypeApplication() != null
                        ? doc.getTypeApplication().getDescription()
                        : null,

                doc.getNamePackage(),
                doc.getNumberDepcon(),
                doc.getDepcon(),
                doc.getNumberPackage(),

                doc.getNumberUre() != null
                        ? doc.getNumberUre().getIdentifier()
                        : null,

                doc.getNumberUre() != null
                        ? doc.getNumberUre().getDescription()
                        : null,

                doc.getPatrimonialRegistration(),
                doc.getPaymentComplement(),
                doc.getTravelFormat(),
                doc.getPaymentMethod(),
                doc.getPaymentType(),
                doc.getP3e(),
                doc.getProject(),

                formatAmount(doc.getRequestAmount()),
                formatAmount(doc.getIsrRetention()),
                formatAmount(doc.getTotalAmountPaid()),

                doc.getStockNumber() != null
                        ? doc.getStockNumber().getIdentifier()
                        : null,

                doc.getStockNumber() != null
                        ? doc.getStockNumber().getDescription()
                        : null
        );
    }

    private String formatAmount(Double value) {
        return value != null
                ? "$" + BigDecimal.valueOf(value).toPlainString()
                : null;
    }

    public Page<CaptureReturnDto> buscarCapturasDto(
            String palabraClave,
            int pagina,
            int tamano) {

        return buscarCapturas(palabraClave, pagina, tamano)
                .map(this::convertirADto);
    }

}
