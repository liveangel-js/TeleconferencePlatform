package rmi.server.implement;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import manager.ServerManager;
import manager.ServiceManager;
import data.Message;
import data.record.ChatMessageManager;
import data.record.MeetingRecordManager;
import data.record.RecordWriterInterface;
import rmi.client.interfaces.MessageReciever;
import rmi.server.interfaces.MessageService;

public class MessageServiceImp extends UnicastRemoteObject implements MessageService {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7143552015056186549L;
	
	private ArrayList<MessageReciever>receivers=new ArrayList<MessageReciever>();
	
	private RecordWriterInterface writer=new ChatMessageManager();
	
	
	public MessageServiceImp() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public synchronized boolean sendMessage(Message message)throws RemoteException {
		// TODO Auto-generated method stub
		for(int i=0;i<receivers.size();i++){
			MessageReciever receiver=receivers.get(i);
			try{
				receiver.recieveMessage(message);
			}catch(RemoteException e){
				receivers.remove(receiver);
				i--;
			}
		}

		return true;
	}

	@Override
	public synchronized boolean addReciever(MessageReciever reciever)throws RemoteException {
		// TODO Auto-generated method stub
		receivers.add(reciever);
		return true;
	}
	
	
	public static void main(String ars[]) throws RemoteException, MalformedURLException, UnknownHostException{
		ServiceManager.setupMessageService();
	}
}
