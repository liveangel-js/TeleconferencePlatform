package board;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PaintReceiverImp extends UnicastRemoteObject 
    implements PaintReceiver{//服务器可调用的客户端远程对象
   
//	PaintBoard paintBoard;
//	ImgData imgPaint; //客户端 用imgPaint暂时存储从服务器发送过来的img数据
	ShapeModel shapeModel;
	
	protected PaintReceiverImp() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


//	public void receivePaint(ImgData paint) throws RemoteException {
		// TODO Auto-generated method stub
	//   imgPaint=paint;
	 //然后将paint显示到paintBoard的drawPanel上
	  // paintBoard.drawPanel.bufImg=(BufferedImage)imgPaint;
	//}

	public void receiveShapeModel(ShapeModel model) throws RemoteException {
		// TODO Auto-generated method stub
		shapeModel=model;
	}

}

