package security.jpa.ApiRest.service;

import org.springframework.stereotype.Service;
import security.jpa.ApiRest.dto.CaptureDto;
import security.jpa.ApiRest.dto.CaptureReturnDto;
import security.jpa.ApiRest.dto.UserCatalogDto;
import security.jpa.ApiRest.model.CaptureModel;
import security.jpa.ApiRest.model.CatalogRolesUsersModel;
import security.jpa.ApiRest.model.SubCatalogCaptureModel;
import security.jpa.ApiRest.model.UserModel;
import security.jpa.ApiRest.repository.CaptureRepository;

@Service
public class HelperMappingDto {

    public UserCatalogDto mapToDto(UserModel user){
        UserCatalogDto dto = new UserCatalogDto(
                user.getId(),
                user.getUsername(),
                null,
               "",
                (user.getRoles()).toString(),
                Byte.toUnsignedInt(user.getRoles()),
                user.getName(),
                true,
                false
        );
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPass());
        dto.setType("");
        dto.setRoles(user.getRoles().toString());
        dto.setRolesId(Byte.toUnsignedInt(user.getRoles()));
        dto.setName(user.getName());
        dto.setIsValid(true);
        dto.setDeleteApplication(false);
        return dto;
    }

    /*
    public CaptureReturnDto maptoCaptureDto(CaptureModel capture, CaptureRepository captureRepository){
        CaptureReturnDto dtocap = new CaptureReturnDto(
                capture.getApplicationConcept(),
                capture.getApplicationNumber(),
                capture.getBeneficiaryName(),
                capture.getFundApplication(),
                String.valueOf(capture.getTypeApplication()),
              Integer.valueOf("120")
        );
        //CaptureReturnDto dtocap = captureRepository.findByValuesForCatalos();
        return  dtocap;
    }
*/
}

