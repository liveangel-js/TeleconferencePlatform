package rmi.server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import data.Message;
import rmi.client.interfaces.MessageReciever;





public interface MessageService extends Remote {
	
	public int PORT=8887;
	
	public String NAME="Chatting";
	
	public boolean sendMessage(Message message)throws RemoteException;
	
	public boolean addReciever(MessageReciever reciever)throws RemoteException;

	
}
