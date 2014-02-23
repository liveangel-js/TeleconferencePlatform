package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.Message;

public class ChatNetController implements ChatNetInterface {

	private Socket client;
	
	private ObjectInputStream in;
	
	private ObjectOutputStream out;

	
	public ChatNetController() {
		this.setUpNetworking();
	}


	// 连接，将 输入输出 的信息与socket进行绑定
	private void setUpNetworking() {
		try {
			client = new Socket("127.0.0.1", 8888);
			in= new ObjectInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
			System.out.println("networking established");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//接受 界面层 输入，然后传递过来的文字信息 进行处理
	@Override
	public boolean sendMessage(Message m) {
		// TODO Auto-generated method stub
		try {
			out.writeObject(m);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	//接受服务器传递来的信息
	@Override
	public Message getMessage() {
		// TODO Auto-generated method stub
		try {
			Message message=(Message) in.readObject();
			return message;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
