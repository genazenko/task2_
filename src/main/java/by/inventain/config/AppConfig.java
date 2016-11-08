package by.inventain.config;

import by.inventain.dao.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;


@Configuration
@EnableJpaRepositories(basePackages = "by.inventain.dao")
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Autowired
    Environment environment;

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

    @Bean
    public CompanyDAO getCompanyDAO() {
        return new CompanyDAOImpl();
    }

    @Bean
    public MeetingDAO getMeetingDAO() {
        return new MeetingDAOImpl();
    }
    @Bean
    public EmployeeDAO getEmployeeDAO() {
        return new EmployeeDAOImpl();
    }

    @Bean
    public ObjectMapper jsonObjectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .modules(new JavaTimeModule())
                .build();
    }
}

