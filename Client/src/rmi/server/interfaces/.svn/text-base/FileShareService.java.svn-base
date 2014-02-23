package rmi.server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface FileShareService extends Remote {
	
	public int PORT=8889;
	
	public String NAME="FileShare";
	
	public ArrayList<String>getFileList()throws RemoteException;
	
	public int getFileLength(String fileName)throws RemoteException;
	
	public byte[] download(String fileName, int start, int length)throws RemoteException;
	
	public boolean isFileExist(String fileName)throws RemoteException;
	
	public void upload(String fileName,byte b[])throws RemoteException;
	
	public void completeUpload(String fileName,String userName)throws RemoteException;

}
