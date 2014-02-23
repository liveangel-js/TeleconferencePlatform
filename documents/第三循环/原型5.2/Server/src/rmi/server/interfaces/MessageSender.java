package rmi.server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.client.interfaces.MessageReciever;
import rmi.data.Message;






public interface MessageSender extends Remote {//客户端可调用的服务器远程对象
	
	public boolean sendMessage(Message message)throws RemoteException;
	
	public boolean addReciever(MessageReciever reciever)throws RemoteException;

	
}
