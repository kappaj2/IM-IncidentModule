package za.co.ajk.incident.repository.search;

import za.co.ajk.incident.domain.Incident;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Incident entity.
 */
public interface IncidentSearchRepository extends ElasticsearchRepository<Incident, Long> {
}
