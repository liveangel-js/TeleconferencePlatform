package logic.implement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import rmi.server.interfaces.FileShareService;
import view.FileSharePanel.ProcedureController;
import logic.interfaces.FileShareLogic;
import manager.ClientManager;
import manager.ServiceManager;


public class FileShareLogicImp implements FileShareLogic {

	private static final int SIZE = 64 * 1024;
	

	@Override
	public ArrayList<String> getFileList()   {
		// TODO Auto-generated method stub
		try {
			FileShareService service = ServiceManager.getFileShareService();
			return service.getFileList();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ServiceManager.networkingbreak();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ServiceManager.networkingbreak();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ServiceManager.networkingbreak();
		}
		return null;
		
	}

	@Override
	public boolean downloadFile(String src, String des,ProcedureController procedure)   {
		// TODO Auto-generated method stub
		new DownloadThread(src, des, procedure).start();
		return true;
	}

	@Override
	public boolean uploadloadFile(String fileName, ProcedureController procedure)  {
		// TODO Auto-generated method stub
		new UploadThread(fileName, procedure).start();
		return true;
	}

	class DownloadThread extends Thread {

		private String src, des;

		private ProcedureController procedure;

		public DownloadThread(String src, String des,ProcedureController procedure) {
			this.src = src;
			this.des = des;
			this.procedure = procedure;
		}

		public void run() {
			try {
				FileShareService service = ServiceManager.getFileShareService();
				int length = service.getFileLength(src);
				File desFile = new File(des);
				if(desFile.exists()){
					int override=JOptionPane.showConfirmDialog(null, "file exist already,override?");
					if(override!=JOptionPane.YES_OPTION){
						return;
					}
				}
				OutputStream out = new FileOutputStream(desFile);
				procedure.init(desFile.getName(), length);
				int value=0;
				int percent;
				for (int i = 0; i < length - SIZE; i += SIZE) {
					byte b[] = service.download(src, i, SIZE);
					out.write(b);
					value+=SIZE;
					percent=(int) (value*1.0/length*100);
					procedure.set("downloading", percent, value);
				}
				byte b[] = service.download(src, value, length-value);
				out.write(b);
				procedure.set("downloaded", 100, length);
				out.close();
				//System.out.println(desFile.getName()+ "download completed");
			} catch (RemoteException e) {
				 e.printStackTrace();
				ServiceManager.networkingbreak();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServiceManager.networkingbreak();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServiceManager.networkingbreak();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "file path wrong~!");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("IO error");
			}
		}
	}

	class UploadThread extends Thread {

		private String fileName;
		private ProcedureController procedure;
		
		private static final int MAX_SIZE=16*1024*1024;

		public UploadThread(String fileName, ProcedureController procedure) {
			this.fileName = fileName;
			this.procedure = procedure;
		}

		public void run() {
			try {
				File file = new File(fileName);
				FileShareService service = ServiceManager.getFileShareService();
				boolean exsit=service.isFileExist(file.getName());
				if(exsit){
					JOptionPane.showMessageDialog(null, file.getName()+" file with same name already exsit~!");
				    return;
				}
				
				InputStream in = new FileInputStream(file);
				int length=(int) file.length();
				if(length>MAX_SIZE){
					JOptionPane.showMessageDialog(null,file.getName()+ " file size should not be larger than 16M~!");
				    return;
			    }
				if(length==0){
					JOptionPane.showMessageDialog(null,file.getName()+ " file size should not be 0~!");
				    return;
				}
				int value=0;
				int percent;
				procedure.init(file.getName(), length);
				for (int i = 0; i < length - SIZE; i += SIZE) {
					byte b[] = new byte[SIZE];
					in.read(b);
					service.upload(file.getName(), b);
					value+=SIZE;
					percent=(int) (value*1.0/length*100);
					procedure.set("uploading", percent, value);
				}
				byte b[] = new byte[length-value];
				in.read(b);
				in.close();
				service.upload(file.getName(), b);

				procedure.set("uploaded", 100, length);
				service.completeUpload(file.getName(), ClientManager.getUserName());
			} catch (RemoteException e) {
				 e.printStackTrace();
				ServiceManager.networkingbreak();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServiceManager.networkingbreak();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "file path wrong~!");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("IO error");
			}
		}
	}
}
