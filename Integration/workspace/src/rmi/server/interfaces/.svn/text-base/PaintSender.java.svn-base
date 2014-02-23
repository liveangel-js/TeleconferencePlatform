package rmi.server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import rmi.client.interfaces.PaintReceiver;
import data.ShapeModel;
public interface PaintSender extends Remote {
	
	
	public static final int PORT =8888;
	
	public static final String NAME ="白板服务";
	
	
 //客户端得到来自服务器的数据 的接口
 //public boolean sendPaint(ShapeModel model)throws RemoteException;
   public boolean sendPaint(ShapeModel model,int boardID,int meetingID)throws RemoteException;
   public boolean sendUndo(int boardID)throws RemoteException;
   public boolean sendRedo(int boardID)throws RemoteException;
   public boolean sendRedoModel(ShapeModel model,int boardID,int meetingID)throws RemoteException;
   public boolean sendAllImg()throws RemoteException;
	//撤销方法
	
	// 同步白板
	public ArrayList<ShapeModel> getUpdateList(int boardID)throws RemoteException;
	
	// 同步new白板
	public void sendNewBoard(int boardID)throws RemoteException;
	
	// 同步delete白板	
	public void sendDeleteBoard(int boardID)throws RemoteException;
	
	public boolean addReceiver(PaintReceiver reciever)throws RemoteException;


}







