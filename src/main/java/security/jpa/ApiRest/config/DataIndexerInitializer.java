
package security.jpa.ApiRest.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import security.jpa.ApiRest.model.CaptureDocument;
import security.jpa.ApiRest.model.CaptureModel;
import security.jpa.ApiRest.dto.catalogsCaptureElasticDto;
import security.jpa.ApiRest.repository.CaptureElasticRepository;
import security.jpa.ApiRest.repository.CaptureRepository;
import security.jpa.ApiRest.repository.SubCatalogCaptureRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataIndexerInitializer {

    private final CaptureRepository captureRepository;
    private final CaptureElasticRepository captureElasticRepository;
    private final SubCatalogCaptureRepository subCatalogCaptureRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void migrarDatosExistentesAElastic() {

        long registrosEnElastic = captureElasticRepository.count();

        if (registrosEnElastic == 0) {

            log.info(">>>> Elasticsearch está vacío. Iniciando indexación automática desde MySQL...");

            List<CaptureModel> datosMysql = captureRepository.findAll();

            if (datosMysql.isEmpty()) {
                log.info(">>>> No hay registros previos en MySQL para migrar.");
                return;
            }

            List<CaptureDocument> documentos = datosMysql.stream()
                    .map(this::convertirADocumento)
                    .collect(Collectors.toList());

            captureElasticRepository.saveAll(documentos);

            log.info(
                    ">>>> ¡Migración exitosa! Se copiaron {} registros a Elasticsearch.",
                    documentos.size()
            );

        } else {

            log.info(
                    ">>>> Elasticsearch ya cuenta con {} registros. Se omite la migración inicial.",
                    registrosEnElastic
            );
        }
    }

    private CaptureDocument convertirADocumento(CaptureModel captura) {

        // 1. Construimos el documento con los campos principales
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

                .requestAmount(
                        captura.getRequestAmount() != null
                                ? captura.getRequestAmount().doubleValue()
                                : null
                )

                .isrRetention(
                        captura.getIsrRetention() != null
                                ? captura.getIsrRetention().doubleValue()
                                : null
                )

                .totalAmountPaid(
                        captura.getTotalAmountPaid() != null
                                ? captura.getTotalAmountPaid().doubleValue()
                                : null
                )

                .build();

        // 2. Recuperamos los datos de los catálogos

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

        // 3. Construimos el texto global de búsqueda
        doc.rebuildSearchText();

        return doc;
    }
}
