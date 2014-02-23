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
	public String getWords(){
		return words;
	}
	public String getUserName(){
		return person;
	}
	public String getTime(){
		return null;
	}

	@Override
	public String toString() {
		String message=person+":"+words+"\n";
		return message;
	}
}
