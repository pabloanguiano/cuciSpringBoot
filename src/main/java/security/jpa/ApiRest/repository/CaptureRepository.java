package security.jpa.ApiRest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import security.jpa.ApiRest.dto.CaptureDto;
import security.jpa.ApiRest.dto.CaptureReturnDto;
import security.jpa.ApiRest.model.CaptureModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface CaptureRepository extends JpaRepository<CaptureModel, Integer> {

    Boolean existsByApplicationNumber(int applicationNumber);

    Optional<CaptureModel> findByApplicationNumber(Integer applicationNumber);

    @Query("SELECT ("+
            "u.id,"+
            "u.receiptDate ," +
            "u.applicationConcept," +
            "u.applicationNumber,"+
            "u.beneficiaryName,"+
            "fn.identifier ,"+
            "sa.description, "+
            "c.description,"+
             "u.namePackage,"+
            "u.numberDepcon,"+
            "u.depcon,"+
           "u.numberPackage,"+
           "nu.identifier,"+
             "nu.description,"+
             "u.patrimonialRegistration,"+
           "u.paymentComplement,"+
            "u.travelFormat,"+
            "u.paymentMethod,"+
          "u.paymentType," +
            "u.p3e,"+
            "u.project,"+
            "CONCAT('$',u.requestAmount),"+
            "CONCAT('$',u.isrRetention),"+
            "CONCAT('$',u.totalAmountPaid),"+
            "sn.identifier,"+
            "sn.description )"+
            "FROM CaptureModel u JOIN u.typeApplication c  "+
            "JOIN u.NumberUre as nu "+
            "JOIN u.StockNumber sn "+
            "JOIN u.statusApplication sa "+
            "JOIN u.fundApplication as fn "+
            "WHERE u.applicationNumber = :appNumber ")
    CaptureModel findByValuesForCatalos(@Param("appNumber") int appNumber);

    @Query("SELECT new security.jpa.ApiRest.dto.CaptureReturnDto("+
            "u.id,"+
            "u.receiptDate as receiptDateApplication," +
            "u.applicationConcept," +
            "u.applicationNumber,"+
            "u.beneficiaryName,"+
            "fn.id as fundApplication,"+
            "sa.id as descriptionStatusApplication,"+
            "c.id as descriptionTypeApplication,"+
            "u.namePackage,"+
            "u.numberDepcon,"+
            "u.depcon,"+
            "u.numberPackage,"+
            "nu.id as numberUres,"+
            "nu.description as descriptionUre,"+
            "u.patrimonialRegistration,"+
            "u.paymentComplement,"+
            "u.travelFormat,"+
            "u.paymentMethod,"+
            "u.paymentType," +
            "u.p3e,"+
            "u.project,"+
            "u.requestAmount,"+
            "u.isrRetention,"+
            "u.totalAmountPaid,"+
            "sn.id as stockNumber,"+
            "sn.description as nameStock)"+
            "FROM CaptureModel u JOIN u.typeApplication c  "+
            "JOIN u.NumberUre as nu "+
            "JOIN u.StockNumber sn "+
            "JOIN u.statusApplication sa "+
            "JOIN u.fundApplication as fn "+
            "WHERE u.applicationNumber = :appNumber ")
    List<CaptureReturnDto> findByApplicationNumbers(@Param("appNumber") int appNumber);

    @Query("SELECT new security.jpa.ApiRest.dto.CaptureReturnDto("+
            "u.id,"+
            "u.receiptDate as receiptDateApplication," +
            "u.applicationConcept," +
            "u.applicationNumber,"+
            "u.beneficiaryName,"+
            "fn.identifier as fundApplication,"+
            "sa.description as descriptionStatusApplication,"+
            "c.description as descriptionTypeApplication,"+
            "u.namePackage,"+
            "u.numberDepcon,"+
            "u.depcon,"+
            "u.numberPackage,"+
            "nu.identifier as numberUres,"+
            "nu.description as descriptionUre,"+
            "u.patrimonialRegistration,"+
            "u.paymentComplement,"+
            "u.travelFormat,"+
            "u.paymentMethod,"+
            "u.paymentType," +
            "u.p3e,"+
            "u.project,"+
            "CONCAT('$',u.requestAmount),"+
            "CONCAT('$',u.isrRetention),"+
            "CONCAT('$',u.totalAmountPaid),"+
            "sn.identifier as stockNumber,"+
            "sn.description as nameStock)"+
            "FROM CaptureModel u JOIN u.typeApplication c  "+
            "JOIN u.NumberUre as nu "+
            "JOIN u.StockNumber sn "+
            "JOIN u.statusApplication sa "+
            "JOIN u.fundApplication as fn WHERE u.deleteApplication = 0 ")
    Page<CaptureReturnDto> pagesCapture( Pageable pageable);



}













