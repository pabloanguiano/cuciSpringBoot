package security.jpa.ApiRest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "catalogs_model")
@Getter
@Setter
public class CatalogsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCatalog")
    private short id;

    private String description;

    public CatalogsModel() {
    }

    public CatalogsModel( String description) {

        this.description = description;
    }


    @Override
    public String toString() {
        return "CatalogsModel{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
