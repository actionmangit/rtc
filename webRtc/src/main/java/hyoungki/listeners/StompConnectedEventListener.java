package hyoungki.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class StompConnectedEventListener  implements ApplicationListener<SessionConnectedEvent>{

	private static final Logger logger = LoggerFactory.getLogger(StompConnectedEventListener.class);
	
	@Override
	public void onApplicationEvent(SessionConnectedEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		
		logger.info("SessionConnectedEvent");
		
		String	user				= headerAccessor.getUser().getName().toString();
		
		logger.info("username = " + user);
		logger.info("sessionId = " + headerAccessor.getSessionId());
	}
}
