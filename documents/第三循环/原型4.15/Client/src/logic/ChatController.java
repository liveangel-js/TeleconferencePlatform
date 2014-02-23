package logic;


import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import net.ChatNetController;
import net.ChatNetInterface;
import data.Message;

public class ChatController implements ChatLogicInterface {
	
	private ChatNetInterface net;
	SocketAddress s;
	
	public ChatController(){
		this.init();
	}
	
	public void init(){
		net=new ChatNetController();
	}

	@Override
	public boolean sendMessage(String message) {
		// TODO Auto-generated method stub
		//InetAddress.getLocalHost().toString()
		Message m;
		try {
			m = new  Message(InetAddress.getLocalHost().getHostAddress(),message);
			net.sendMessage(m);
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		Message message=net.getMessage();
        String m=message.toString();
        return m;
	}
}
