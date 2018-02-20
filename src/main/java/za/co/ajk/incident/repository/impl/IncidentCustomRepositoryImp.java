package za.co.ajk.incident.repository.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import za.co.ajk.incident.repository.IncidentCustomRepository;
import za.co.ajk.incident.service.dto.TodoSearchResultDTO;


@Repository
@Component
public class IncidentCustomRepositoryImp implements IncidentCustomRepository {
    
    private static final String SEARCH_TODO_ENTRIES = "SELECT id, title FROM todos t WHERE " +
        "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
        "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%')) " +
        "ORDER BY t.title ASC";
    
    public List<TodoSearchResultDTO> findBySearchTerm(String searchTerm) {
        return null;
    }
//    @Autowired
//    private  NamedParameterJdbcTemplate jdbcTemplate;
//
//    @Transactional(readOnly = true)
//    @Override
//    public List<TodoSearchResultDTO> findBySearchTerm(String searchTerm) {
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("searchTerm", searchTerm);
//
//        List<TodoSearchResultDTO> searchResults = jdbcTemplate.query(SEARCH_TODO_ENTRIES,
//            queryParams,
//            new BeanPropertyRowMapper<>(TodoSearchResultDTO.class)
//        );
//
//        return searchResults;
//    }
//
//
//    @Bean
//    public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
//        return new NamedParameterJdbcTemplate(dataSource);
//    }

}
