package com.niuzhendong.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableCaching
@SpringBootApplication(scanBasePackages = {"com.niuzhendong.graphql"})
@EntityScan(basePackages = {"com.niuzhendong.graphql"})
@EnableJpaRepositories(basePackages = {"com.niuzhendong.graphql"})
public class TdfBaseSampleJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TdfBaseSampleJpaApplication.class, args);
    }

}
