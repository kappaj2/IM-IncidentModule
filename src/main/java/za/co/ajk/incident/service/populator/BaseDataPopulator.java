package za.co.ajk.incident.service.populator;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import za.co.ajk.incident.domain.Company;
import za.co.ajk.incident.domain.Country;
import za.co.ajk.incident.domain.Equipment;
import za.co.ajk.incident.domain.Region;
import za.co.ajk.incident.repository.CompanyRepository;
import za.co.ajk.incident.repository.CountryRepository;
import za.co.ajk.incident.repository.EquipmentRepository;
import za.co.ajk.incident.repository.RegionRepository;
import za.co.ajk.incident.service.CompanyService;
import za.co.ajk.incident.service.CountryService;
import za.co.ajk.incident.service.EquipmentService;
import za.co.ajk.incident.service.RegionService;
import za.co.ajk.incident.service.dto.CompanyDTO;
import za.co.ajk.incident.service.dto.CountryDTO;
import za.co.ajk.incident.service.dto.EquipmentDTO;
import za.co.ajk.incident.service.dto.RegionDTO;


/**
 * Class to load basic data for unit testing and QA.
 */
@Profile("dev")
@Service
public class BaseDataPopulator implements CommandLineRunner {
    
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryService countryService;
    
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private RegionService regionService;
    
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private EquipmentService equipmentService;
    
    private Country southAfrica = new Country().countryCode("ZA").countryName("South Africa").regions(null);
    private Country america = new Country().countryCode("US").countryName("United States of America").regions(null);
    private Country newZealand = new Country().countryCode("NZ").countryName("New Zealand").regions(null);
    
    private Region gauteng = new Region().regionCode("REG1").regionName("Gauteng").country(southAfrica);
    private Region kzn = new Region().regionCode("REG2").regionName("Kwazulu Natal").country(southAfrica);
    private Region westernCape = new Region().regionCode("REG3").regionName("Western Cape").country(southAfrica);
    
    private Company ca1_1 = new Company().name("Company One").branchCode("BC1").region(westernCape);
    private Company ca1_2 = new Company().name("Company One").branchCode("BC2").region(westernCape);
    private Company ca1_3 = new Company().name("Company One").branchCode("BC3").region(westernCape);
    
    private Company ca2 = new Company().name("Company Two").region(gauteng);
    private Company ca3 = new Company().name("Company Three").region(gauteng);
    private Company ca4 = new Company().name("Company Four").region(kzn);
    private Company ca5 = new Company().name("Company Five").region(kzn);
    private Company ca6 = new Company().name("Company Six");
    private Company ca7 = new Company().name("Company Seven");
    
    @Override
    public void run(String... args) throws Exception {
        loadBaseCountries();
        loadBaseRegions();
        loadBaseCompanies();
       // loadBaseEquipment();
    }
    
    private void loadBaseCountries() {
        List<CountryDTO> countryList = countryService.findAll();
        if (countryList.size() == 0) {
            countryRepository.save(southAfrica);
            countryRepository.save(america);
            countryRepository.save(newZealand);
        }
        countryRepository.findAll().forEach(System.out::println);
    }
    
    private void loadBaseRegions() {
        List<RegionDTO> regionList = regionService.findAll();
        if (regionList.size() == 0) {
            regionRepository.save(gauteng);
            regionRepository.save(kzn);
            regionRepository.save(westernCape);
        }
    }
    
    private void loadBaseCompanies() {
        List<CompanyDTO> companyList = companyService.findAll();
        if (companyList.size() == 0) {
            
            //  Load a company with branch codes for the same company
            companyRepository.save(ca1_1);
            companyRepository.save(ca1_2);
            companyRepository.save(ca1_3);
            
            //  Load companies without branch codes.
            companyRepository.save(ca2);
            companyRepository.save(ca3);
            companyRepository.save(ca4);
            companyRepository.save(ca5);
            companyRepository.save(ca6);
            companyRepository.save(ca7);

        }
        companyRepository.findAll().forEach(System.out::println);
    }
    
    private void loadBaseEquipment() {
        List<EquipmentDTO> equipmentList = equipmentService.findAll();
        if (equipmentList.size() == 0) {
            
            equipmentRepository.save(new Equipment()
                .company(ca1_1)
                .equipmentId(Integer.valueOf(1))
                .dateAdded(Instant.now())
                .addedBy("SYSTEM"));
            equipmentRepository.save(new Equipment()
                .company(ca1_1)
                .equipmentId(Integer.valueOf(2))
                .dateAdded(Instant.now())
                .addedBy("SYSTEM"));
            equipmentRepository.save(new Equipment()
                .company(ca1_2)
                .equipmentId(Integer.valueOf(3))
                .dateAdded(Instant.now())
                .addedBy("SYSTEM"));
            equipmentRepository.save(new Equipment()
                .company(ca1_2)
                .equipmentId(Integer.valueOf(4))
                .dateAdded(Instant.now())
                .addedBy("SYSTEM"));
            
            equipmentRepository.save(new Equipment()
                .company(ca2)
                .equipmentId(Integer.valueOf(21))
                .dateAdded(Instant.now())
                .addedBy("SYSTEM"));
            equipmentRepository.save(new Equipment()
                .company(ca2)
                .equipmentId(Integer.valueOf(22))
                .dateAdded(Instant.now())
                .addedBy("SYSTEM"));
            equipmentRepository.save(new Equipment()
                .company(ca2)
                .equipmentId(Integer.valueOf(23))
                .dateAdded(Instant.now())
                .addedBy("SYSTEM"));
            
            equipmentRepository.findAll().stream().forEach(System.out::print);
        }
    }
}
