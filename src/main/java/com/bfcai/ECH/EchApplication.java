package com.bfcai.ECH;

import com.bfcai.ECH.dao.CompanionRepository;
import com.bfcai.ECH.dao.PatientRepository;
import com.bfcai.ECH.dto.NbaService;
//import com.bfcai.ECH.symptomchecker.HealthIssueService;
import com.bfcai.ECH.service.CompanionService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@SpringBootApplication
@RequiredArgsConstructor
public class EchApplication implements CommandLineRunner {

    private final PatientRepository patientRepository;

//    private final HealthIssueService healthIssueService;
    private final CompanionService companionService;

    public static void main(String[] args) throws IOException {
//        ClassLoader classLoader= EchApplication.class.getClassLoader();
//
//        File file=new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile());
//        FileInputStream serviceAccount=
//                new FileInputStream(file.getAbsolutePath());
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setDatabaseUrl("https://ech1-67bf0-default-rtdb.firebaseio.com")
//                .build();
//
//        FirebaseApp.initializeApp(options);
        SpringApplication.run(EchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println(this.healthIssueService.getIssueInfoByIssue("Abdominal hernia"));

//        System.out.println(this.companionService.tes(1L));
//        System.out.println(this.patientRepository.findByCompanionId(33L));


    }
}
