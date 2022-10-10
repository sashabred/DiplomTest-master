package com.example.test;

import com.example.test.model.Flat;
import com.example.test.service.FlatServiceImpl;
import com.example.test.service.UserDetailsServiceImpl;
import com.example.test.model.Address;
import com.example.test.model.House;
import com.example.test.service.HouseServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}


	@Bean
	CommandLineRunner run(HouseServiceImpl houseService, UserDetailsServiceImpl userService, FlatServiceImpl flatService) {
		return args -> {
			houseService.save(new House(new Address( "Россия", "Улан-Удэ", "Бабушкина"), "33"));
			houseService.save(new House(new Address( "Россия", "Москва", "Ленина"), "164"));
			flatService.save(new Flat("32", "flat", new House(new Address( "Россия", "Санкт-Петербург", "Фрунзе"), "201/5")));
		};
	}

}
