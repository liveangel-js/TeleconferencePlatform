package data;

import java.io.Serializable;
import java.util.Calendar;

public class Message implements Serializable {
	
	private Calendar time;
	
	private String person;
	
	private String words;
	
	public Message(){
		
	}

	public Message(String person, String words) {
		super();
		time=Calendar.getInstance();
		this.person = person;
		this.words = words;
	}

	@Override
	public String toString() {
		String message=time.getTime().toLocaleString()+"\n"+person+":"+words+"\n";
		return message;
	}
	
}
