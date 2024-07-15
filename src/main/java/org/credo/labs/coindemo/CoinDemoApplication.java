package org.credo.labs.coindemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(scanBasePackages = {"com.giant.core", "com.giant.claimapi"})
@SpringBootApplication
public class CoinDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoinDemoApplication.class, args);
    }

}
