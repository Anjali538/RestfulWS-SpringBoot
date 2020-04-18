package com.java.restful.webservices.restfulwebServices.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	@GetMapping("/filtering")
	public SomeBean retriveSomeBean() {
		
		
		SomeBean someBean = new SomeBean("Value1","Value2","Value3");
		
		//we can use dynamic binding as par below code
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("Field1, Field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		mapping.setFilters(filters);
		
		return someBean; // for static we have to return mapping
		//return mapping; //for dynamic we have to return mapping
	}
	@GetMapping("/filtering-list")
	public List<SomeBean> retriveListOfSomeBean() {
		
		List<SomeBean> list = Arrays.asList(new SomeBean("Value1","Value2","Value3"), new SomeBean("Value11","Value22","Value33"));
		
		
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("Field2, fiels3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(list);
		mapping.setFilters(filters);
		
		return list; // for static or dynamic
		
		
	}

}
