package security.jpa.ApiRest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sub_catalog_capture_model")
@Getter
@Setter
public class SubCatalogCaptureModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    //private int catalogCaptureId;
    private String identifier;
    private String description;

    public SubCatalogCaptureModel() {
    }

    // recive father CatalogCaptureModel
    public SubCatalogCaptureModel(CatalogCaptureModel catalogCaptureModel,String identifier, String description) {
        //this.catalogCaptureId = catalogCaptureId;
        this.catalogCaptureModel = catalogCaptureModel;
        this.identifier = identifier;
        this.description = description;
    }
//lado de muchos a uno
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalogCaptureId")
    private CatalogCaptureModel catalogCaptureModel;


    @OneToMany(mappedBy = "idTypeApplication", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaptureModel> TypeApplication = new ArrayList<>();

    @OneToMany(mappedBy = "idStatusApplication", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaptureModel> StatusApplication = new ArrayList<>();

    @OneToMany(mappedBy = "idFundApplication", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaptureModel> fundApplication = new ArrayList<>();

    @OneToMany(mappedBy = "idNumberUre", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaptureModel> NumberUre = new ArrayList<>();

    @OneToMany(mappedBy = "idStockNumber", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaptureModel> StockNumber = new ArrayList<>();

  /*
    //muchos a muchos, se necesita una tabla puente
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "capture_subCatalogs",
            joinColumns = {
                    @JoinColumn(name = "id_subCatalogStatus", referencedColumnName="id")
                   // @JoinColumn(name = "id_subCatalog")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "id_status_Application", referencedColumnName = "statusApplication")
            }

    )
private Set<CaptureModel> statusCapture = new HashSet<>();
    */

    @Override
    public String toString() {
        return "SubCatalogCaptureModel{" +
                ", identifier='" + identifier + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
