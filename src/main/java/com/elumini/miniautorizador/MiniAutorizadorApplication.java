package com.elumini.miniautorizador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.elumini.miniautorizador.service")
@ComponentScan("com.elumini.miniautorizador.controller")
@ComponentScan("com.elumini.miniautorizador.exception")
public class MiniAutorizadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniAutorizadorApplication.class, args);
	}

}
