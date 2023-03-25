package com.bfcai.ECH;

import com.bfcai.ECH.dao.CompanionRepository;
import com.bfcai.ECH.dao.PatientRepository;
import com.bfcai.ECH.dto.NbaService;
//import com.bfcai.ECH.symptomchecker.HealthIssueService;
import com.bfcai.ECH.service.CompanionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class EchApplication implements CommandLineRunner {

    private final PatientRepository patientRepository;

//    private final HealthIssueService healthIssueService;
//    private final CompanionService companionService;

    public static void main(String[] args) {
        SpringApplication.run(EchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println(this.healthIssueService.getIssueInfoByIssue("Abdominal hernia"));

//        System.out.println(this.companionService.tes(1L));
//        System.out.println(this.patientRepository.findByCompanionId(33L));
    }
}
