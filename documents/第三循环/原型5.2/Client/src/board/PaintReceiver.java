package board;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface PaintReceiver extends Remote {
	//服务器得到来自客户端的数据 的接口
	//public void receivePaint(ImgData paint)throws RemoteException;
	public void receiveShapeModel(ShapeModel model)throws RemoteException;

}