package net;

import data.Message;

public interface ChatNetInterface {
	
	boolean sendMessage(Message m);
	
	Message getMessage();

}
