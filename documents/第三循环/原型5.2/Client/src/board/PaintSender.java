package board;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PaintSender extends Remote {
	
	
	public static final int PORT =8888;
	
	public static final String NAME ="�װ����";
	//�ͻ��˵õ����Է����������� �Ľӿ�
	public boolean sendPaint(ShapeModel model)throws RemoteException;
	
	//��������
	
	public boolean addReceiver(PaintReceiver reciever)throws RemoteException;

}

