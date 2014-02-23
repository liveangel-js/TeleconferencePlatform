package rmi.server.interfaces;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.rmi.Remote;
import java.rmi.RemoteException;

import data.ImgData;
import data.ShapeModel;

import rmi.client.interfaces.PaintReceiver;
import rmi.data.Paint;



public interface PaintSender extends Remote {
	public static final int PORT =8888;
	
	public static final String NAME ="�װ����";
	//�ͻ��˿ɵ��õķ�����Զ�̶���Ľӿ�
	public boolean sendPaint(ShapeModel model)throws RemoteException;
	
	//��������
	
	public boolean addReceiver(PaintReceiver receiver)throws RemoteException;

}
