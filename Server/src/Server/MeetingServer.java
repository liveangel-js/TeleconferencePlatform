/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;


import manager.ServiceManager;
import rmi.server.implement.LoggingServiceImp;
import rmi.server.implement.MessageServiceImp;
import rmi.server.implement.PaintSendImp;
import rmi.server.interfaces.LoggingService;
import rmi.server.interfaces.MeetingControlService;
import rmi.server.interfaces.MemberControlInterface;
import rmi.server.interfaces.MessageService;
import rmi.server.interfaces.OrderControlInterface;
import rmi.server.interfaces.PaintSender;
import voiceServer.voiceServer;

/**
 *
 * @author Gyx
 */
public class MeetingServer {
    meetingControl MC;
    voiceServer VS;
    memberControl MemberC;
    OrderControl orderC;
    
    
    public MeetingServer(){
        Logging();
        
        Chatting();
        
        VS=new voiceServer();
        VS.start();
        PaintControl();
        
        FileShare();
        memberControl();
        orderControl();
        MeetingControl();

        
    }
    

    private void Logging() {
        try {
            LoggingService service=new LoggingServiceImp();
            LocateRegistry.createRegistry(service.PORT);
            Naming.rebind("rmi://127.0.0.1:"+service.PORT+"/"+service.NAME, service);
        } catch (RemoteException ex) {
            Logger.getLogger(MeetingServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MeetingServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void MeetingControl() {
        try {
            MC=new meetingControl(MemberC);
            LocateRegistry.createRegistry(MC.PORT);
            Naming.rebind("rmi://127.0.0.1:"+MC.PORT+"/"+MC.NAME, MC);
        } catch (RemoteException ex) {
            Logger.getLogger(MeetingServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MeetingServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("会议管理开启");
    }

    private void Chatting() {
        try {
            MessageService service=new MessageServiceImp();
            LocateRegistry.createRegistry(service.PORT);
            Naming.rebind("rmi://"+"127.0.0.1"+":"+service.PORT+"/"+service.NAME, service);
            System.out.println("聊天室服务开启");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void memberControl() {
        try {
            MemberC=new memberControl();
            LocateRegistry.createRegistry(MemberC.PORT);
            Naming.rebind("rmi://127.0.0.1:"+MemberC.PORT+"/"+MemberC.NAME, MemberC);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("成员管理开启");
    }

    private void PaintControl() {
        try {
            PaintSender service =new PaintSendImp();
            LocateRegistry.createRegistry(service.PORT);
            try {
                Naming.rebind( "rmi://"+"127.0.0.1:"+service.PORT+"/"+service.NAME,service);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("白板服务开启");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  }


    private void orderControl() {
        try {
            orderC=new OrderControl();
            LocateRegistry.createRegistry(orderC.PORT);
            Naming.rebind("rmi://127.0.0.1:"+orderC.PORT+"/"+orderC.NAME, orderC);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("白板管理开启");
    }

    private void FileShare() {
        try {
            ServiceManager.setupFileShareService();
        } catch (RemoteException ex) {
            Logger.getLogger(MeetingServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MeetingServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(MeetingServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public MeetingControlService getMC(){
        return MC;
    }

}

