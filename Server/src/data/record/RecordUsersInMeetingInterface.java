package data.record;

public interface RecordUsersInMeetingInterface {
	/**
	 * @param meetingID
	 * @param username
	 * @return δ����ɹ��򷵻�-1 �Ѵ��ڷ���0 ����>0
	 */
	public abstract int insertMeetingMember(int meetingID,String username);
}
