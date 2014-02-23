package rmi.server.implement;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi.server.interfaces.FileShareService;

public class FileShareImp extends UnicastRemoteObject implements FileShareService {

	protected FileShareImp() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean uploadFile(File file) throws RemoteException{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public File downloadFile(String file_name) throws RemoteException{
		// TODO Auto-generated method stub
		return null;
	}

}
