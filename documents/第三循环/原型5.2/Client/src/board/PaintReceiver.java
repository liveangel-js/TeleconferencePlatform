package board;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface PaintReceiver extends Remote {
	//�������õ����Կͻ��˵����� �Ľӿ�
	//public void receivePaint(ImgData paint)throws RemoteException;
	public void receiveShapeModel(ShapeModel model)throws RemoteException;

}