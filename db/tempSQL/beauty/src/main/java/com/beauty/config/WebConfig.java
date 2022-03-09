package com.beauty.config;

import java.nio.charset.Charset;

import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@EnableAutoConfiguration
//@ComponentScan(basePackages = "com.beauty.admin.*")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
//		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
//		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
//		registry.addResourceHandler("/images/**").addResourceLocations("/images/");
		registry.addResourceHandler("/vendors/**").addResourceLocations("classpath:/static/vendors/");
//		registry.addResourceHandler("/plugins/**").addResourceLocations("classpath:/static/plugins/");
//		registry.addResourceHandler("/bootstrap/**").addResourceLocations("classpath:/static/bootstrap/");
		registry.addResourceHandler("/dist/**").addResourceLocations("classpath:/static/dist/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("redirect:/admin/login");
		registry.addViewController("/admin/login").setViewName("login");
		registry.addViewController("/error/401").setViewName("/error/401");
		registry.addViewController("/error/404").setViewName("/error/404");
		registry.addViewController("/error/500").setViewName("/error/500");
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401");
				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
				ErrorPage error505Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
				
				container.addErrorPages(error404Page, error505Page, error401Page);
			}
		};
	}
	
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

//	
//	@Bean
//	public VelocityConfigurer velocityConfigurer() {
//	    VelocityConfigurer configurer = new VelocityConfigurer();
//	    configurer.setResourceLoaderPath("classpath:/templates");
//	    Properties properties = new Properties();
//	    properties.setProperty("input.encoding", "UTF-8");
//	    properties.setProperty("output.encoding", "UTF-8");
//	    configurer.setVelocityProperties(properties);
//	    return configurer;
//	}
//	@Bean
//	ServletRegistrationBean h2servletRegistration(){
//		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
//		registrationBean.addUrlMappings("/console/*");
//		return registrationBean;
//	}
	
}