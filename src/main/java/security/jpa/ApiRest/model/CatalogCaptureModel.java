package security.jpa.ApiRest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "catalog_capture")
@Getter
@Setter
public class CatalogCaptureModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCatalogCapture;

    private short catalog;
    private String description;

    public CatalogCaptureModel( short catalog, String description) {

        this.catalog = catalog;
        this.description = description;
    }

    public CatalogCaptureModel() {
    }

    @ManyToOne()
    @JoinColumn(name = "catalog", referencedColumnName = "idCatalog",
    insertable = false, updatable = false, nullable = false,  columnDefinition = "SMALLINT"
    )
    private CatalogsModel catalogsModel;


    //lado de uno a muchos
    @OneToMany(mappedBy = "catalogCaptureModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCatalogCaptureModel> subCatalogs = new ArrayList<>();

//    @ManyToOne()
//    @JoinColumn(name = "idCatalogCapture", referencedColumnName = "catalogCaptureId",
//            insertable = false, updatable = false, nullable = false,  columnDefinition = "INTEGER"
//    )
//    private SubCatalogCaptureModel subCatalogCaptureModel;

    @Override
    public String toString() {
        return "CatalogCaptureModel{" +
                ", catalog=" + catalog +
                ", description='" + description + '\'' +
                '}';
    }
}
