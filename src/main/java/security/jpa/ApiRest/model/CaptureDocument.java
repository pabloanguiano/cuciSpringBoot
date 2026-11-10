package security.jpa.ApiRest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import security.jpa.ApiRest.dto.catalogsCaptureElasticDto;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "captures")
public class CaptureDocument {

    @Id
    private String id;

    @Field(type = FieldType.Integer)
    private Integer applicationNumber;

    @Field(type = FieldType.Keyword)
    private String receiptDate;

    @Field(type = FieldType.Text)
    private String applicationConcept;

    @Field(type = FieldType.Text)
    private String beneficiaryName;

    @Field(type = FieldType.Text)
    private String depcon;

    @Field(type = FieldType.Keyword)
    private String namePackage;

    @Field(type = FieldType.Integer)
    private Integer numberDepcon;

    @Field(type = FieldType.Integer)
    private Integer numberPackage;

    @Field(type = FieldType.Keyword)
    private String paymentMethod;

    @Field(type = FieldType.Keyword)
    private String paymentType;

    @Field(type = FieldType.Integer)
    private Integer p3e;

    @Field(type = FieldType.Integer)
    private Integer project;

    @Field(type = FieldType.Boolean)
    private Boolean patrimonialRegistration;

    @Field(type = FieldType.Boolean)
    private Boolean paymentComplement;

    @Field(type = FieldType.Boolean)
    private Boolean travelFormat;

    @Field(type = FieldType.Double)
    private Double requestAmount;

    @Field(type = FieldType.Double)
    private Double isrRetention;

    @Field(type = FieldType.Double)
    private Double totalAmountPaid;

    @Field(type = FieldType.Object)
    private catalogsCaptureElasticDto typeApplication;

    @Field(type = FieldType.Object)
    private catalogsCaptureElasticDto statusApplication;

    @Field(type = FieldType.Object)
    private catalogsCaptureElasticDto numberUre;

    @Field(type = FieldType.Object)
    private catalogsCaptureElasticDto stockNumber;

    @Field(type = FieldType.Object)
    private catalogsCaptureElasticDto fundApplication;

    @Field(type = FieldType.Text)
    private String searchText;

    public void rebuildSearchText() {

        this.searchText = Stream.of(
                        applicationNumber,
                        receiptDate,
                        applicationConcept,
                        beneficiaryName,
                        depcon,
                        namePackage,
                        numberDepcon,
                        numberPackage,
                        paymentMethod,
                        paymentType,
                        p3e,
                        project,
                        requestAmount,
                        isrRetention,
                        totalAmountPaid,
                        getCatalogText(typeApplication),
                        getCatalogText(statusApplication),
                        getCatalogText(numberUre),
                        getCatalogText(stockNumber),
                        getCatalogText(fundApplication)
                )
                .filter(value -> value != null)
                .map(String::valueOf)
                .filter(value -> !value.isBlank())
                .collect(Collectors.joining(" "));
    }

    private String getCatalogText(catalogsCaptureElasticDto catalog) {

        if (catalog == null) {
            return null;
        }

        return Stream.of(
                        catalog.getIdentifier(),
                        catalog.getDescription()
                )
                .filter(value -> value != null && !value.isBlank())
                .collect(Collectors.joining(" "));
    }
}