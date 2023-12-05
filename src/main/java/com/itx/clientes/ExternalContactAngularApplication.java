package com.itx.clientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan(basePackages = "com.itx.clientes.ControllerService")
@SpringBootApplication
public class ExternalContactAngularApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExternalContactAngularApplication.class, args);
	}

}
