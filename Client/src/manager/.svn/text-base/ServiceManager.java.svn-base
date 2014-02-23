/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import logic.Setting;
import rmi.server.interfaces.FileShareService;
import rmi.server.interfaces.LoggingService;
import rmi.server.interfaces.MeetingControlService;
import rmi.server.interfaces.MemberControlInterface;
import rmi.server.interfaces.MessageService;
import rmi.server.interfaces.OrderControlInterface;

/**
 *
 * @author Gyx
 */
public class ServiceManager {
    public static LoggingService getLoggingService() throws NotBoundException, MalformedURLException, RemoteException{
        String ip=Setting.get("ip");
        int port= LoggingService.PORT;
        String name=LoggingService.NAME;
        String url="rmi://"+ip+":"+port+"/"+name;
        LoggingService service = (LoggingService) Naming.lookup(url);
        return service;
    }
    public static MeetingControlService getMeetingControlService() throws NotBoundException, MalformedURLException, RemoteException{
        String ip=Setting.get("ip");
        int port= MeetingControlService.PORT;
        String name=MeetingControlService.NAME;
        String url="rmi://"+ip+":"+port+"/"+name;
        MeetingControlService service = (MeetingControlService) Naming.lookup(url);
        return service;
    }

    public static MemberControlInterface getMemberControlService() throws NotBoundException, MalformedURLException, RemoteException{
        String ip=Setting.get("ip");
        int port= MemberControlInterface.PORT;
        String name=MemberControlInterface.NAME;
        String url="rmi://"+ip+":"+port+"/"+name;
        MemberControlInterface service = (MemberControlInterface) Naming.lookup(url);
        return service;
    }

    public static OrderControlInterface getOrderControl() throws NotBoundException, MalformedURLException, RemoteException{
        String ip=Setting.get("ip");
        int port= OrderControlInterface.PORT;
        String name=OrderControlInterface.NAME;
        String url="rmi://"+ip+":"+port+"/"+name;
        OrderControlInterface service = (OrderControlInterface) Naming.lookup(url);
        return service;
    }
    
	public static FileShareService getFileShareService() throws MalformedURLException, RemoteException, NotBoundException{
		String IP=Setting.get("ip");
		int port=FileShareService.PORT;
		String name=FileShareService.NAME;
		String url="rmi://"+IP+":"+port+"/"+name;
		FileShareService service=(FileShareService) Naming.lookup(url);
		return service;
	}
	
	
	public static MessageService getMessageService() throws MalformedURLException, RemoteException, NotBoundException{
		String IP=Setting.get("ip");
		int port=MessageService.PORT;
		String name=MessageService.NAME;
		String url="rmi://"+IP+":"+port+"/"+name;
		MessageService service=(MessageService) Naming.lookup(url);
		return service;
	}
	
	public static void networkingbreak(){
		String message="networking problem,please check the networking or connect the server and restart";
		JOptionPane.showMessageDialog(null,message );
		System.exit(0);
	}
}
