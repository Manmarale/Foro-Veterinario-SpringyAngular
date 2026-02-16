package com.manmarale.foro_veterinario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ForoVeterinarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForoVeterinarioApplication.class, args);
	}

}
