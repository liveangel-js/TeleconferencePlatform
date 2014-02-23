package board;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PaintSender extends Remote {
	
	
	public static final int PORT =8888;
	
	public static final String NAME ="白板服务";
	//客户端得到来自服务器的数据 的接口
	public boolean sendPaint(ShapeModel model)throws RemoteException;
	
	//撤销方法
	
	public boolean addReceiver(PaintReceiver reciever)throws RemoteException;

}

