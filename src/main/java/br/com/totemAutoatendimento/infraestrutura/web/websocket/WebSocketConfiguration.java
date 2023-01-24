package br.com.totemAutoatendimento.infraestrutura.web.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
	
	@Value("${app.frontend.origem}")
	private String frontOrigen;

	/**
	 * Método implementado da interface <b>WebSocketMessageBrokerConfigurer</b> que registra o endpoint da conexão websocket
	 * e seta a origem permitida
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/pedidos-recebidos").setAllowedOrigins(frontOrigen).withSockJS();
	}

	/**
	 * Método implementado da interface <b>WebSocketMessageBrokerConfigurer</b> que configura o Broker
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app").enableSimpleBroker("/topic");
	}
	
}
