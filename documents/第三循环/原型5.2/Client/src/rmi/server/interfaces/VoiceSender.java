package rmi.server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.client.interfaces.VoiceReceiver;
import rmi.data.Voice;



public interface VoiceSender extends Remote {
	

	
	public boolean sendVoice(Voice voice)throws RemoteException;
	
	public boolean addReciever(VoiceReceiver reciever)throws RemoteException;
	
}
