package board;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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

