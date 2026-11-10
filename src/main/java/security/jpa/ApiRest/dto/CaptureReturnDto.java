package security.jpa.ApiRest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaptureReturnDto {
    private Integer id;
    private String receiptDateApplication;
    private String applicationConcept;
    private Integer applicationNumber;
    private String beneficiaryName;
    Object fundApplication;
    Object descriptionStatusApplication;
    Object descriptionTypeApplication;
    private String namePackage;
    private Integer numberDepcon;
    private String depcon;
    private Integer numberPackage;
    Object numberUres;
    private String descriptionUre;
    private Boolean patrimonialRegistration;
    private Boolean paymentComplement;
    private Boolean travelFormat;
    private String paymentMethod;
    private String paymentType;
    private Integer p3e;
    private Integer project;
    private Object requestAmount;
    private Object isrRetention;
    private Object totalAmountPaid;
    Object stockNumber;
    private String nameStock;

    public CaptureReturnDto(Integer id, String receiptDateApplication, String applicationConcept, Integer applicationNumber, String beneficiaryName, Object fundApplication, Object descriptionStatusApplication, Object descriptionTypeApplication, String namePackage, Integer numberDepcon, String depcon, Integer numberPackage, Object numberUres, String descriptionUre, Boolean patrimonialRegistration, Boolean paymentComplement, Boolean travelFormat, String paymentMethod, String paymentType, Integer p3e, Integer project, Object requestAmount, Object isrRetention, Object totalAmountPaid, Object stockNumber, String nameStock) {
        this.id = id;
        this.receiptDateApplication = receiptDateApplication;
        this.applicationConcept = applicationConcept;
        this.applicationNumber = applicationNumber;
        this.beneficiaryName = beneficiaryName;
        this.fundApplication = fundApplication;
        this.descriptionStatusApplication = descriptionStatusApplication;
        this.descriptionTypeApplication = descriptionTypeApplication;
        this.namePackage = namePackage;
        this.numberDepcon = numberDepcon;
        this.depcon = depcon;
        this.numberPackage = numberPackage;
        this.numberUres = numberUres;
        this.descriptionUre = descriptionUre;
        this.patrimonialRegistration = patrimonialRegistration;
        this.paymentComplement = paymentComplement;
        this.travelFormat = travelFormat;
        this.paymentMethod = paymentMethod;
        this.paymentType = paymentType;
        this.p3e = p3e;
        this.project = project;
        this.requestAmount = requestAmount;
        this.isrRetention = isrRetention;
        this.totalAmountPaid = totalAmountPaid;
        this.stockNumber = stockNumber;
        this.nameStock = nameStock;
    }
}
