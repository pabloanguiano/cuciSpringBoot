package security.jpa.ApiRest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import security.jpa.ApiRest.model.CaptureDocument;

@Repository
public interface CaptureElasticRepository
        extends ElasticsearchRepository<CaptureDocument, String> {

    Page<CaptureDocument> findBySearchTextContaining(
            String searchText,
            Pageable pageable
    );
}