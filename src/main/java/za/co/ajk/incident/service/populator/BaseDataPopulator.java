package za.co.ajk.incident.service.populator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import za.co.ajk.incident.domain.Company;
import za.co.ajk.incident.domain.Country;
import za.co.ajk.incident.repository.CompanyRepository;
import za.co.ajk.incident.repository.CountryRepository;
import za.co.ajk.incident.service.CompanyService;
import za.co.ajk.incident.service.CountryService;
import za.co.ajk.incident.service.dto.CompanyDTO;
import za.co.ajk.incident.service.dto.CountryDTO;


@Service
public class BaseDataPopulator implements CommandLineRunner {
    
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryService countryService;
    
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyService companyService;
    
    @Override
    public void run(String... args) throws Exception {
        loadBaseCountries();
        loadBaseCompanies();
    }
    
    private void loadBaseCountries() {
        List<CountryDTO> countryList = countryService.findAll();
        if(countryList.size() == 0){
            countryRepository.save(new Country().countryCode("ZA").countryName("South Africa").regions(null));
            countryRepository.save(new Country().countryCode("US").countryName("United States of America").regions(null));
            countryRepository.save(new Country().countryCode("NZ").countryName("New Zealand").regions(null));
        }
        countryRepository.findAll().forEach(System.out::println);
    }
    
    private void loadBaseCompanies(){
        List<CompanyDTO> companyList = companyService.findAll();
        if(companyList.size() == 0){
            companyRepository.save(new Company().companyCode("C1").companyName("Company One"));
        }
        companyRepository.findAll().forEach(System.out::println);
    }
}
