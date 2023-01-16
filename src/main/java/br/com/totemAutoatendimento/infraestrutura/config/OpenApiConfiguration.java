package br.com.totemAutoatendimento.infraestrutura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfiguration {

	@Bean
	public OpenAPI customizeOpenAPI() {
		final String securitySchemeName = "bearerAuth";
		OpenAPI openAPI = new OpenAPI();
		openAPI.addSecurityItem(new SecurityRequirement()
			.addList(securitySchemeName))
			.components(new Components()
			.addSecuritySchemes(securitySchemeName, new SecurityScheme()
			.name(securitySchemeName)
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT")));
		Info info = new Info();
		info.setTitle("Totem de autoatendimento eletrônico");
		info.setDescription("API destinada ao controle de um totem de autoatendimento eletrônico");
		info.setVersion("1.0.0");
		return openAPI;
	}
}
