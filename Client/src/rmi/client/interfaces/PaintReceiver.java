package rmi.client.interfaces;
import java.awt.Stroke;
import java.rmi.Remote;
import java.rmi.RemoteException;

import data.ShapeModel;


public interface PaintReceiver extends Remote {
	//服务器得到来自客户端的数据 的接口
	//public void receivePaint(ImgData paint)throws RemoteException;
	public void receiveShapeModel(int boardID, ShapeModel model)throws RemoteException;
	//public void receiveStroke(Stroke stro)throws RemoteException;
	public void receiveUndo(int boardID)throws RemoteException;
	public void receiveRedo(int boardID)throws RemoteException;
	public void receiveNewBoard(int boardID)throws RemoteException;
	public void receiveDeleteBoard(int boardID)throws RemoteException;
}
