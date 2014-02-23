package data.record;

import java.util.ArrayList;
import java.util.Date;

import sql.MeetingRecordSQL;
import sql.Interface.MeetingDataInterface;

import data.Meeting;

public class MeetingRecordManager implements RecordWriterInterface,RecordReaderInterface,RecordUpdateInterface,RecordUsersInMeetingInterface {
	private MeetingDataInterface recorder = new MeetingRecordSQL();
	public ArrayList<Meeting> getMeetingList(){
		return null;
	}
	

	@Override
	public int updateRecordByID(int meetingID, Object o,String username) {
		// TODO Auto-generated method stub
		int columns =-1;
		Date temp = ((Meeting)o).getEndTime();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endTime = sdf.format(temp);
		
		columns =recorder.updateMeeting(meetingID, endTime);
		return columns;
	}
	@Override
	public Object readRecordByID(int objectID, int meetingID) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int writeRecord( int meetingID,Object o, String username) {
		// TODO Auto-generated method stub
		int columns =-1;
		Date temp = ((Meeting)o).getStartTime();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(temp);
		columns =recorder.insertMeeting(currentTime);
		return columns;
	}


	@Override
	public int insertMeetingMember(int meetingID, String username) {
		// TODO Auto-generated method stub
		int columns = -1;
		columns = recorder.insertMeetingMember(meetingID, username);
		return columns;
	}
	
	
	
	

}
