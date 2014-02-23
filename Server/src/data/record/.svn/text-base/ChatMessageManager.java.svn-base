package data.record;

import data.Message;
import sql.ChatRecordSQL;
import sql.Interface.ChatWriterInterface;

public class ChatMessageManager implements RecordWriterInterface {
	ChatWriterInterface sqlWriter = new ChatRecordSQL();
	@Override
	public int writeRecord(int meetingID,Object o,String username) {
		int columns = -1;
		String words = ((Message)o).getWords();
		String user = ((Message)o).getUserName();
		String time = ((Message)o).getTime();
		columns=sqlWriter.writeRecord(meetingID, username, time, words);
		// TODO Auto-generated method stub
		return columns;
	}

}
