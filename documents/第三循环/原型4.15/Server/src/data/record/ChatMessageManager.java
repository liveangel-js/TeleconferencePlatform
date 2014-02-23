package data.record;

import data.Message;
import sql.ChatRecordSQL;
import sql.Interface.ChatWriterInterface;

public class ChatMessageManager implements RecordWriterInterface {
	ChatWriterInterface sqlWriter = new ChatRecordSQL();
	@Override
	public boolean writeRecord(Object o,int meetingID,String username) {
		String word = ((Message)o).getWords();
		String user = ((Message)o).getUserName();
		String time = ((Message)o).getTime();
		sqlWriter.writeRecord(user, time, word, 6);
		// TODO Auto-generated method stub
		return false;
	}

}
