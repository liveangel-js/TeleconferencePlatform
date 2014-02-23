package chatServer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;
import data.Message;

public class ChatServer extends Thread {
	
	private ArrayList<ChatHandler>handlers=new ArrayList<ChatHandler>();
	
	
	public ChatServer(){
		
	}

	
	public void run() {
		try {
			ServerSocket server = new ServerSocket(8888);
			while (true) {
				Socket clientSocket = server.accept();
				ChatHandler handler=new  ChatHandler(clientSocket);
				handlers.add(handler);
				handler.start();
				System.out.println("got a  11connection");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	
	public void tellEveryone(Message message) {
		Iterator it = handlers.iterator();
		while (it.hasNext()) {
			try {
				ChatHandler handler=(ChatHandler) it.next();
				handler.sendMessage(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	 class ChatHandler extends Thread {

		private Socket client;
		
		private ObjectInputStream in;

		private ObjectOutputStream out;
		
		

		public ChatHandler(Socket clientSocket) {
			try {
				client = clientSocket;
				out = new ObjectOutputStream(client.getOutputStream());
				in = new ObjectInputStream(client.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		public void run() {
			while(true){
				recieveMessage();
			}
		}
		
		
		public void recieveMessage(){
			try {
				Message message = (Message) in.readObject();
				tellEveryone(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void sendMessage(Message message) {
			try {
				out.writeObject(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChatServer().start();
	}

}
