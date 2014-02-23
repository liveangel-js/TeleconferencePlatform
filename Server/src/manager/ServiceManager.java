package manager;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.swing.JOptionPane;

import rmi.server.implement.FileShareServiceImp;
import rmi.server.implement.MessageServiceImp;
import rmi.server.interfaces.FileShareService;
import rmi.server.interfaces.MessageService;


public class ServiceManager {
	
	public static void setupFileShareService() throws RemoteException, MalformedURLException, UnknownHostException{
		FileShareService service=new FileShareServiceImp();
		String IP=InetAddress.getLocalHost().getHostAddress();
		int port=FileShareService.PORT;
		String name=FileShareService.NAME;
		String url="rmi://"+IP+":"+port+"/"+name;
		LocateRegistry.createRegistry(port);
		Naming.rebind(url, service);
		System.out.println(name+"服务开启");
	}
	
	
	public static void setupMessageService() throws RemoteException, MalformedURLException, UnknownHostException{
		MessageService service=new MessageServiceImp();
		String IP=InetAddress.getLocalHost().getHostAddress();
		int port=MessageService.PORT;
		String name=MessageService.NAME;
		String url="rmi://"+IP+":"+port+"/"+name;
		LocateRegistry.createRegistry(port);
		Naming.rebind(url, service);
		System.out.println(name+"服务开启");
	}
	


}
