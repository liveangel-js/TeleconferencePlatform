package data.record;

public interface RecordWriterInterface {
	/**
	 * @param meetingID
	 * @param o
	 * @param username
	 * @return -1ʱд��ʧ�ܣ�����������Ӱ������
	 */
	public abstract int writeRecord(int meetingID,Object o,String username);

}
