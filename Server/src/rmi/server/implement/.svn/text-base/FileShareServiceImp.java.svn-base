package rmi.server.implement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import manager.ServerManager;
import manager.ServiceManager;
import rmi.server.interfaces.FileShareService;
import sql.FileAccessSQL;

public class FileShareServiceImp extends UnicastRemoteObject implements FileShareService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -690923502299593148L;
	
	private FileAccessSQL  sql=new FileAccessSQL();

	public FileShareServiceImp() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized ArrayList<String> getFileList() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("getFileList");
		return sql.getFilesList();
	}

	@Override
	public synchronized int getFileLength(String fileName) throws RemoteException {
		// TODO Auto-generated method stub
		int ID=getFileID(fileName);
		int length=sql.getFileLength(ID);
		return length;
	}

	@Override
	public synchronized byte[] download(String fileName, int start, int length)
			throws RemoteException {
		// TODO Auto-generated method stub
		int ID=getFileID(fileName);
		InputStream in=sql.downloadFile(ID);
		byte b[] = new byte[length];
		try {
			in.skip(start);
			in.read(b);
			sql.closeStream();
			return b;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public boolean isFileExist(String fileName) throws RemoteException {
		// TODO Auto-generated method stub
		int ID=getFileID(fileName);
		File file = new File("upload/"+fileName);
		if(!file.exists()&&(ID==-1)){
			try {
				System.out.println(file.getAbsolutePath());
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}
	
	

	@Override
	public synchronized void upload(String fileName,byte b[]) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			File file = new File("upload/"+fileName);
			FileOutputStream out = new FileOutputStream(file, true);
			out.write(b);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("IO´íÎó");
		}
	}

	@Override
	public synchronized void completeUpload(String fileName, String userName)throws RemoteException {
		// TODO Auto-generated method stub
		sql.uploadFile("upload/"+fileName, userName, ServerManager.getMeetingID());
		File file=new File("upload/"+fileName);
		file.delete();
	}
	
	
	
	private synchronized int getFileID(String fileName)throws RemoteException{
		ArrayList<String>fileList=sql.getFilesList();
		for(int i=0;i<fileList.size();i++){
			String name=fileList.get(i).split(",")[2];
			String ID=fileList.get(i).split(",")[0];
			if(name.equals(fileName)){
				return Integer.parseInt(ID);
			}
		}
		return -1;
	}
	
	
	public static void main(String args[]) throws RemoteException, MalformedURLException, UnknownHostException{
		ServiceManager.setupFileShareService();
	}


}
