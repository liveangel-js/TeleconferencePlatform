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


	// ���ӣ��� ������� ����Ϣ��socket���а�
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
	
	
	//���� ����� ���룬Ȼ�󴫵ݹ�����������Ϣ ���д���
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
	
	
	//���ܷ���������������Ϣ
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
