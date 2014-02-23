package data.record;

import java.util.ArrayList;

import sql.MeetingRecordSQL;

import data.Meeting;

public class MeetingRecordManager implements RecordWriterInterface,RecordReaderInterface,RecordUpdateInterface {
	private MeetingRecordSQL record =null;
	public ArrayList<Meeting> getMeetingList(){
		return null;
	}
	

	@Override
	public boolean updateRecordByID(int ID, Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Object readRecordByID(int objectID, int meetingID) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean writeRecord(Object o, int meetingID, String username) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	

}
