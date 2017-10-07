package com.staszkox.stacktrack.configuration;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.staszkox.stacktrack.configuration.filters.SecurityHeadersFilter;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer 
{
	@Override
	protected Class<?>[] getRootConfigClasses() 
	{
		return new Class[] { ResourcesConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() 
	{
		return null;
	}

	@Override
	protected String[] getServletMappings() 
	{
		return new String[] { "/" };
	}
	
	@Override
	protected Filter[] getServletFilters()
	{
		return new Filter[] {new SecurityHeadersFilter()};
	}
}
