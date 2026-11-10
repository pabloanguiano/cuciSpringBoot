package security.jpa.ApiRest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="roles_user_catalog")
@Getter
@Setter
public class CatalogRolesUsersModel {

    public CatalogRolesUsersModel() {
    }

    public CatalogRolesUsersModel(String category, String type) {
        this.category = category;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCatalogUser")
    private Byte id;


    private String category;


    private String type;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Id")
//    private CatalogsModel catalogsModel;

//@OneToMany(mappedBy = "CatalogRolesUsersModel",cascade = CascadeType.ALL, orphanRemoval = true)
//private List<UserModel> userss;

    @Override
    public String toString() {
        return "CatalogRolesUsersModel{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
