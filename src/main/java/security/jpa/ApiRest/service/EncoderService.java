package security.jpa.ApiRest.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import security.jpa.ApiRest.dto.UserCatalogDto;
import security.jpa.ApiRest.model.UserModel;
import security.jpa.ApiRest.repository.UserRepository;

@Service
public class EncoderService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    //private final PasswordConfig passwordConfig;

    public EncoderService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
     //   this.passwordConfig = passwordConfig;
        this.passwordEncoder = passwordEncoder;
    }

    public UserModel register(UserModel encoder){

        encoder.setPass(passwordEncoder.encode(encoder.getPass()));
       // encoder.setPass(passwordConfig.passwordEncoder.encode(encoder.getPass()));
        return userRepository.save(encoder);

    }

}
