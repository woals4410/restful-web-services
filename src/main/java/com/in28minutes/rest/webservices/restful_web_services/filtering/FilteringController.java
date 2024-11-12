package com.in28minutes.rest.webservices.restful_web_services.filtering;

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
	public MappingJacksonValue filtering() {
		
		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);

		// filtering logic
		FilterProvider filters = filteringField(mappingJacksonValue, "field1", "field3");
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}
	
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue filteringList() {
		
		List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"),
						new SomeBean("value4", "value5", "value6"));
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		
		// filtering logic
		FilterProvider filters = filteringField(mappingJacksonValue, "field2", "field3");
		mappingJacksonValue.setFilters(filters);
		
		
		return mappingJacksonValue;
	}


	private FilterProvider filteringField(MappingJacksonValue mappingJacksonValue, String fieldName1, String fieldName2) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fieldName1, fieldName2);
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		return filters;
	}
	
}
