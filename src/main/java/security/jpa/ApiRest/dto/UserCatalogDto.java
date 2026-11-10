package security.jpa.ApiRest.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserCatalogDto {

    private Byte id;
    @NotBlank(message = " El usuario no puede ser vacío o espacio en blanco")
    private String username;

    @NotBlank(message = " El password no puede ser vacío o espacio en blanco")
    private String password;
    private String type;
    @NotNull(message = " El rol no puede ser nulo")
    private String roles;
    private Integer rolesId;
    @NotBlank(message = " El nombre no puede ser vacío o espacio en blanco")
    private String name;
    private Boolean isValid;
    private Boolean deleteApplication;


    public UserCatalogDto(Byte id, String username, String password, String type, String roles, Integer rolesId, String name, Boolean isValid, Boolean deleteApplication) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.roles = roles;
        this.rolesId = rolesId;
        this.name = name;
        this.isValid = isValid;
        this.deleteApplication = deleteApplication;
    }
}
