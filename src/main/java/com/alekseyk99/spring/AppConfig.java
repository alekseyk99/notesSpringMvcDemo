package com.alekseyk99.spring;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;


/**
 * The root application context.
 */
@Import({RepositoryConfig.class})
@Configurable
public class AppConfig {

    /**
     * Setup access to properties via PlaceHolder like @Value("${jdbc.user}")
     * @return PropertyPlaceholderConfigurer 
     */
	@Bean
    public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer()
    {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("application.properties"));
        //ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }
	
}
