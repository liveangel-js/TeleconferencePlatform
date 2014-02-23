package manager;

public class ServerManager {
	
	private static int MeetingID=1;
	
	
	
	public static void setMeetingID(int ID ){
		MeetingID=ID;
	}
	
	public static int getMeetingID(){
		return MeetingID;
	}

}
