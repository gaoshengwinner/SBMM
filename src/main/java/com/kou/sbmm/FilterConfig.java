package com.kou.sbmm;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
	@Bean
	public FilterRegistrationBean<LogCostFilter> registFilter() {

		FilterRegistrationBean<LogCostFilter> registration = new FilterRegistrationBean<LogCostFilter>();

		registration.setFilter(new LogCostFilter());

		registration.addUrlPatterns("/*");

		registration.setName(LogCostFilter.class.getName());

		registration.setOrder(1);

		return registration;

	}
}
