package rmi.server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.client.interfaces.MessageReciever;
import rmi.data.Message;






public interface MessageSender extends Remote {//�ͻ��˿ɵ��õķ�����Զ�̶���
	
	public boolean sendMessage(Message message)throws RemoteException;
	
	public boolean addReciever(MessageReciever reciever)throws RemoteException;

	
}
