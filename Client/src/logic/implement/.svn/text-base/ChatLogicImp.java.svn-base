package logic.implement;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import data.Message;
import rmi.client.interfaces.MessageReciever;
import rmi.server.interfaces.MessageService;
import view.ChatPanel.MessageShower;
import logic.interfaces.ChatLogic;
import manager.ClientManager;
import manager.ServiceManager;


public class ChatLogicImp implements ChatLogic {

	private MessageShower shower;

	private MessageService service;
	
	private MessageReciever receiver;

	public ChatLogicImp(MessageShower messageShower) {
		this.shower = messageShower;
		this.init();
	}

	public void init() {
		try {
			service = ServiceManager.getMessageService();
			System.out.println("查找服务成功");
			receiver=new MessageRecieverImp();
			System.out.println("准备注册用户");
			service.addReciever(receiver);
			System.out.println("注册用户成功");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean sendMessage(String message) {
		// TODO Auto-generated method stub
		try {
			String person = ClientManager.getUserName();
			Message m = new Message(person, message);
			service.sendMessage(m);
			return true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private class MessageRecieverImp extends UnicastRemoteObject implements MessageReciever {

		protected MessageRecieverImp() throws RemoteException {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void recieveMessage(Message message)throws RemoteException {
			// TODO Auto-generated method stub
			shower.show(message.toString());
		}
	}
}
