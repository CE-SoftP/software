package StepDefinitions;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@TestConfiguration
@TestPropertySource
public class testConfi {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/css/**")
                        .addResourceLocations("classpath:/static/css/")
                        .addResourceLocations("classpath:/css/");
            }
        };
    }
    public class OracleConfig {


        @Value("${oracle.jdbc.url}")
        private String jdbcUrl;

        @Value("${oracle.jdbc.username}")
        private String jdbcUsername;

        @Value("${oracle.jdbc.password}")
        private String jdbcPassword;

        @Bean
        public DataSource dataSource2() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
            dataSource.setUrl(jdbcUrl);
            dataSource.setUsername(jdbcUsername);
            dataSource.setPassword(jdbcPassword);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate3(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public WebMvcConfigurer webMvcConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addResourceHandlers(ResourceHandlerRegistry registry) {
                    registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/");
                }
            };
        }
        @Bean
        public WebDriver webDriver() {

             return new ChromeDriver();
        }

    }}
