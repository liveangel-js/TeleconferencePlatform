package rmi.server.implement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import manager.ServiceManager;
import data.ShapeModel;
import rmi.client.interfaces.PaintReceiver;
import rmi.server.interfaces.FileShareService;
import rmi.server.interfaces.PaintSender;
import sql.BoardRecordSQL;
import sql.Interface.BoardElementDataInterface;

import voiceServer.Mixer;


public class PaintSendImp extends UnicastRemoteObject implements PaintSender{
	//客户端可调用的服务器远程对象
	
	
	PaintReceiver paintReceiver;//远程客户端对象
//	ImgData imgpPaint; //服务器 用imgPaint暂时存储从客户端发送过来的img数据
	ShapeModel shapeModel;
	
	public PaintSendImp() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private ArrayList<PaintReceiver>receivers=new ArrayList<PaintReceiver>();


	
  public boolean sendPaint(ShapeModel model,int boardID,int meetingID)throws RemoteException{
		 shapeModel=model;
		   for(int i=0;i<receivers.size();i++){
			   System.out.println("向客户端发送数据 "+i);
			   try {
				 
			  receivers.get(i).receiveShapeModel(boardID,model);//客户端立即得到最新的img数据
			   } catch (Exception e) {
				   receivers.remove(i);
				   i--;
			   }
			   
		   }
		 new BoardRecordSQL().writeNewImgeData(meetingID, boardID, "system", model);
		return true;
	}

	public boolean addReceiver(PaintReceiver receiver) throws RemoteException{
		// TODO Auto-generated method stub
		receivers.add(receiver);
		System.out.println("客户端添加成功");
		return true;
	}
	@Override
	public boolean sendUndo(int boardID) throws RemoteException {
		// TODO Auto-generated method stub
		BoardElementDataInterface sql = new BoardRecordSQL();
		for(int i=0;i<receivers.size();i++){
			//   receivers.get(i).receivePaint(model);//客户端立即得到最新的img数据
				   
				System.out.println("向客户端发送undo请求 "+i);
				try {
				   receivers.get(i).receiveUndo(boardID);//客户端立即得到最新的img数据
				} catch (Exception e) {
					receivers.remove(i);
					i--;
				}  
			   }
		System.out.println("删除白板元素------------------------->"+sql.getNewestID(boardID));
		sql.deleteImageData(boardID,sql.getNewestID(boardID));
		return true;
	}

	@Override
	public boolean sendRedo(int boardID) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i=0;i<receivers.size();i++){
			//   receivers.get(i).receivePaint(model);//客户端立即得到最新的img数据
				   System.out.println("向客户端发送redo请求 "+i);
				   try {
					   receivers.get(i).receiveRedo(boardID);//客户端立即得到最新的img数据
				   } catch (Exception e) {
					   receivers.remove(i);
					   i--;
				   }
			   }
		return true;
	}

	@Override
	public boolean sendRedoModel(ShapeModel model, int boardID, int meetingID)
			throws RemoteException {
		new BoardRecordSQL().writeNewImgeData(meetingID, boardID, "system", model);
		System.out.println("redo白板元素------------------------>");
		return true;
	}
	
	// 同步白板
	@Override
	public ArrayList<ShapeModel> getUpdateList(int boardID) throws RemoteException{
		// TODO Auto-generated method stub
		BoardElementDataInterface sql = new BoardRecordSQL();
		ArrayList<Integer> idList = sql.getUpdateList(boardID, 0);
		ArrayList<ShapeModel>modelList =new ArrayList<ShapeModel>();
		for(int i=0;i<idList.size();++i){
			modelList.add((ShapeModel)sql.readImageData(idList.get(i)));
		}
		return modelList;
	}
	 
	


	// 同步new白板
	@Override
	public void sendNewBoard(int boardID) throws RemoteException {
		for(int i=0;i<receivers.size();i++){
			//   receivers.get(i).receivePaint(model);//客户端立即得到最新的img数据
				   System.out.println("向客户端发送new白板请求 "+i);
				   try {
					   receivers.get(i).receiveNewBoard(boardID);//客户端立即得到最新的img数据
					} catch (Exception e) {
						// TODO: handle exception
						receivers.remove(i);
						i--;
					}
				   
			   }
		
	}
	
	// 同步delete白板
	@Override
	public void sendDeleteBoard(int boardID) throws RemoteException {
		for(int i=0;i<receivers.size();i++){
			//   receivers.get(i).receivePaint(model);//客户端立即得到最新的img数据
				   System.out.println("向客户端发送new白板请求 "+i);
				   try {
					   receivers.get(i).receiveDeleteBoard(boardID);
					} catch (Exception e) {
						receivers.remove(i);
						i--;
					}
				   
			   }   
		}

	@Override
	public boolean sendAllImg() throws RemoteException {
		// TODO Auto-generated method stub
		
		return false;
	}
	
	


}
