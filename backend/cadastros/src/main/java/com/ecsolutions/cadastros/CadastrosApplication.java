package com.ecsolutions.cadastros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CadastrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastrosApplication.class, args);
	}

}
