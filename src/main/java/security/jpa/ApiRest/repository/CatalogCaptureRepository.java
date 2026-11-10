package security.jpa.ApiRest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import security.jpa.ApiRest.dto.catalogsCaptureDto;
import security.jpa.ApiRest.model.CatalogCaptureModel;

import java.util.List;

public interface CatalogCaptureRepository extends JpaRepository<CatalogCaptureModel, Short> {



}
