package com.alekseyk99.spring;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Config class for ORM
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.alekseyk99.spring.repository" })
@EnableTransactionManagement
public class RepositoryConfig {

    @Value("${jdbc.driverClassName}") private String driverClassName;
    @Value("${jdbc.url}") private String url;
    @Value("${jdbc.username}") private String username;
    @Value("${jdbc.password}")private String password;
    
    // type boolean does work with Properties
    @Value("${jpa.generate_ddl}") private String generateDdl;
    @Value("${jpa.show_sql}") private String showSql;
    @Value("${jpa.database_platform}") private String databasePlatform;
    
    
    /**
     * EntityManager Bean
     * 
     * @param dataSource JDBC datasource
     * @return EntityManagerFactory
     */
    @Bean(name="emf")
    public EntityManagerFactory getEntityManagerFactory(DataSource dataSource) {
    	
    	HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(Boolean.parseBoolean(generateDdl));
        vendorAdapter.setShowSql(Boolean.parseBoolean(showSql));
        vendorAdapter.setDatabasePlatform(databasePlatform);
        
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.alekseyk99.spring.model");
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();

        return factory.getObject();
    }
    
    /**
     * Datasource Bean
     * @return DataSource
     */
	@Bean(name="ds")    
    public DataSource getDataSource()
    {
        DriverManagerDataSource ds = new DriverManagerDataSource();        
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
    
        return ds;
    }
    
	/**
	 * TransactionManager bean
	 * @param emf EntityManagerFactory
	 * @return JpaTransactionManager
	 */
	@Bean
	public JpaTransactionManager jpaTransMan(EntityManagerFactory emf){
		JpaTransactionManager jtManager = new JpaTransactionManager(emf);
		return jtManager;
	}	
}
