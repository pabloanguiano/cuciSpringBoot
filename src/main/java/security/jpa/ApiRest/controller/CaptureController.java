package security.jpa.ApiRest.controller;


import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import security.jpa.ApiRest.dto.CaptureDto;
import security.jpa.ApiRest.dto.CaptureReturnDto;
import security.jpa.ApiRest.model.CaptureModel;
import security.jpa.ApiRest.model.SubCatalogCaptureModel;
import security.jpa.ApiRest.repository.CaptureRepository;
import security.jpa.ApiRest.repository.UserRepository;
import security.jpa.ApiRest.service.ApplicationService;
import security.jpa.ApiRest.service.CaptureService;
import security.jpa.ApiRest.service.HelperMappingDto;
import security.jpa.ApiRest.util.ValidationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CaptureController {


    @Autowired
    private CaptureRepository captureRepository;

    @Autowired
    private ApplicationService applicationService;

//    @Autowired
  //  private UserRepository userRepository;

    @Autowired
    private CaptureService captureService;



    public CaptureController( CaptureRepository captureRepository) {

        this.captureRepository = captureRepository;
    }
  /*  @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURE', 'CONSULT')")
    @GetMapping("/applications")
    public Page<CaptureReturnDto> getAllApplication(){
        return applicationService.getApplications(0, 10);
    } */
    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURE', 'CONSULT')")
    @GetMapping("/applications")
    public ResponseEntity<Page<CaptureReturnDto>> getApplications(// Pageable pageable
            @RequestParam int page,
            @RequestParam int size,
                                                                  @RequestParam(required = false) String search
    ) {

        Page<CaptureReturnDto> applications =
                captureService.buscarCapturasDto(search, page, size);

        return ResponseEntity.ok(applications);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURE')")
    @GetMapping("/applications/{applicationNumber}")
    public List<CaptureReturnDto> getApplicationById(@PathVariable int applicationNumber){
        return captureRepository.findByApplicationNumbers(applicationNumber);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURE')")
    @PostMapping("/createApplication")
 // public Page<CaptureReturnDto> newApplication(@RequestBody CaptureDto captureDto, BindingResult bindingResult){
   public ResponseEntity<Object> newApplication(@RequestBody CaptureDto captureDto, BindingResult bindingResult){

      ResponseEntity<Object> errorResponse = ValidationUtil.mapValidationErrors(bindingResult, captureDto.getId());
      if (errorResponse != null) {
          return errorResponse;
      }

      if (captureRepository.existsByApplicationNumber(captureDto.getApplicationNumber())) {
          Map<String, Object> response = new HashMap<>();
          response.put("TypeRequest", "Post");
          response.put("message", "El número de solicitud " + captureDto.getApplicationNumber() + " ya existe");
          response.put("isValid", false);

          return ResponseEntity.status(400).body(response);
      }


        captureService.registrarOActualizarCaptura(captureDto);

     /*   CaptureModel captureModelData = new CaptureModel();
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


     captureRepository.save(captureModelData);

      */

      Map<String, Object> response = new HashMap<>();
      response.put("TypeRequest", "Post");
      response.put("isValid", true);

      return ResponseEntity.status(201).body(response);


  }

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURE')")
    @PatchMapping("/updateApplication/{id}")
    public ResponseEntity<Object> updateApp(@PathVariable int id, @RequestBody CaptureDto captureDto){


      // Integer idApp = captureRepository.findByApplicationNumber(captureDto.getApplicationNumber()).get().getId();
      if((captureRepository.findById(captureDto.getId()).get().getApplicationNumber()).compareTo(captureDto.getApplicationNumber())==0){ // are same

              applicationService.evaluationNullFilds(id, captureDto);
              Map<String, Object> response = new HashMap<>();
              response.put("TypeRequest", "Patch");
              response.put("isValid", true);
              response.put("applications", applicationService.getApplications("ss",0, 10));
              return ResponseEntity.status(201).body(response);

      } else {
          if (!captureRepository.existsByApplicationNumber(captureDto.getApplicationNumber())) {

              applicationService.evaluationNullFilds(id, captureDto);
              Map<String, Object> response = new HashMap<>();
              response.put("TypeRequest", "Patch");
              response.put("isValid", true);
              response.put("applications", applicationService.getApplications("",0, 10));
              return ResponseEntity.status(201).body(response);
          }

          Map<String, Object> response = new HashMap<>();
          response.put("TypeRequest", "Patch");
          response.put("message", "El número de solicitud " + captureDto.getApplicationNumber() + " ya existe");
          response.put("isValid", false);
          response.put("applications", applicationService.getApplications("",0, 10));
          return ResponseEntity.status(400).body(response);
      }
      }






}
