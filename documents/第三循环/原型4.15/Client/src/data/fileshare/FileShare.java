package data.fileshare;

import java.sql.Statement;
import java.util.ArrayList;

import sql.SQLConnecter;

public class FileShare implements FileDownloadInterface,FileUploadInterface,MeetingFileInfo{
	int meetingID = -1;
	private SQLConnecter connect = null;
	private Statement statement =null;
	@Override
	public void downloadFile(String fileUrl, String username, int meetingID) {
		// TODO Auto-generated method stub
		
	}

	public boolean uploadFile(String filename, String Username, int meetingID) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ArrayList<String> getAMeetingFileInfro() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getMeetingInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	

}

	
