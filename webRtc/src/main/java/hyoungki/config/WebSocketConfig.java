package hyoungki.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	//클라이언트에서 붙을 주소
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/rtc").withSockJS();
	}
	
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
    	config.setApplicationDestinationPrefixes("/app");
    	config.enableSimpleBroker("/topic", "/queue");
    }
}
