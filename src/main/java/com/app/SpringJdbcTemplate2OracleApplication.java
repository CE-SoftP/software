package com.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.logging.Logger;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class})
@ComponentScan(basePackages = {"com.app.*", "StepDefinitions"})
public class SpringJdbcTemplate2OracleApplication implements CommandLineRunner {
    Logger logger = Logger.getLogger(getClass().getName());
    public static void main(String [] args) {
        SpringApplication.run(SpringJdbcTemplate2OracleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Application is running.");

    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
