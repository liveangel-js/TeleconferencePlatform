package rmi.server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.client.interfaces.MessageReceiver;
import rmi.data.Message;






public interface MessageSender extends Remote {
	
	public boolean sendMessage(Message message)throws RemoteException;
	
	public boolean addReciever(MessageReceiver reciever)throws RemoteException;

	
}
