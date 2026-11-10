package security.jpa.ApiRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import security.jpa.ApiRest.dto.UserCatalogDto;
import security.jpa.ApiRest.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Byte> {

Optional<UserModel> findByUsername(String username);

   // boolean existByUsername(String username);
   boolean existsByUsername(String username);

    // This query JOINS both tables and puts the result directly into your DTO
    @Query("SELECT new security.jpa.ApiRest.dto.UserCatalogDto(u.id, u.username,u.pass, c.type, c.category, 0, u.name, true, false)" +
            "FROM UserModel u JOIN u.catalogRolesUsersModel c WHERE u.deleteApplication = false"
    )
    List<UserCatalogDto> findAllUsersWithCatalog();

    @Query("SELECT new security.jpa.ApiRest.dto.UserCatalogDto(u.id, u.username,u.pass, c.type, c.category,0, u.name, true, false)" +
            "FROM UserModel u JOIN u.catalogRolesUsersModel c WHERE u.username = :userName")
    List<UserCatalogDto> findDetailUsersWithCatalog(
            @Param("userName") String userName);

}
