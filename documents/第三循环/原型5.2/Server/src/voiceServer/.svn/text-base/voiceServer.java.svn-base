package voiceServer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import rmi.server.implement.PaintSendImp;
import rmi.server.interfaces.PaintSender;


public class voiceServer {
	public static void main(String []args){
		Mixer.userNumber=0;
		ServerSocket ss=null;
		try {
			ss=new ServerSocket(5555);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Socket s=null;
	    while (true){
	    	try{
	    		s=ss.accept();
	    		new Mixer(s).start();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	}
}


