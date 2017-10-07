package com.staszkox.stacktrack.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
public class PersistanceConfiguration
{
	@Bean
	@Autowired
	public LocalSessionFactoryBean sessionFactory(DataSource database) 
	{
		Properties prop = new Properties();
		prop.put("hibernate.hbm2ddl.auto", "update");
		prop.put("hibernate.show_sql", false);

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		sessionFactory.setDataSource(database);
		sessionFactory.setPackagesToScan(new String[] {"com.staszkox"});
		sessionFactory.setHibernateProperties(prop);
 
		return sessionFactory;
	}
	 
	@Bean
	@Autowired
	public DataSource dataSource(DatabaseResolver initializer) 
	{
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriver(new Driver());
		dataSource.setUrl(initializer.getDbUrl());
		dataSource.setUsername(initializer.getDbUsername());
		dataSource.setPassword(initializer.getDbPassword());
		
		return dataSource;
	}
	 
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) 
	{
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		
		txManager.setSessionFactory(sessionFactory);
		
		return txManager;
	}
	 
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() 
	{
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
