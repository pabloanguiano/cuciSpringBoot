package security.jpa.ApiRest.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import security.jpa.ApiRest.dto.CaptureDto;
import security.jpa.ApiRest.dto.CaptureReturnDto;
import security.jpa.ApiRest.model.CaptureDocument;
import security.jpa.ApiRest.model.CaptureModel;
import security.jpa.ApiRest.repository.CaptureElasticRepository;
import security.jpa.ApiRest.repository.CaptureRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class ApplicationService {

    @Autowired
    private  CaptureRepository captureRepository;

    @Autowired
    private CaptureElasticRepository captureElasticRepository;



    public Page<CaptureDocument> getApplications(
            String palabraClave,
            int pagina,
            int tamano) {

        Pageable pageable = PageRequest.of(
                pagina,
                tamano,
                Sort.by(Sort.Direction.ASC, "applicationNumber")
        );

        if (palabraClave == null || palabraClave.isBlank()) {
            return captureElasticRepository.findAll(pageable);
        }

        return captureElasticRepository.findBySearchTextContaining(
                palabraClave.trim(),
                pageable
        );
    }


    @Transactional
    public CaptureModel evaluationNullFilds(int id, CaptureDto captureDto) {
        // iportante, hay que verificar que no tenga variables primitivas en el dto
        //para refactorizar es necesario que dto y modelo coincidan en todo
        // para usar el metodot pacth tenemos que verificar ue campos vienen y que campos no





        CaptureModel captureM = captureRepository.findById(id).orElseThrow(() -> new RuntimeException("no existe solicitud"));

// this evit that specific field not allowed update, i need use felper the end file example
//        String[] ignoredProperties = getIgnoredProperties(captureDto, "applicationNumber", "createdAt", "status");
//        BeanUtils.copyProperties(captureDto, captureM, ignoredProperties);

        // aqui revisa springboot automaticasmente siempre y cuando los campos del dto coincidadn en nombre y tipo con el modelo
        // 2. Copiar automáticamente TODOS los campos que se llaman EXACTAMENTE IGUAL (ignorando los nulos)
        BeanUtils.copyProperties(captureDto, captureM, getNullPropertyNames(captureDto));
// y los que no coinciden hay que realizar de manera manual la validacion
        // 3. Mapear MANUALMENTE solo los campos que cambian de nombre (solo si no vienen nulos)


        if (captureDto.getFundApplication() != null) {
            captureM.setIdFundApplication(captureDto.getFundApplication());
        }
        if (captureDto.getStockNumber() != null) {
            captureM.setIdStockNumber(captureDto.getStockNumber());
        }
        if (captureDto.getNumberUre() != null) {
            captureM.setIdNumberUre(captureDto.getNumberUre());
        }

       // CaptureModel capture = captureRepository.findById(numberApplication).orElseThrow(() -> new RuntimeException("no existe solicitud"));
    return  captureRepository.save(captureM);
    }


    // Método de utilidad para extraer los nombres de campos que vienen nulos desde el controlador
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        return emptyNames.toArray(new String[0]);
    }


    private String[] getIgnoredProperties(Object source, String... restrictedFields) {
        Set<String> emptyNames = new HashSet<>(Arrays.asList(getNullPropertyNames(source)));
        emptyNames.addAll(Arrays.asList(restrictedFields));
        return emptyNames.toArray(new String[0]);
    }


}
