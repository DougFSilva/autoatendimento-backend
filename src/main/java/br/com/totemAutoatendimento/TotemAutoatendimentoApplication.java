package br.com.totemAutoatendimento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TotemAutoatendimentoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TotemAutoatendimentoApplication.class, args);
	}

}
