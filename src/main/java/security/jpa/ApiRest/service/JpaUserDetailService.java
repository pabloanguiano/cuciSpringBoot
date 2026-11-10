package security.jpa.ApiRest.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import security.jpa.ApiRest.dto.UserCatalogDto;
import security.jpa.ApiRest.model.CatalogRolesUsersModel;
import security.jpa.ApiRest.model.SecurityUser;
import security.jpa.ApiRest.model.UserModel;
import security.jpa.ApiRest.repository.CatalogsRepository;
import security.jpa.ApiRest.repository.UserRepository;

@Service
public class JpaUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
   // private final Validator validator;
    private  final CatalogsRepository catalogsRepository;
   // @Lazy
    private final PasswordEncoder passwordEncoder;

    public JpaUserDetailService(UserRepository userRepository, CatalogsRepository catalogsRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.catalogsRepository = catalogsRepository;
        this.passwordEncoder = passwordEncoder;
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("User no found "+ username));
    }


    @Transactional
    public UserModel updateUserPartial(Byte id, UserCatalogDto dto, BindingResult bindingResult) {

        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        if(dto.getUsername() != null && !dto.getUsername().isBlank()) {
            existingUser.setUsername(dto.getUsername());
        }
        if (dto.getName() != null && !dto.getName().isBlank()) {
            existingUser.setName(dto.getName());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existingUser.setPass(passwordEncoder.encode(dto.getPassword()));
        }
/*
        if (dto.getDeleteApplication() != null && !dto.getDeleteApplication()) {
            existingUser.setDeleteApplication(dto.getDeleteApplication());
        }
*/

        if (dto.getRoles() != null && !dto.getRoles().isBlank()) {
            try {
                existingUser.setRoles(Byte.parseByte(dto.getRoles()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El campo 'roles' debe ser un número válido (ej. \"1\"), recibido: " + dto.getRoles());
            }
        }


        if (dto.getRolesId() != null) {
            if (existingUser.getCatalogRolesUsersModel() == null || !existingUser.getCatalogRolesUsersModel().getId().equals(dto.getRolesId())) {
                CatalogRolesUsersModel catalog = catalogsRepository.findById(dto.getRolesId())
                        .orElseThrow(() -> new RuntimeException("Catálogo no encontrado con ID: " + dto.getRolesId()));
                existingUser.setCatalogRolesUsersModel(catalog);
            }
        } else {
            // Opcional: define si deseas desvincular el catálogo si se envía rolesId como null
            existingUser.setCatalogRolesUsersModel(null);
        }


        return userRepository.save(existingUser);
    }


}
