package hyoungki.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthMap {
	private static final Logger logger = LoggerFactory.getLogger(AuthMap.class);
	
	private static Map<String, Integer>			joinedRoomNo	= Collections.synchronizedMap(new HashMap<String, Integer>());	
	private static Map<Integer, List<String>>	attendList		= Collections.synchronizedMap(new HashMap<Integer, List<String>>());	
	
	public static List<String> getSessionIdList(int roomNo) {
		List<String> attendListByRoom		= new ArrayList<>();
		
		attendListByRoom		= attendList.get(roomNo);
		
		return attendListByRoom;
	}
	
	public static Integer getJoinRoomNo(String id) {
		return joinedRoomNo.get(id);
	}
	
	public static void addSessionId(String id, int roomNo) {
		logger.info("addSessionId : id = " + id);
		logger.info("addSessionId : roomNo = " + roomNo);
		
		joinedRoomNo.put(id, roomNo);
		
		List<String> attendListByRoom		= new ArrayList<>();
		
		if (attendList.get(roomNo) != null) {
			attendListByRoom		= attendList.get(roomNo);
			
			attendListByRoom.add(id);
		}
		else {
			attendListByRoom.add(id);
		}
		
		attendList.put((Integer) roomNo, attendListByRoom);
	}	
	
	public static void delSessionId(String id) {
		
		logger.info("delSessionId : id = " + id);
		
		Integer		roomNo	  	= joinedRoomNo.get(id);
		
		logger.info("delSessionId : roomNo = " + roomNo);
		logger.info("delSessionId : attendList = " + attendList.get(roomNo));
		
		if (roomNo != null && attendList.get(roomNo) != null) {
			joinedRoomNo.remove(id);
			
			List<String> attendListByRoom		= new ArrayList<>(); 
					
			attendListByRoom					= attendList.get(roomNo);
			int		index						= 0;
			
			logger.info("delSessionId : attendListByRoom = " + attendListByRoom.toString());
			
			for (int i = 0; i < attendListByRoom.size(); i++) {
				
				if (attendListByRoom.get(i).equals(id)) {
					index	= i;
					break;
				}
			}
			
			attendListByRoom.remove(index);
			
			if (attendListByRoom.size() > 0) {
				attendList.put(roomNo, attendListByRoom);
			}
			else {
				attendList.remove(roomNo);
			}
		}
	}
}
