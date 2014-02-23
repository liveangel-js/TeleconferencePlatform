package rmi.client.interfaces;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.rmi.Remote;
import java.rmi.RemoteException;

import data.ImgData;
import data.ShapeModel;
import rmi.data.Paint;


public interface PaintReceiver extends Remote {
	
	public void receiveShapeModel(ShapeModel model)throws RemoteException;

}
