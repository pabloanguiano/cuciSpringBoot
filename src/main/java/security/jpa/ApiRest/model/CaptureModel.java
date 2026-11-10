package security.jpa.ApiRest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "capture")
@Getter
@Setter
public class CaptureModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "La fecha de solicitudpuede no debe ser vacio")
    private String receiptDate;

    private Byte userCreateApplication;
    private Byte userLastUpdateApplication;

    @NotNull(message = "El número de solicitud no puede ser vacio")
    @Positive(message = "Debe ser un número mayor a cero")
    private Integer applicationNumber;
    private Short idTypeApplication;
    private Short idStatusApplication;
    private String paymentType;
    private Boolean paymentComplement;
    private Boolean patrimonialRegistration;
    private Boolean travelFormat;
    private String applicationConcept;
    private String beneficiaryName;
    private String paymentMethod;
    private Integer p3e;
    private Integer project;
    private Short idFundApplication;
    private Short idStockNumber;
    private Short idNumberUre;
    private Integer numberDepcon;
    private String depcon;
    @Column(name = "requestAmount", precision = 15, scale = 2)
    private BigDecimal requestAmount;

    @Column(name = "isrRetention", precision = 15, scale = 2)
    private BigDecimal isrRetention;

    @Column(name = "totalAmountPaid", precision = 15, scale = 2)
    private BigDecimal totalAmountPaid;
    private Integer numberPackage;
    private String namePackage;
    private Byte deleteApplication;
    private Byte userDeleted;

    public CaptureModel() {
    }

    public CaptureModel(String receiptDate, Byte userCreateApplication, Byte userLastUpdateApplication, Integer applicationNumber, Short idTypeApplication, Short idStatusApplication, String paymentType, Boolean paymentComplement, Boolean patrimonialRegistration, Boolean travelFormat, String applicationConcept, String beneficiaryName, String paymentMethod, Integer p3e, Integer project, Short idFundApplication, Short idStockNumber, Short idNumberUre, Integer numberDepcon, String depcon, BigDecimal requestAmount, BigDecimal isrRetention, BigDecimal totalAmountPaid, Integer numberPackage, String namePackage, Byte deleteApplication, Byte userDeleted) {
        this.receiptDate = receiptDate;
        this.userCreateApplication = userCreateApplication;
        this.userLastUpdateApplication = userLastUpdateApplication;
        this.applicationNumber = applicationNumber;
        this.idTypeApplication = idTypeApplication;
        this.idStatusApplication = idStatusApplication;
        this.paymentType = paymentType;
        this.paymentComplement = paymentComplement;
        this.patrimonialRegistration = patrimonialRegistration;
        this.travelFormat = travelFormat;
        this.applicationConcept = applicationConcept;
        this.beneficiaryName = beneficiaryName;
        this.paymentMethod = paymentMethod;
        this.p3e = p3e;
        this.project = project;
        this.idFundApplication = idFundApplication;
        this.idStockNumber = idStockNumber;
        this.idNumberUre = idNumberUre;
        this.numberDepcon = numberDepcon;
        this.depcon = depcon;
        this.requestAmount = requestAmount;
        this.isrRetention = isrRetention;
        this.totalAmountPaid = totalAmountPaid;
        this.numberPackage = numberPackage;
        this.namePackage = namePackage;
        this.deleteApplication = deleteApplication;
        this.userDeleted = userDeleted;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userCreateApplication", referencedColumnName = "id", insertable = false, updatable = false, columnDefinition = "SMALL")
    private UserModel userModelCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userLastUpdateApplication", referencedColumnName = "id", insertable = false, updatable = false, columnDefinition = "SMALL")
    private UserModel userModelUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userDeleted", referencedColumnName = "id", insertable = false, updatable = false, columnDefinition = "SMALL")
    private UserModel userModelDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idStatusApplication", referencedColumnName = "id", insertable = false, updatable = false, columnDefinition = "SMALL")
    private SubCatalogCaptureModel statusApplication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTypeApplication", referencedColumnName = "id", insertable = false, updatable = false, columnDefinition = "SMALL")
    private SubCatalogCaptureModel typeApplication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFundApplication", referencedColumnName = "id", insertable = false, updatable = false, columnDefinition = "SMALL")
    private SubCatalogCaptureModel fundApplication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idNumberUre", referencedColumnName = "id", insertable = false, updatable = false, columnDefinition = "SMALL")
    private SubCatalogCaptureModel NumberUre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idStockNumber", referencedColumnName = "id", insertable = false, updatable = false, columnDefinition = "SMALL")
    private SubCatalogCaptureModel StockNumber;

    @Override
    public String toString() {
        return "CaptureModel{" +
                "receiptDate='" + receiptDate + '\'' +
                ", userCreateApplication=" + userCreateApplication +
                ", userLastUpdateApplication=" + userLastUpdateApplication +
                ", applicationNumber=" + applicationNumber +
                ", idTypeApplication=" + idTypeApplication +
                ", idStatusApplication=" + idStatusApplication +
                ", paymentType='" + paymentType + '\'' +
                ", paymentComplement=" + paymentComplement +
                ", patrimonialRegistration=" + patrimonialRegistration +
                ", travelFormat=" + travelFormat +
                ", applicationConcept='" + applicationConcept + '\'' +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", p3e=" + p3e +
                ", project=" + project +
                ", idFundApplication=" + idFundApplication +
                ", idStockNumber=" + idStockNumber +
                ", idNumberUre=" + idNumberUre +
                ", numberDepcon=" + numberDepcon +
                ", depcon='" + depcon + '\'' +
                ", requestAmount=" + requestAmount +
                ", isrRetention=" + isrRetention +
                ", totalAmountPaid=" + totalAmountPaid +
                ", numberPackage=" + numberPackage +
                ", namePackage='" + namePackage + '\'' +
                ", deleteApplication=" + deleteApplication +
                ", userDeleted=" + userDeleted +
                '}';
    }
}



   /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userCreateApplication")
    private UserModel userModel;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userLastUpdateApplication")
    private UserModel userModelUpdate;
*/
    /*
    // Muchos a muchos
    @ManyToMany(mappedBy = "statusCapture")
    private Set<SubCatalogCaptureModel> subStatusCapture = new HashSet<>();
     */
/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeApplication", referencedColumnName="id")
    private SubCatalogCaptureModel idTypeApplication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statusApplication", referencedColumnName="id")
    private SubCatalogCaptureModel idStatusApplication;
       */



