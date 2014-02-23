package rmi.client.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import data.Message;




public interface MessageReciever extends Remote{
	
	public void recieveMessage(Message message)throws RemoteException;

}
