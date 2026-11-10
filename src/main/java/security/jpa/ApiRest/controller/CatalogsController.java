package security.jpa.ApiRest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.jpa.ApiRest.dto.CatalogsDto;
import security.jpa.ApiRest.dto.catalogsCaptureDto;
import security.jpa.ApiRest.repository.CatalogsRepository;
import security.jpa.ApiRest.repository.SubCatalogCaptureRepository;

import java.util.List;

@RestController
@RequestMapping("/api/catalogs")
public class CatalogsController {

    @Autowired
    private  CatalogsRepository catalogsRepository;

    @Autowired
    private SubCatalogCaptureRepository subCatalogCaptureRepository;

    public CatalogsController(CatalogsRepository catalogsRepository, SubCatalogCaptureRepository subCatalogCaptureRepository) {

        this.catalogsRepository = catalogsRepository;
        this.subCatalogCaptureRepository = subCatalogCaptureRepository;
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURE')")
    @GetMapping("/{type}")
    public List<CatalogsDto> getCatalog(@PathVariable String type){
      return catalogsRepository.findByTypeEquals(type);
    }



    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURE')")
    @GetMapping("catalogsCapture/{catalogId}")
    public List<catalogsCaptureDto> getCatalogCapture(@PathVariable int catalogId){
    return subCatalogCaptureRepository.findByCatalogCaptureModel_IdCatalogCapture(catalogId);
    }



}
