package com.myblog.myblog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}
	//Spring IOC does not have any knowledge about creating model mapper object
	//so that we need to educate the IOC through  declaring  bean
	//when spring IOC does not have knowledge to configure the object than we need to specify in application
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
