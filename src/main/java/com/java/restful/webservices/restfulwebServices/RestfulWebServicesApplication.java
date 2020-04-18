package com.java.restful.webservices.restfulwebServices;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) use to exclude the auto configuration
@SpringBootApplication
public class RestfulWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServicesApplication.class, args);
	}
	
	
	@Bean // Used for Enable COROS globally
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/users").allowedOrigins("*");
			}
		};
	}

	@Bean // Used for internationalisation with passing langiage in header
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

	// If we not want to pass the accept header using the header param
	// we have to change the sassionLocalResolver into AcceptHeaderLocaleResolver

	@Bean
	public LocaleResolver localeResolverWithoutAcceptHeader() {
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

	@Bean
	@Primary // used to read value from the properties file
	public ResourceBundleMessageSource bundleMessageSource() {
		ResourceBundleMessageSource lResourceBundleMessageSource = new ResourceBundleMessageSource();
		lResourceBundleMessageSource.setBasename("message");
		return lResourceBundleMessageSource;
	}

}
