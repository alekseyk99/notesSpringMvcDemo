package com.alekseyk99.spring;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * The SpringMVC application context.
 *
 * Any @Controller classes will be picked up by component scanning. All other
 * components are ignored as they will be picked up by the root application
 * context.
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com.alekseyk99.spring.controller" })
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
	}
	
    /**
     * Setup for JSP views.
     * @return InternalResourceViewResolver
     */
	@Bean
	public InternalResourceViewResolver jspViewResolver() {
	    InternalResourceViewResolver bean = new InternalResourceViewResolver();
	    bean.setPrefix("/WEB-INF/views/");
	    bean.setSuffix(".jsp");
	    return bean;
	}
	  
	/* uncomment if ComponentScan is not used  
	@Bean 
	public WebController getWebController() {
	    return new WebController();
	}
	*/
	/* uncomment if ComponentScan is not used
	@Bean 
	public NoteService getNoteService() {
	    return new NoteService();
	}
	*/
	
	/**
	 * Setup messages source
	 * Use messages_XX.propertires files
	 * @return ReloadableResourceBundleMessageSource
	 */
	@Bean(name="messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
		ms.setBasename("classpath:messages");
		ms.setDefaultEncoding("UTF-8");
		return ms;
	}

	/**
	 * Setup a way to keep state of locale
	 * via cookie "locale"
	 * @return CookieLocaleResolver
	 */
	@Bean(name="localeResolver")
	public LocaleContextResolver getLocaleResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("ru"));
		resolver.setCookieName("locale");
		resolver.setCookieMaxAge(3600);
		return resolver;
	}

	/**
	 * Setup a way to change Locale
	 * Locale will change via parameter name "lang"
	 * 
	 * Interceptor is configured to intercept the user request and identify the user locale. 
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
	    registry.addInterceptor(interceptor);
	}
	
}
