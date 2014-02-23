package rmi.server.implement;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import data.ShapeModel;

import rmi.client.interfaces.PaintReceiver;
import rmi.server.interfaces.PaintSender;
import voiceServer.Mixer;


public class PaintSendImp extends UnicastRemoteObject implements PaintSender{
	//�ͻ��˿ɵ��õķ�����Զ�̶���
	
	PaintReceiver paintReceiver;//Զ�̿ͻ��˶���
//	ImgData imgpPaint; //������ ��imgPaint��ʱ�洢�ӿͻ��˷��͹�����img����
	ShapeModel shapeModel;
	
	protected PaintSendImp() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private ArrayList<PaintReceiver>receivers=new ArrayList<PaintReceiver>();

	public boolean sendPaint(ShapeModel model) throws RemoteException{
		// TODO Auto-generated method stub
		//   imgpPaint=paint;//���������õ���img��������������
		 shapeModel=model;
		   for(int i=0;i<receivers.size();i++){
		//   receivers.get(i).receivePaint(model);//�ͻ��������õ����µ�img����
			   System.out.println("��ͻ��˷������� "+i);
			   receivers.get(i).receiveShapeModel(model);//�ͻ��������õ����µ�img����
		   
		   }
		return false;
	}

	public boolean addReceiver(PaintReceiver receiver) throws RemoteException{
		// TODO Auto-generated method stub
		receivers.add(receiver);
		System.out.println("�ͻ�����ӳɹ�");
		return true;
	}
	public static void main(String args[]){
		try {
			PaintSender service =new PaintSendImp();
			LocateRegistry.createRegistry(PORT);
			try {
				Naming.rebind( "rmi://"+InetAddress.getLocalHost().getHostAddress()+":"+PORT+"/"+NAME,service);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("�װ������");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//����
		Mixer.userNumber=0;
		ServerSocket ss=null;
		try {
			ss=new ServerSocket(5555);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Socket s=null;
	    while (true){
	    	try{
	    		s=ss.accept();
	    		new Mixer(s).start();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }

	}

}
