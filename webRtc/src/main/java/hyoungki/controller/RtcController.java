package hyoungki.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import hyoungki.domain.Room;
import hyoungki.domain.Sdp;
import hyoungki.domain.AuthMap;
import hyoungki.domain.Ice;

@Controller
public class RtcController {
	
	private static final Logger logger = LoggerFactory.getLogger(RtcController.class);
	private SimpMessagingTemplate		template;
	
	@Autowired
	public RtcController(SimpMessagingTemplate template) {
		this.template = template;
	}
	                                                  
	@MessageMapping("/joinRoom")
//	@SendToUser("/queue/joinRoom")
	public void joinRoom (@Payload Room room, Principal principal) throws Exception {
		
		logger.info("name = " + principal.getName());
		logger.info("roomInfo = " + room.toString());
		
		AuthMap.addSessionId(principal.getName(), room.getRoomNo());
		
		List<String>	attendees	= AuthMap.getSessionIdList(room.getRoomNo()); 
		
		room.setAttendees(attendees);
		room.setLastAttendee(principal.getName());
		
		for (String id : attendees) {
			logger.info("userName = " + id);
			this.template.convertAndSendToUser(id, "/queue/joinRoom", room);
		}		
		
//		return room;
	}
	
	@MessageMapping("/sendOffer")
	public void sendOffer (@Payload Sdp sdp, Principal principal) throws Exception {
		
		logger.info("name = " + principal.getName());
		logger.info("sdp = " + sdp);
		
		int		roomNo		= AuthMap.getJoinRoomNo(principal.getName());
		
		logger.info("roomNo = " + roomNo);
		
		List<String>		attendingList	= AuthMap.getSessionIdList(roomNo);
		
		for (String id : attendingList) {
			logger.info("userName = " + id);
			if (! id.equals(principal.getName())) {
				this.template.convertAndSendToUser(id, "/queue/sdp", sdp);
			}
		}		
	}

	@MessageMapping("/sendAnswer")
	public void sendAnswer (@Payload Sdp sdp, Principal principal) throws Exception {
		
		logger.info("sdp = " + sdp);
		
		int		roomNo		= AuthMap.getJoinRoomNo(principal.getName());
		
		logger.info("roomNo = " + roomNo);
		
		List<String>		attendingList	= AuthMap.getSessionIdList(roomNo);
		
		for (String id : attendingList) {
			if (! id.equals(principal.getName())) {
				this.template.convertAndSendToUser(id, "/queue/sdp", sdp);
			}
		}	
	}

	@MessageMapping("/sendIce")
	public void sendIce (@Payload Ice ice, Principal principal) throws Exception {
		
		logger.info("ice = " + ice);
		
		int		roomNo		= AuthMap.getJoinRoomNo(principal.getName());
		
		logger.info("roomNo = " + roomNo);
		
		List<String>		attendingList	= AuthMap.getSessionIdList(roomNo);
		
		for (String id : attendingList) {
			if (! id.equals(principal.getName())) {
				this.template.convertAndSendToUser(id, "/queue/ice", ice);
			}
		}	
	}
}
