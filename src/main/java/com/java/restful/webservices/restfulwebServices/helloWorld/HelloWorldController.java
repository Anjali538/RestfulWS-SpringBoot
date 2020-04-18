package com.java.restful.webservices.restfulwebServices.helloWorld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@Autowired
	private MessageSource mesageSource;

//one way 
	@RequestMapping(method = RequestMethod.GET, path = "/hello")
	public String helloWorld() {
		return "hello worls";
	}

//AnotherWay
	@GetMapping("/hi")
	public String hello() {
		return "hi";
	}

//Path variable
	@GetMapping("/hello/{Id}")
	public String getHellousingPath(@PathVariable String Id) {
		return Id;
	}

	@GetMapping("/hello-world-internationalisation") // using accept header internationalization
	public String internationalMessage(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
		return mesageSource.getMessage("good.morning.message", null, locale);
	}

	// Other way to internationalised without passing in the header parameter
	// because it is very difficult every tym we ar
	// passing in the header

	@GetMapping("/helloInter")
	public String interMessage() {
		return mesageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
}
