package board;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class PaintSendImp extends UnicastRemoteObject implements PaintSender{
	//客户端可调用的服务器远程对象
	
	PaintReceiver paintReceiver;//远程客户端对象
//	ImgData imgpPaint; //服务器 用imgPaint暂时存储从客户端发送过来的img数据
	ShapeModel shapeModel;
	
	protected PaintSendImp() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private ArrayList<PaintReceiver>receivers=new ArrayList<PaintReceiver>();

	@Override
	public boolean sendPaint(ShapeModel model) throws RemoteException{
		// TODO Auto-generated method stub
		//   imgpPaint=paint;//服务器将得到的img数据立即存起来
		 shapeModel=model;
		   for(int i=0;i<receivers.size();i++){
		//   receivers.get(i).receivePaint(model);//客户端立即得到最新的img数据
			   System.out.println("向客户端发送数据 "+i);
			   receivers.get(i).receiveShapeModel(model);//客户端立即得到最新的img数据
		   
		   }
		return false;
	}

	@Override
	public boolean addReceiver(PaintReceiver receiver) throws RemoteException{
		// TODO Auto-generated method stub
		receivers.add(receiver);
		System.out.println("客户端添加成功");
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
			System.out.println("白板服务开启");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
