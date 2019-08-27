package com.phan.spring_gram_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringGramBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGramBackendApplication.class, args);
    }

}
