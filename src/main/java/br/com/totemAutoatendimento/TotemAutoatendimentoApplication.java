package br.com.totemAutoatendimento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@EnableCaching
@SecurityScheme(name = "api-security", scheme = "basic",  type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.COOKIE)
@OpenAPIDefinition(info = @Info(
		title = "Totem de autoatendimento eletrônico", 
		description = "API destinada ao controle de um totem de autoatendimento eletrônico", 
		version = "1.0.0"))
public class TotemAutoatendimentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TotemAutoatendimentoApplication.class, args);
		
	}

}
