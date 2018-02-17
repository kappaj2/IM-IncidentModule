package za.co.ajk.incident.repository.search;

import za.co.ajk.incident.domain.IncidentActivity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IncidentActivity entity.
 */
public interface IncidentActivitySearchRepository extends ElasticsearchRepository<IncidentActivity, Long> {
}
