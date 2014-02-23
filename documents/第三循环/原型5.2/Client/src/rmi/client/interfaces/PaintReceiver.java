package rmi.client.interfaces;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.rmi.Remote;
import java.rmi.RemoteException;

import data.paint.ImgData;
import data.paint.ShapeModel;

import rmi.data.Paint;


public interface PaintReceiver extends Remote {
	//�������õ����Կͻ��˵����� �Ľӿ�
	//public void receivePaint(ImgData paint)throws RemoteException;
	public void receiveShapeModel(ShapeModel model)throws RemoteException;

}
