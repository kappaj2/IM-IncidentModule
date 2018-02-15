package za.co.ajk.incident.repository.search;

import za.co.ajk.incident.domain.EquipmentActivity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EquipmentActivity entity.
 */
public interface EquipmentActivitySearchRepository extends ElasticsearchRepository<EquipmentActivity, Long> {
}
