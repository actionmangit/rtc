package hyoungki.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import hyoungki.domain.AuthMap;

@Component
public class StompDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent>{

	private static final Logger logger = LoggerFactory.getLogger(StompDisconnectEventListener.class);
	
	@Override
	public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
		
		String	userName				= headerAccessor.getUser().getName().toString();
		
		logger.info("userName = " + userName);
		
		AuthMap.delSessionId(userName);
	}
}
