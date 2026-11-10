package security.jpa.ApiRest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
//@DynamicInsert
@Getter
@Setter
public class UserModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @NotBlank(message = " El usuario no puede ser vacío o espacio en blanco")
    @Column(unique = true)
    private String username;

    @NotBlank(message = " El password no puede ser vacío o espacio en blanco")
    @Column(name = "password")
    private String pass;

    @NotNull(message = " El rol no puede ser nulo")
    private Byte roles;

    @NotBlank(message = " El nombre no puede ser vacío o espacio en blanco")
    private String name;


    private Boolean deleteApplication;


    public UserModel() {
    }

    public UserModel(String username, String pass, Byte roles, String name, Boolean deleteApplication) {
        this.username = username;
        this.pass = pass;
        this.roles = roles;
        this.name = name;
        this.deleteApplication = deleteApplication;
    }

    @ManyToOne
    @JoinColumn(name = "roles", referencedColumnName="idCatalogUser" ,
    insertable = false, updatable = false, nullable = false
            )
    private CatalogRolesUsersModel catalogRolesUsersModel;
    /*
    //lado de uno a muchos
    @OneToMany(mappedBy = "userModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaptureModel> createApplication = new ArrayList<>();


    @OneToMany(mappedBy = "userModelDelete", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaptureModel> deleteApplication = new ArrayList<>();


    @OneToMany(mappedBy = "userModelUpdate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaptureModel> updateApplication = new ArrayList<>();
 */

    @Override
    public String toString() {
        return "UserModel{" +
                "deleteApplication=" + deleteApplication +
                ", name='" + name + '\'' +
               ", roles=" + roles +
                ", pass='" + pass + '\'' +
                ", username='" + username + '\'' +
                ", id=" + id +
                '}';
    }
}



