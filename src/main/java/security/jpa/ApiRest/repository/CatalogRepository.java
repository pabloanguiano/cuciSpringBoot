package security.jpa.ApiRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.jpa.ApiRest.model.CatalogRolesUsersModel;
import security.jpa.ApiRest.model.CatalogsModel;

public interface CatalogRepository extends JpaRepository<CatalogsModel, Short> {
}
