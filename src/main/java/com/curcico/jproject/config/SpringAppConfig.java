package com.curcico.jproject.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.curcico.jproject.*")
public class SpringAppConfig {

	@Resource
	private Environment env;

	@Value("${db.driver}")
	private String databaseDriver;
	
	@Value("${db.url}")
	private String databaseUrl;
	
	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernateHbm2dll;
	
	@Value("${hibernate.dialect}")
	private String hibernateDialect;
	
	@Value("${hibernate.show_sql}")
	private String hibernateShowSQL;
	
	@Value("${hibernate.format_sql}")
	private String hibernateFormatSQL;
	
	@Value("${hibernate.globally_quoted_identifiers}")
	private String hibernateGloballyQuotedIdentifiers;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
    @Bean
    public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(databaseDriver);
            dataSource.setUrl(databaseUrl);
            return dataSource;
    }
	
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
       LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
       sessionFactory.setDataSource(dataSource());
       sessionFactory.setPackagesToScan(new String[] {"com.curcico.jproject.entities"});
       sessionFactory.setHibernateProperties(hibernateProperties());
       return sessionFactory;
    }
   
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(sessionFactory);
       return txManager;
    }
    
    Properties hibernateProperties() {
        return new Properties() {
			private static final long serialVersionUID = -5418386938331918780L;

		{
        	   setProperty("hibernate.hbm2ddl.auto", 	hibernateHbm2dll);
        	   setProperty("hibernate.show_sql", 		hibernateShowSQL);
        	   setProperty("hibernate.format_sql", 		hibernateFormatSQL);
        	   setProperty("hibernate.globally_quoted_identifiers", hibernateGloballyQuotedIdentifiers);
        	   setProperty("hibernate.dialect", 		hibernateDialect);
           }
        };
     }   
    
}
