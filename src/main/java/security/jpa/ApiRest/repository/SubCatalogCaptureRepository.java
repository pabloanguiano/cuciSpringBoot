package security.jpa.ApiRest.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import security.jpa.ApiRest.dto.catalogsCaptureDto;
import security.jpa.ApiRest.model.CatalogCaptureModel;
import security.jpa.ApiRest.model.SubCatalogCaptureModel;
import security.jpa.ApiRest.model.UserModel;

import java.util.List;

public interface SubCatalogCaptureRepository extends JpaRepository<SubCatalogCaptureModel, Integer> {

    List<catalogsCaptureDto> findByCatalogCaptureModel_IdCatalogCapture(int catalogCaptureModelIdCatalogCapture);
}
