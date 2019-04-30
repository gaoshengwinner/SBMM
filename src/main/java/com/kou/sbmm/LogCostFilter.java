package com.kou.sbmm;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Configuration でDI必要
 * @author gaosh
 *
 */
public class LogCostFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(SbmmApplication.class.getName());
	public void init(FilterConfig filterConfig) throws ServletException {
 

    }
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		long start = System.currentTimeMillis();
		logger.trace("filter trace start:" + start);
		

		chain.doFilter(request,response);

		start = System.currentTimeMillis();
		logger.trace("filter trace end:" + start);
		
	}



}
