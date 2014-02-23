package data;

import java.util.ArrayList;
import java.util.Date;

public class Meeting {
	private Date startTime =null;
	private Date endTime=null;
	private ArrayList<String> users =null;
	private int id =-1;
	public Meeting(Date startTime){
		this.startTime=startTime;
		
	}

}
