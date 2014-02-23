package rmi.server.interfaces;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.rmi.Remote;
import java.rmi.RemoteException;

import data.paint.ImgData;
import data.paint.ShapeModel;

import rmi.client.interfaces.PaintReceiver;
import rmi.data.Paint;


public interface PaintSender extends Remote {
	
	
	public static final int PORT =8888;
	
	public static final String NAME ="白板服务";
	//客户端得到来自服务器的数据 的接口
	public boolean sendPaint(ShapeModel model)throws RemoteException;
	
	//撤销方法
	
	public boolean addReceiver(PaintReceiver reciever)throws RemoteException;

}
