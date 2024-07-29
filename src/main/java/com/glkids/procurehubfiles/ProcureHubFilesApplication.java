package com.glkids.procurehubfiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProcureHubFilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcureHubFilesApplication.class, args);
    }

}
