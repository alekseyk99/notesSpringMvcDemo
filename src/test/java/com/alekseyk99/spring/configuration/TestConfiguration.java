package com.alekseyk99.spring.configuration;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import com.alekseyk99.spring.RepositoryConfig;
import java.util.Properties;

@Configuration
@Import({RepositoryConfig.class})
@PropertySource("classpath:application.properties")
public class TestConfiguration {

    /**
     * Test database properties 
     * @return PropertyPlaceholderConfigurer PlaceHolder with test properties
     */
    @Bean
    public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer()
    {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        Properties properties = new Properties();
        properties.put("jdbc.driverClassName","org.hsqldb.jdbcDriver");
        properties.put("jdbc.url","jdbc:hsqldb:mem:sandboxDb");
        properties.put("jdbc.username","sa");
        properties.put("jdbc.password","");
        properties.put("jpa.generate_ddl","true");
        properties.put("jpa.show_sql","true");
        properties.put("jpa.database_platform","org.hibernate.dialect.HSQLDialect");
        ppc.setProperties(properties);
        return ppc;
    }

}
