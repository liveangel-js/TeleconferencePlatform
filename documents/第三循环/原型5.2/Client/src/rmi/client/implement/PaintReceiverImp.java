package rmi.client.implement;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.print.attribute.standard.Media;

import data.paint.ImgData;
import data.paint.ShapeModel;

import logic.PaintBoard;

import rmi.client.interfaces.PaintReceiver;
import rmi.data.Paint;
import rmi.server.interfaces.PaintSender;

@SuppressWarnings("serial")
public class PaintReceiverImp extends UnicastRemoteObject 
    implements PaintReceiver{//�������ɵ��õĿͻ���Զ�̶���
   
//	PaintBoard paintBoard;
//	ImgData imgPaint; //�ͻ��� ��imgPaint��ʱ�洢�ӷ��������͹�����img����
	ShapeModel shapeModel;
	
	protected PaintReceiverImp() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


//	public void receivePaint(ImgData paint) throws RemoteException {
		// TODO Auto-generated method stub
	//   imgPaint=paint;
	 //Ȼ��paint��ʾ��paintBoard��drawPanel��
	  // paintBoard.drawPanel.bufImg=(BufferedImage)imgPaint;
	//}

	public void receiveShapeModel(ShapeModel model) throws RemoteException {
		// TODO Auto-generated method stub
		shapeModel=model;
	}

}
