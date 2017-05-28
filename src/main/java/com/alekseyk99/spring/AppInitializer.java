package com.alekseyk99.spring;

import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Base class for WebApplicationInitializer implementations 
 * that register a DispatcherServlet configured with annotated classes, 
 * e.g. Spring's @Configuration classes.
 *
 */
public class AppInitializer 
	extends AbstractAnnotationConfigDispatcherServletInitializer 
	implements WebApplicationInitializer  
	// Adding "implements WebApplicationInitializer" is redundant because one of the parent 
	// classes of AbstractAnnotationConfigDispatcherServletInitializer does implement 
	// WebApplicationInitializer. 
	// But it seems that some application server is not looking for implementation in lib jar
	{

    /**
     * Set the root context for the Spring
     * 
     * See {@link AbstractAnnotationConfigDispatcherServletInitializer}.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{AppConfig.class};
    }

    /**
     * Set the context for the Spring MVC web tier.
     *
     * @See {@link AbstractAnnotationConfigDispatcherServletInitializer}
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{MvcConfig.class};
    }

    /**
     * Map the Spring MVC servlet as the root.
     *
     * @See {@link AbstractAnnotationConfigDispatcherServletInitializer}
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
	
    /**
     * Set Parameter to server to throw exception if page not found
     * so we can catch it and send custom page
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration)
    {
         registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }

}
