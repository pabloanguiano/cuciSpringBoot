package security.jpa.ApiRest.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CaptureDto {
    private Integer id;
    @NotNull(message = "La fecha de solicitudpuede no debe ser vacio")
    private String receiptDate;
    private String userCreateApplication;
    private String userLastUpdateApplication;

    @NotNull(message = "El número de solicitud no puede ser vacio")
    @Positive(message = "El precio debe ser un número mayor a cero")
    @Column(unique = true)
    private Integer applicationNumber;

    @Positive(message = "Debes seleccionar una tipo de solicitud válido")
    private Short idTypeApplication;

    @Positive(message = "Debes seleccionar una tipo de status válido")
    private Short idStatusApplication;
    private String paymentType;
    private Boolean paymentComplement;
    private Boolean patrimonialRegistration;
    private Boolean travelFormat;
    private String applicationConcept;

    @NotNull(message = "El beneficiario no puede ser nulo")
    private String beneficiaryName;
    private String paymentMethod;
    private Integer p3e;
    private Integer project;
    @Positive(message = "Debes seleccionar una tipo de fondo válido")
    private Short fundApplication;
    private Short stockNumber;
    @Positive(message = "Debes seleccionar una tipo de ure válido")
    private Short numberUre;
    private Integer numberDepcon;
    private String depcon;
    private BigDecimal requestAmount;
    private BigDecimal isrRetention;
    private BigDecimal totalAmountPaid;
    private Integer numberPackage;
    private String namePackage;
    private Byte deleteApplication;
    private String userDeleted;

    public CaptureDto(Integer id, String receiptDate, String userCreateApplication, String userLastUpdateApplication, Integer applicationNumber, Short idTypeApplication, Short idStatusApplication, String paymentType, Boolean paymentComplement, Boolean patrimonialRegistration, Boolean travelFormat, String applicationConcept, String beneficiaryName, String paymentMethod, Integer p3e, Integer project, Short fundApplication, Short stockNumber, Short numberUre, Integer numberDepcon, String depcon, BigDecimal requestAmount, BigDecimal isrRetention, BigDecimal totalAmountPaid, Integer numberPackage, String namePackage, Byte deleteApplication, String userDeleted) {
        this.id = id;
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
        this.fundApplication = fundApplication;
        this.stockNumber = stockNumber;
        this.numberUre = numberUre;
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
}