package hyoungki.domain;

import java.util.List;

import lombok.Data;

@Data
public class Room {
	private int roomNo;
	private List<String> attendees;
	private String lastAttendee;
}
