package za.co.ajk.incident.repository;

import java.util.List;

import za.co.ajk.incident.service.dto.TodoSearchResultDTO;


public interface IncidentCustomRepository {
    
    List<TodoSearchResultDTO> findBySearchTerm(String searchTerm);
}
