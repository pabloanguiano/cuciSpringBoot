package security.jpa.ApiRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.jpa.ApiRest.dto.CatalogsDto;
import security.jpa.ApiRest.model.CatalogRolesUsersModel;

import java.util.List;

public interface CatalogsRepository extends JpaRepository<CatalogRolesUsersModel, Integer> {
    // where type LIKE '%%'
    // Filters by exact match on the "Type" column
 //List<CatalogRolesUsersModel> findByTypeEquals(String type);
    List<CatalogsDto> findByTypeEquals(String type);
}
