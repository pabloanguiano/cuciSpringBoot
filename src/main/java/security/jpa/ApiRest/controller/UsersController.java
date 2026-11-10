package security.jpa.ApiRest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import security.jpa.ApiRest.dto.UserCatalogDto;
import security.jpa.ApiRest.handler.DuplicateUsername;
import security.jpa.ApiRest.model.CatalogRolesUsersModel;
import security.jpa.ApiRest.model.UserModel;
import security.jpa.ApiRest.repository.CatalogsRepository;
import security.jpa.ApiRest.repository.UserRepository;
import security.jpa.ApiRest.service.EncoderService;
import security.jpa.ApiRest.service.JpaUserDetailService;
import security.jpa.ApiRest.util.ValidationUtil;
import security.jpa.ApiRest.service.HelperMappingDto;
import java.util.*;


@RestController
@RequestMapping("/api")
public class UsersController {

    private final EncoderService encoderService;
    private final HelperMappingDto helperMappingDto;
    private final JpaUserDetailService jpaUserDetailService;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private CatalogsRepository catalogRepository;


    private CatalogRolesUsersModel catalogRolesUsersModel;


    public UsersController(EncoderService encoderService, UserRepository userRepository,HelperMappingDto helperMappingDto, JpaUserDetailService jpaUserDetailService) {
        this.encoderService = encoderService;
        this.userRepository = userRepository;
        this.helperMappingDto = helperMappingDto;
        this.jpaUserDetailService = jpaUserDetailService;
    }




    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<UserCatalogDto> getUsers(){
        return userRepository.findAllUsersWithCatalog();
    }



    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURE', 'CONSULT')")
    @GetMapping("/users/{userName}")
    public List<UserCatalogDto> geterById(@PathVariable String userName){
       return userRepository.findDetailUsersWithCatalog(userName);
                //userRepository.findByUsername(userName);
      //  return userId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Object> createNewUser(@Valid @RequestBody UserCatalogDto userDTO, BindingResult bindingResult){

        UserModel newUser = new UserModel();
        // 1. Reusable one-line check
        ResponseEntity<Object> errorResponse = ValidationUtil.mapValidationErrors(bindingResult, 0);
        if (errorResponse != null) {
            return errorResponse; // Returns the 400 Bad Request payload automatically
        }


        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new DuplicateUsername("El nombre de usuario '" + userDTO.getUsername() + "' ya está en uso por otro registro.");
        }


        // 1. Crear la instancia de la entidad a partir del DTO

        newUser.setUsername(userDTO.getUsername());
        newUser.setPass(userDTO.getPassword());
        newUser.setRoles(Byte.parseByte(userDTO.getRoles()));
        newUser.setName(userDTO.getName());
        newUser.setDeleteApplication(false);

        // 2. Buscar el catálogo usando el ID plano del DTO
        if (userDTO.getRolesId() != null) {
            CatalogRolesUsersModel catalog = catalogRepository.findById(userDTO.getRolesId())
                    .orElseThrow(() -> new RuntimeException("Catálogo no encontrado con ID: " + userDTO.getRolesId()));

            // 3. Vincular el catálogo a la entidad usuario
            newUser.setCatalogRolesUsersModel(catalog);
        }

        // 4. Encriptar contraseña y guardar al usuario con la relación establecida
        UserModel userCreated = userRepository.save(encoderService.register(newUser));
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);


    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(
            @PathVariable Byte id, @Valid
            @RequestBody UserCatalogDto userDTO,
            BindingResult bindingResult) {

        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        if(userDTO.getDeleteApplication() == true){


        ResponseEntity<Object> errorResponse = ValidationUtil.mapValidationErrors(bindingResult, id.intValue());
        if (errorResponse != null) {
            return errorResponse;
        }

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new DuplicateUsername("El nombre de usuario '" + userDTO.getUsername() + "' ya está en uso por otro registro.");
        }

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setRoles(Byte.parseByte(userDTO.getRoles()));
        existingUser.setPass(userDTO.getPassword());
      existingUser.setName(userDTO.getName());



        UserModel updatedUser = userRepository.save(encoderService.register(existingUser));//userRepository.save(existingUser);

       return ResponseEntity.ok(helperMappingDto.mapToDto(updatedUser));
        }
        else{

            existingUser.setDeleteApplication(true);
            UserModel updatedUser = userRepository.save(encoderService.register(existingUser));//userRepository.save(existingUser);
            return ResponseEntity.ok(helperMappingDto.mapToDto(updatedUser));
        }

    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/validation/{id}")
    public ResponseEntity<Object> updateUserValidation(
            @PathVariable Byte id,
            @Valid @RequestBody UserCatalogDto userDTO,
            BindingResult bindingResult) {

        UserModel existingUser  = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            if((userRepository.findByUsername(userDTO.getUsername()).get().getId()).compareTo(userDTO.getId().byteValue())==0){

                ResponseEntity<Object> errorResponse = ValidationUtil.mapValidationErrors(bindingResult, id.intValue());
                if (errorResponse != null) {
                    return errorResponse;
                }

            } else throw new DuplicateUsername("El nombre de usuario '" + userDTO.getUsername() + "' ya está en uso por otro registro.");
        }



        return ResponseEntity.ok(helperMappingDto.mapToDto(existingUser));





/*
        if (userDTO.getRolesId() != null) {

            if (existingUser.getCatalogRolesUsersModel() == null || !existingUser.getCatalogRolesUsersModel().getId().equals(userDTO.getRolesId())) {
                CatalogRolesUsersModel catalog = catalogRepository.findById(userDTO.getRolesId())
                        .orElseThrow(() -> new RuntimeException("Catálogo no encontrado con ID: " + userDTO.getRolesId()));
                existingUser.setCatalogRolesUsersModel(catalog);
            }
        } else {

            existingUser.setCatalogRolesUsersModel(null);
        }
        */




    }




    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> updateSameUserPatch(@PathVariable Byte id,
                                                           @RequestBody UserCatalogDto userDTO,
                                                           BindingResult bindingResult){


      // UserModel updatedUser = encoderService.register(jpaUserDetailService.updateUserPartial(id, userDTO, bindingResult));
        UserModel updatedUser = jpaUserDetailService.updateUserPartial(id, userDTO, bindingResult);
        return ResponseEntity.ok(helperMappingDto.mapToDto(updatedUser));

    }

    /*@DeleteMapping("/{id}")
    public  ResponseEntity<Void>  deleteUser(@PathVariable Byte id){
        if(!userRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return  ResponseEntity.noContent().build();
    }

     */
}
