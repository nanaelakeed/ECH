package com.bfcai.ECH;

import com.bfcai.ECH.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@RequiredArgsConstructor
public class EchApplication implements CommandLineRunner {

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
        LogUtil.getLogger().info("Ech Application Started !");
    }
}
