package org.credo.labs.coindemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.credo.labs.coindemo.repository")
@EntityScan(basePackages = "org.credo.labs.coindemo.entity")
public class CoinDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoinDemoApplication.class, args);
    }


}
