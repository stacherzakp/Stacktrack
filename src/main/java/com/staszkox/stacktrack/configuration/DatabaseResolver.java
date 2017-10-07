package com.staszkox.stacktrack.configuration;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseResolver
{
	private static final Logger logger = Logger.getLogger(DatabaseResolver.class);

	private String dbUrl;
	private String dbUsername;
	private String dbPassword;
	
	@Autowired
	public DatabaseResolver()
	{
		readProperties();
	}
	
	private void readProperties()
	{
		try
		{
			readDatabaseProperties();
		}
		catch (Exception e)
		{
			System.exit(0);
		}
	}
	
	private void readDatabaseProperties() throws Exception
	{
		Properties properties = new Properties();
		
		try (FileInputStream input = new FileInputStream(System.getProperty("stacktrack-database")))
		{
			properties.load(input);
			
			dbUrl = properties.getProperty("db.url");
			dbUsername = properties.getProperty("db.username");
			dbPassword = properties.getProperty("db.password");
		}
		catch (Exception e)
		{
			logger.error("Missing stacktrack-database VM arg!", e);
			throw e;
		}
	}

	public String getDbUrl()
	{
		return dbUrl;
	}

	public String getDbUsername()
	{
		return dbUsername;
	}

	public String getDbPassword()
	{
		return dbPassword;
	}
}
