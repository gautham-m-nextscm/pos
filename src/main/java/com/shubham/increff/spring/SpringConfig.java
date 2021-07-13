package com.shubham.increff.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan("com.shubham.increff")
@PropertySources({ //
		@PropertySource(value = "file:./employee.properties", ignoreResourceNotFound = true) //
})
public class SpringConfig {


}
