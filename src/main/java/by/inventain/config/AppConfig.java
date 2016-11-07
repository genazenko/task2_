package by.inventain.config;

import by.inventain.dao.CompanyDAO;
import by.inventain.dao.CompanyDAOImpl;
import by.inventain.dao.MeetingDAO;
import by.inventain.dao.MeetingDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;


/**
 * Created by 123 on 06.11.2016.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Autowired
    Environment environment;
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }
    @Bean
    public CompanyDAO getCompanyDAO(){
        return new CompanyDAOImpl();
    }
    @Bean
    public MeetingDAO getMeetingDAO(){
        return new MeetingDAOImpl();
    }
}

