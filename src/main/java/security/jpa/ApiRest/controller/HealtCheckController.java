package security.jpa.ApiRest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import security.jpa.ApiRest.model.UserModel;
import security.jpa.ApiRest.repository.CaptureRepository;
import security.jpa.ApiRest.repository.UserRepository;

import java.util.Optional;

@RestController
public class HealtCheckController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CaptureRepository captureRepository;

    @GetMapping("/")
    public ResponseEntity<Integer> healthCheck() {
        //return ResponseEntity.ok("OKey");
       // byte ids = userRepository.findByUsername("User").get().getId();
        //Integer idApp = captureRepository.findByApplicationNumber(100).get().getId();
       Integer idApp=0;
//    if((captureRepository.findById(1).get().getApplicationNumber()).compareTo(99)==0){ // are same
//        idApp=0;
//    } else {idApp=1;}
        if((userRepository.findByUsername("captures").get().getId()).compareTo((byte)2)==0){
            idApp=0;
        } else {idApp=1;}
    return ResponseEntity.ok(idApp);
    }
}
