package com.staszkox.stacktrack.configuration.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SecurityHeadersFilter implements Filter
{
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		HttpServletResponse response = (HttpServletResponse) res;
	    
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
	    response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("X-XSS-Protection", "1; mode=block");

	    chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
	}

	@Override
	public void destroy()
	{
	}
}
