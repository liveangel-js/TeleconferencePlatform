package board;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;

import data.ShapeModel;
import data.Point;
import rmi.client.interfaces.PaintReceiver;
import rmi.server.interfaces.FileShareService;
import rmi.server.interfaces.MeetingControlService;
import rmi.server.interfaces.PaintSender;
import view.Logging;
//import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.awt.font.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.font.*;
import logic.Setting;
import manager.ClientManager;
import manager.ServiceManager;
//latest version
@SuppressWarnings("serial")
public class PaintBoard extends JPanel implements ActionListener,ChangeListener {
	boolean isSender = false; // 判断是否为发送者
	MeetingControlService meetingcontrol; //客户端可调用的远程服务器对象
	PaintSender paintSender;//客户端可调用的远程服务器对象
	PaintReceiver paintReceiver;//服务器可调用的远程客户端对象
	
	
	private String ButtonNameBasic[]={"鼠标","铅笔","直线","矩形","圆角矩形","椭圆","橡皮擦",
			 "菱形","文字"};
	private String ButtonNameUML[]={"联系箭头","组合箭头","依赖箭头","实现箭头","类图","包图",
			"开始状态","结束状态","时间柱","小人","节点","组件"};
	private JToggleButton jToggleButtonBasic[];
	private JSpinner lineWidthSelect;
	public int number = 1;  //记录画笔粗细
	private JButton jreundo[];
	private JButton newBoard,openFile,saveFile,saveAs,deleteBoard;
	private ButtonGroup reundoGroup;
	private JToggleButton jToggleButtonUML[];
	private ButtonGroup buttonGroup;
	private JPanel jPanel[] = new JPanel[5];// 0内层,1绘图区,2工具箱,3色彩,4属性栏
	private Icon toolUML[]=new ImageIcon[12];
	private String toolnameUML[] = { 
	"image/association.png", "image/composition.png",
	"image/dependency.png", "image/generalization.png", 
	"image/class.png", "image/package.png",
	"image/initial.png", "image/finalState.png", "image/timeShape.png",
	 "image/actor.png",
	"image/node.png", "image/component.png" };
	private Icon toolBasic[]=new ImageIcon[9];
	private String toolnameBasic[] = { "image/mousepointer.png",		
	"image/pensil.png", "image/line.png",
	"image/rectangle.png", "image/roundrectangle.png", 
	"image/oval.png", "image/eraser.png",
	"image/diamond.png", "image/word.png",
	 };
	@SuppressWarnings("unused")
	private int i, j;
	int meetingID;//会议ID
	boolean meetingState = false;// 会议状态
	private int boardIndex=0,currentID=0;////boardIndex记录白板索引，current记录当前白板索引
	private int drawMethod = 1;//记录要画的是哪种图形 
	private int draw_panel_width = 1500;//画板默认宽度
	private int draw_panel_height = 1500;//画板默认高度

    
    private HashMap<Integer,DrawPanel>drawPanelList;
    private HashMap<Integer,MyJScrollPane>scrollPaneList;
    private JTabbedPane myJTabbedPane;//选项板
	private Stroke stroke;

	private String isFilled;
	private Paint color_border; 
	private boolean canDraw=false;
	private boolean canReceive=false;
		
	//设定JMenuBar,并产生JMenuItem、设置快捷键	
	public PaintBoard() {
		
		String ip=Setting.get("ip");;
		String urlMeetingControl="rmi://"+ip+":"+MeetingControlService.PORT+"/"+MeetingControlService.NAME;
		try {
			meetingcontrol =(MeetingControlService) Naming.lookup(urlMeetingControl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} 
	    String urlPaintsender="rmi://"+ip+":"+PaintSender.PORT+"/"+PaintSender.NAME;
		
	    try {
			paintSender=(PaintSender) Naming.lookup(urlPaintsender);
			paintReceiver=new PaintReceiverImp();
			paintSender.addReceiver(paintReceiver);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		try {
			meetingID = meetingcontrol.getMeetingID();
			meetingState = (meetingcontrol.getMeetingState()!=0);
			System.out.println("meetingID----------->"+meetingID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		color_border=new Color(0,0,0);//将画笔设置为黑色
	
		this.setLayout(new BorderLayout());
		for (i = 0; i < 5; i++)
			jPanel[i] = new JPanel();
	
		@SuppressWarnings("unused")
		BasicStroke stroke2 = (BasicStroke) stroke;
		stroke = new BasicStroke(1, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER);

		buttonGroup = new ButtonGroup();
		//工具栏
		JPanel jp1=new JPanel();
		jToggleButtonBasic = new JToggleButton[ButtonNameBasic.length];
		jp1.setBorder(new TitledBorder(null, "toolbar",
			TitledBorder.LEFT, TitledBorder.TOP));
		for (int i = 0; i < ButtonNameBasic.length; i++) {
			toolBasic[i] = new ImageIcon(toolnameBasic[i]);
			jToggleButtonBasic[i] = new JToggleButton(toolBasic[i]);
			jToggleButtonBasic[i].addActionListener(this);
			jToggleButtonBasic[i].setFocusable(false);
			jToggleButtonBasic[i].setEnabled(false);
			//jToggleButtonBasic[i].setSize(20, 20);
			buttonGroup.add(jToggleButtonBasic[i]);
		}
		jp1.add(jToggleButtonBasic[0]);
		jp1.add(jToggleButtonBasic[1]);
		jp1.add(jToggleButtonBasic[2]);
		jp1.add(jToggleButtonBasic[3]);
		jp1.add(jToggleButtonBasic[4]);
		jp1.add(jToggleButtonBasic[5]);
		jp1.add(jToggleButtonBasic[6]);
		jp1.add(jToggleButtonBasic[7]);	
		jp1.add(jToggleButtonBasic[8]);
		jToggleButtonBasic[2].setSelected(true);
		
	    jToggleButtonBasic[0].setToolTipText("mouse pointer");
	    jToggleButtonBasic[1].setToolTipText("pensil");
	    jToggleButtonBasic[2].setToolTipText("line");
	    jToggleButtonBasic[3].setToolTipText("rectangle");
	    jToggleButtonBasic[4].setToolTipText("round rectangle");
	    jToggleButtonBasic[5].setToolTipText("oval");
	    jToggleButtonBasic[6].setToolTipText("eraser");
	    jToggleButtonBasic[7].setToolTipText("diamond");
	    jToggleButtonBasic[8].setToolTipText("font");
	 	
		//UML图元
		jToggleButtonUML=new JToggleButton[ButtonNameUML.length];

		for (int i = 0; i < ButtonNameUML.length; i++) {
			toolUML[i] = new ImageIcon(toolnameUML[i]);
			jToggleButtonUML[i] = new JToggleButton(toolUML[i]);
			jToggleButtonUML[i].addActionListener(this);
			jToggleButtonUML[i].setFocusable(false);
			jToggleButtonUML[i].setEnabled(false);
			//jToggleButtonUML[i].setSize(20, 20);
			buttonGroup.add(jToggleButtonUML[i]);
		}
		jp1.add(jToggleButtonUML[0]);
		jp1.add(jToggleButtonUML[1]);
		jp1.add(jToggleButtonUML[2]);
		jp1.add(jToggleButtonUML[3]);
		jp1.add(jToggleButtonUML[4]);
		jp1.add(jToggleButtonUML[5]);
		jp1.add(jToggleButtonUML[6]);
		jp1.add(jToggleButtonUML[7]);
		jp1.add(jToggleButtonUML[8]);
		jp1.add(jToggleButtonUML[9]);
		jp1.add(jToggleButtonUML[10]);
		jp1.add(jToggleButtonUML[11]);
		jp1.setLayout(new GridLayout(15, 2, 2, 2));
		jp1.setBounds(new Rectangle(0, 0, 80, 550));
		jp1.setPreferredSize(new Dimension(80,550));
		
		jToggleButtonUML[0].setToolTipText("association");
		jToggleButtonUML[1].setToolTipText("composition");
		jToggleButtonUML[2].setToolTipText("dependency");
		jToggleButtonUML[3].setToolTipText("generalization");
		jToggleButtonUML[4].setToolTipText("class");
		jToggleButtonUML[5].setToolTipText("package");
		jToggleButtonUML[6].setToolTipText("initial state");
		jToggleButtonUML[7].setToolTipText("final state");
		jToggleButtonUML[8].setToolTipText("time shape");
		jToggleButtonUML[9].setToolTipText("actor shape");
		jToggleButtonUML[10].setToolTipText("node");
		jToggleButtonUML[11].setToolTipText("component");
		
		//画笔粗细
		//JPanel jp2=new JPanel();
	    lineWidthSelect = new JSpinner();
		lineWidthSelect.setValue(new Integer(1));
		lineWidthSelect.addChangeListener(this);
		lineWidthSelect.setEnabled(false);
		lineWidthSelect.setToolTipText("line width");
		jreundo=new JButton[2];
		reundoGroup = new ButtonGroup();
		jreundo[0]=new JButton();
		jreundo[0].setToolTipText("undo");
		ImageIcon undoIcon=new ImageIcon("image/undo.png");
		jreundo[0].setIcon(undoIcon);
		jreundo[0].setMnemonic(KeyEvent.VK_Z);
		jreundo[0].addActionListener(this);
		jreundo[0].setEnabled(false);
		jreundo[1]=new JButton();
		jreundo[1].setToolTipText("redo");
		ImageIcon redoIcon=new ImageIcon("image/redo.png");
		jreundo[1].setIcon(redoIcon);
		jreundo[1].addActionListener(this);
		jreundo[1].setEnabled(false);
		reundoGroup.add(jreundo[0]);
		reundoGroup.add(jreundo[1]);
		
		newBoard=new JButton();
		newBoard.setToolTipText("new Board");
		ImageIcon newIcon=new ImageIcon("image/newBoard.png");
		newBoard.setIcon(newIcon);
		newBoard.setEnabled(false);
		openFile=new JButton();
		openFile.setToolTipText("open file");
		ImageIcon openIcon=new ImageIcon("image/open.png");
		openFile.setIcon(openIcon);
		openFile.setEnabled(false);
		saveFile=new JButton();
		saveFile.setToolTipText("save as source file");
		ImageIcon saveIcon=new ImageIcon("image/save.png");
		saveFile.setIcon(saveIcon);
		saveFile.setEnabled(false);
		saveAs=new JButton();
		ImageIcon saveAsIcon=new ImageIcon("image/saveAs.png");
		saveAs.setIcon(saveAsIcon);
		saveAs.setToolTipText("save as png");
		saveAs.setEnabled(false);
		deleteBoard=new JButton();
		deleteBoard.setToolTipText("delete board");
		ImageIcon deleteIcon=new ImageIcon("image/delete.png");
		deleteBoard.setIcon(deleteIcon);
		deleteBoard.setEnabled(false);
		
		newBoard.addActionListener(this);
		saveFile.addActionListener(this);
		openFile.addActionListener(this);
		saveAs.addActionListener(this);
		deleteBoard.addActionListener(this);
		
		jp1.add(lineWidthSelect);
		jp1.add(jreundo[0]);
		jp1.add(jreundo[1]);
		jp1.add(newBoard);
		jp1.add(openFile);
		jp1.add(saveFile);
		jp1.add(saveAs);
		jp1.add(deleteBoard);
	
		jPanel[2].add(jp1);
	
		drawPanelList=new HashMap<Integer,DrawPanel>();
		scrollPaneList=new HashMap<Integer,MyJScrollPane>();
		myJTabbedPane=new JTabbedPane();
		myJTabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(myJTabbedPane.getSelectedComponent()!=null){
					currentID = ((MyJScrollPane)myJTabbedPane.getSelectedComponent()).id;	
					DrawPanel temp =drawPanelList.get(currentID);
					if((!temp.shapeModelList.isEmpty())&&canDraw){
						jreundo[0].setEnabled(true);
					}else{
						jreundo[0].setEnabled(false);
					}
					if ((!temp.modelStack.empty())&&canDraw) {
						jreundo[1].setEnabled(true);
					}else{
						jreundo[1].setEnabled(false);
					}
				}
			}
		});


		jPanel[0].setLayout(new BorderLayout());
		jPanel[0].add(myJTabbedPane, BorderLayout.CENTER);
		jPanel[0].add(jPanel[2], BorderLayout.WEST);

		this.add(jPanel[0], BorderLayout.CENTER);
		this.setSize(653,546);
		if(true==meetingState){
			try {
				ArrayList<Integer> boardList = meetingcontrol.getBoardList();
				System.out.println("getboardList------>"+boardList.size());
				for(int i=0;i<boardList.size();i++){
					newBoardFromServer(boardList.get(i));
				}
				for(int i=0;i<boardList.size();i++){
					if(boardIndex <= (boardList.get(i)%100)){
						boardIndex = (boardList.get(i)%100) + 1;
					}
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("当前无会议...");
		}
		
		
		show();
	}
	
	public void sendBoardImg(String fileName){
		try {
			int SIZE=1024*1024;
			File file = new File(fileName);
			FileShareService service = ServiceManager.getFileShareService();
			boolean exsit=service.isFileExist(file.getName());
			if(exsit){
				//JOptionPane.showMessageDialog(null, file.getName()+" file with same name already exsit~!");
			    return;
			}
			
			InputStream in = new FileInputStream(file);
			int length=(int) file.length();
			int value=0;


			for (int i = 0; i < length - SIZE; i += SIZE) {
				byte b[] = new byte[SIZE];
				in.read(b);
				service.upload(file.getName(), b);
				value+=SIZE;

			}
			byte b[] = new byte[length-value];
			in.read(b);
			in.close();
			service.upload(file.getName(), b);
			service.completeUpload(file.getName(), ClientManager.getUserName());
		} catch (RemoteException e) {
			 e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "file path wrong~!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IO error");
		}
	}

	
	
	public void setAccess(int access){
		if(access==0){ //既不能接收，也不能画
			canDraw=false;
			canReceive=false;
			resetEnabledFalse();
		}
		else if(access==1){//能接收，不能画
			canDraw=false;
			canReceive=true;
			resetEnabledFalse();
		}
		else if(access==2){//能接受，能画，即排在笔序第一位
			canDraw=true;
			canReceive=true;
			resetEnabledTrue();
		}
	}
	
	/**
	 * start/stop meeting
	 *@author masking
	 */
	public void onStartMeeting(){
		meetingState = true;
		try {
			meetingID = meetingcontrol.getMeetingID();
			System.out.println("meetingID----------->"+meetingID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendImgOnStopMeeting(){
		if(!drawPanelList.isEmpty()){
			Iterator itr = drawPanelList.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry) itr.next();
				DrawPanel indexPanel = (DrawPanel) entry.getValue(); //(DrawPanel) entry.getValue();
				indexPanel.filename = "BoardImg/"+String.valueOf(indexPanel.boardID)+".png";
				saveToPng(indexPanel.boardID);			
				sendBoardImg(indexPanel.filename);
			}
		}
	}
	
	public void onStopMeeting(){
		meetingState = false;
		boardIndex=0;
		currentID=0;
		drawPanelList=new HashMap<Integer,DrawPanel>();
		scrollPaneList=new HashMap<Integer,MyJScrollPane>();
		myJTabbedPane.removeAll();
		System.out.println("removeAll----------->removeAll");
	
	}

	
	public void resetEnabledFalse(){
		  for (int i = 0; i < ButtonNameBasic.length; i++){
				jToggleButtonBasic[i].setEnabled(false);
			  }
			  for(int i=0;i<ButtonNameUML.length;i++){
				  jToggleButtonUML[i].setEnabled(false);
			  }
					lineWidthSelect.setEnabled(false);
					if(currentID>0){
						DrawPanel temp =drawPanelList.get(currentID);
						if((!temp.shapeModelList.isEmpty())&&canDraw){
							jreundo[0].setEnabled(true);
						}else{
							jreundo[0].setEnabled(false);
						}
						if ((!temp.modelStack.empty())&&canDraw) {
							jreundo[1].setEnabled(true);
						}else{
							jreundo[1].setEnabled(false);
						}
					}

					newBoard.setEnabled(false);
					openFile.setEnabled(false);
					saveFile.setEnabled(false);
					saveAs.setEnabled(false);
					deleteBoard.setEnabled(false);
	}
	
	public void resetEnabledTrue(){
	  for (int i = 0; i < ButtonNameBasic.length; i++){
		jToggleButtonBasic[i].setEnabled(true);
	  }
	  for(int i=0;i<ButtonNameUML.length;i++){
		  jToggleButtonUML[i].setEnabled(true);
	  }
			lineWidthSelect.setEnabled(true);
			if(currentID>0){
				DrawPanel temp =drawPanelList.get(currentID);
				if((!temp.shapeModelList.isEmpty())&&canDraw){
					jreundo[0].setEnabled(true);
				}else{
					jreundo[0].setEnabled(false);
				}
				if ((!temp.modelStack.empty())&&canDraw) {
					jreundo[1].setEnabled(true);
				}else{
					jreundo[1].setEnabled(false);
				}
			}
			newBoard.setEnabled(true);
			openFile.setEnabled(true);
			saveFile.setEnabled(true);
			saveAs.setEnabled(true);
			deleteBoard.setEnabled(true);
	}
	
     //存储
	public boolean save(int _boardID) {
		
	 	   JFileChooser chooser = new JFileChooser(); 
	  	  chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY); 
	  	   chooser.setFileFilter(new FileFilter() {			
	 			@Override
	 			public String getDescription() {
	 				return "图像文件  .png";
	 			}
	 			
	 			@Override
	 			public boolean accept(File f) {
	 				if(f.isDirectory())
	 					return true;
	 				if (f.getName().toLowerCase().endsWith(".png")){      
	 			        return true;      
	 			      }else{      
	 			        return false;      
	 			      } 
	 			}
	 		});
	  	   	chooser.showSaveDialog(PaintBoard.this);
	 		if(chooser.getSelectedFile()!=null){
	 			drawPanelList.get(_boardID).filename = chooser.getSelectedFile().getAbsolutePath()+".png";
	 			return true;
	 		}
	 			return false;
	
	}
	
	//监听工具栏按钮事件
	public void actionPerformed(ActionEvent e) {
		for (i = 0; i < ButtonNameBasic.length; i++) {
			if (e.getSource() == jToggleButtonBasic[i]) {
				drawMethod = i;
				drawPanelList.get(currentID).clear();
				drawPanelList.get(currentID).repaint();
			}
		}
		for (i = 0; i < ButtonNameUML.length; i++) {
			if (e.getSource() == jToggleButtonUML[i]) {
				drawMethod = i+9;
				drawPanelList.get(currentID).clear();
				drawPanelList.get(currentID).repaint();
				System.out.println("id:"+(i+9));
			}
		}
		BasicStroke stroke2 = (BasicStroke) stroke;
       //监听画笔粗细的改变
		if (e.getSource() == lineWidthSelect)
			stroke = new BasicStroke(stroke2.getLineWidth(),
					BasicStroke.CAP_ROUND, stroke2.getLineJoin(),
					stroke2.getMiterLimit(), stroke2.getDashArray(),
					stroke2.getDashPhase());
       if(e.getSource()==jreundo[0]){
    	   drawPanelList.get(currentID).undo();
       }
       else if(e.getSource()==jreundo[1]){
    	   drawPanelList.get(currentID).redo();
       }
       else if (e.getSource() == newBoard) {// 新建白板
	    	
	    	try {
				meetingcontrol.addBoardID(meetingID*100+boardIndex);
				isSender = true;
				paintSender.sendNewBoard(meetingID*100+boardIndex);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
       
       else if (e.getSource() ==openFile) {// 打开文件
    	   
    	   JFileChooser chooser = new JFileChooser();
    	   chooser.setFileFilter(new FileFilter() {			
			@Override
			public String getDescription() {
				return "UML源文件  .msk";
			}
			
			@Override
			public boolean accept(File f) {
				if(f.isDirectory())
					return true;
				if (f.getName().toLowerCase().endsWith(".msk")){      
			        return true;      
			      }else{      
			        return false;      
			      }  
			}
		});
    	chooser.showOpenDialog(PaintBoard.this);

//		System.out.println(chooser.getSelectedFile().getAbsolutePath());
			if(chooser.getSelectedFile()!=null){
				drawPanelList.get(currentID).openfile(chooser.getSelectedFile().getAbsolutePath());
			}
		} 
       else if (e.getSource() == saveFile) {// 存储图纸
    	   saveToMsk(currentID);
		}
       else if (e.getSource() == saveAs) {// 另存图纸
			save(currentID);
			saveToPng(currentID);
    	 
		}	
       else if(e.getSource()==deleteBoard){//删除 白板
    	   
    	    int toDelete=JOptionPane.showConfirmDialog(null, 
    	    		"do you want to delete the current board?");
    	    if(toDelete==JOptionPane.YES_OPTION){
    	        
    	        try {
    				meetingcontrol.deleteBoardID(currentID);
    				isSender = true;
    				paintSender.sendDeleteBoard(currentID);
    			} catch (RemoteException e1) {
    				e1.printStackTrace();
    			}
    	    }
    	    
          }
	   }
	
	/**
	 * 同步时add 白板 masking
	 * @param _boardID
	 * 
	 */
	
	private void newBoardFromServer(int _boardID) {
		boardIndex++;
    	DrawPanel tmpPanel = new DrawPanel(_boardID);
    	drawPanelList.put(_boardID, tmpPanel);
    	scrollPaneList.put(_boardID, new MyJScrollPane(_boardID,tmpPanel));
    	myJTabbedPane.add("board"+(_boardID),scrollPaneList.get(_boardID));
    	scrollPaneList.get(_boardID).setVerticalScrollBarPolicy(
    		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
    	scrollPaneList.get(_boardID).setHorizontalScrollBarPolicy(
    		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    	drawPanelList.get(_boardID).setBounds(new Rectangle(2, 2, draw_panel_width,
    		draw_panel_height));
    	myJTabbedPane.setSelectedIndex(myJTabbedPane.getTabCount()-1);
	}
	
	/**
	 * 同步时delete 白板 masking
	 * 
	 */
	private void deleteBoardFromServer(int boardID) {
        drawPanelList.remove(boardID);
        scrollPaneList.remove(boardID);
        for(int i=0;i<myJTabbedPane.getTabCount();i++){
        	if(((MyJScrollPane)myJTabbedPane.getComponentAt(i)).id == boardID){
        		myJTabbedPane.remove(i);
        		break;
        	}
        }
	}
	
	// 保存为图片
	private void saveToPng(int _boradID){
		try {
			int dotpos = drawPanelList.get(_boradID).filename.lastIndexOf('.');
			ImageIO.write(drawPanelList.get(_boradID).bufImg, 
					drawPanelList.get(_boradID).filename
					.substring(dotpos + 1), 
					new File(drawPanelList.get(_boradID).filename));
		} catch (IOException even) {
			JOptionPane.showMessageDialog(null, even.toString(), "无法存储文档",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	// 保存为源文件
	private void saveToMsk(int _boradID){
 	   JFileChooser chooser = new JFileChooser(); 
 	  chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY); 
 	   chooser.setFileFilter(new FileFilter() {			
			@Override
			public String getDescription() {
				return "UML源文件  .msk";
			}
			
			@Override
			public boolean accept(File f) {
				if(f.isDirectory())
					return true;
				if (f.getName().toLowerCase().endsWith(".msk")){      
			        return true;      
			      }else{      
			        return false;      
			      } 
			}
		});
 	   	chooser.showSaveDialog(PaintBoard.this);
		if(chooser.getSelectedFile()!=null){
			try {
				FileOutputStream fos =new FileOutputStream(chooser.getSelectedFile().getAbsolutePath()+".msk");
				ObjectOutputStream oos =new ObjectOutputStream(fos);
				oos.writeObject(drawPanelList.get(_boradID).shapeModelList);
				oos.flush();
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	@Override//监听通过按钮改变画笔粗细
	public void stateChanged(ChangeEvent e) {
		number = Integer.parseInt(lineWidthSelect.getValue().toString());
		if (number <= 0) {
			lineWidthSelect.setValue(new Integer(1));
			number = 1;
		}
		BasicStroke stroke2 = (BasicStroke) stroke;
		stroke = new BasicStroke(number, stroke2.getEndCap(),
				stroke2.getLineJoin(), stroke2.getMiterLimit(),
				stroke2.getDashArray(), stroke2.getDashPhase());
	}
	
	public int getLineWithSelect(){
		return Integer.parseInt(lineWidthSelect.getValue().toString());
	}
	
	public void setLineWidth(int lineWidth){
		lineWidthSelect.setValue(lineWidth);
		BasicStroke stroke2 = (BasicStroke) stroke;
		stroke = new BasicStroke(lineWidth, stroke2.getEndCap(),
				stroke2.getLineJoin(), stroke2.getMiterLimit(),
				stroke2.getDashArray(), stroke2.getDashPhase());
	}

//画图面板	
public class DrawPanel extends JPanel implements MouseListener,
	MouseMotionListener, ActionListener, ChangeListener {

	
	// 中央画布
		
		BufferedImage imgPaint; //客户端 用imgPaint暂时存储从服务器发送过来的img数据
		//int counter = 0;
public BufferedImage bufImg;//  记录最新画面，并在此上作画
private BufferedImage bufImg_data[];//   记录所有画出圆面，索引值越大越新，最大为最新      
private JLabel jlbImg;
//count记录可重做次数,Press判断坐标是否为新起点

private int x1 = -1, y1 = -1, x2, y2,  press,
		   click;
//private int count, redo_lim; 
/////////private int lastIndex;

@SuppressWarnings("unused")
private float data[] = { 5 };
private Ellipse2D.Double ellipse2D_pan = new Ellipse2D.Double();
private BasicStroke basicStroke_pen = new BasicStroke(1,
		BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
///private BasicStroke basicStroke_select = new BasicStroke(1,
//		BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10, data, 0);

protected ArrayList<ShapeModel>shapeModelList=new ArrayList<ShapeModel>();
private Stack<ShapeModel>modelStack=new Stack<ShapeModel>();
private Shape shape;
private ShapeModel shapeModel;
private MyLine myLine=new MyLine();//直线
private MyRectangle myRectangle=new MyRectangle();//矩形
private MyRoundRectangle myRoundRectangle=new MyRoundRectangle();//圆角矩形
//private MyBlackCircle myBlackCircle=new MyBlackCircle();//黑心圆
private MyOval myOval=new MyOval();//椭圆
private MyLin myLin = new MyLin();// 凌形
private MyClassShape myClassShape=new MyClassShape();//类图
private MyPackageShape myPackageShape=new MyPackageShape();//包图
private MyActorShape myActorShape=new MyActorShape();//小人
private MyAssoShape myAssoShape=new MyAssoShape();//联系
private MyAggrShape myAggrShape=new MyAggrShape();//组合
private MyUsageShape myUsageShape=new MyUsageShape();//使用,依赖
private MyGeneShape myGeneShape=new MyGeneShape();//继承
private MyCompoShape myCompoShape=new MyCompoShape();//组件
private MyNodeShape myNodeShape=new MyNodeShape();//节点
private MyTimeShape myTimeShape=new MyTimeShape();//时间柱
private MyStartState myStartState=new MyStartState();//开始状态
private MyEndState myEndState=new MyEndState();//结束状态
private MyPensil myPensil=new MyPensil();//铅笔
private MyEraser myEraser=new MyEraser();//橡皮
private MyFont myFont=new MyFont();//文字

public String filename; //保存 文件名
private JTextField textField_font = new JTextField("Sanserif", 16),
		textField_word = new JTextField("JAVA", 16);
private int size = 18;
String fieldword;
String fieldfont;
boolean FontToPaint=false;
double sw ;
double sh ;
boolean lineStart=false;
int lineStartIndex,lineEndIndex;
private JSpinner fontsize = new JSpinner();
private JDialog jDialog;
//private JCheckBox bold, italic;
private JButton ok, cancel;

private int boardID; //drawpanel id

public DrawPanel(int _boardID) {
	this.boardID = _boardID;
	
	//bufImg_data = new ImgData[1000];
	bufImg = new ImgData(draw_panel_width, draw_panel_height,
			ImgData.TYPE_3BYTE_BGR);
	jlbImg = new JLabel(new ImageIcon(bufImg));// 在JLabel上放置bufImg，用來绘图

	this.setSize(draw_panel_width,draw_panel_height);
	this.setLayout(null);
	this.add(jlbImg);
	jlbImg.setBounds(new Rectangle(0, 0, draw_panel_width,
			draw_panel_height));

	jreundo[0].setEnabled(false);
	jreundo[1].setEnabled(false);

	// 画出空白//
	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	g2d_bufImg.setPaint(Color.white);
	g2d_bufImg.fill(new Rectangle2D.Double(0, 0, draw_panel_width,
			draw_panel_height));


	Graphics2D g2d_bufImgdata= (Graphics2D) bufImg
			.getGraphics();
	g2d_bufImgdata.drawImage(bufImg, 0, 0, this);
	
	// Font
	jDialog = new JDialog(new JFrame(),"請選擇文字、字型、大小與屬性", true);
	fontsize.setValue(new Integer(18));

	ok = new JButton("确定");
	cancel = new JButton("取消");
	JPanel temp_0 = new JPanel(new GridLayout(3, 1));
	
	JPanel temp_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel temp_4 = new JPanel(new FlowLayout());
	JPanel temp_5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	Container jDialog_c = jDialog.getContentPane();

	jDialog_c.setLayout(new FlowLayout());
	jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	jDialog.setSize(250, 200);
	temp_5.add(new JLabel("文字:"));
	temp_5.add(textField_word);
	
	temp_2.add(new JLabel("大小:"));
	temp_2.add(fontsize);
	
	temp_4.add(ok);
	temp_4.add(cancel);
	temp_0.add(temp_5);
	//temp_0.add(temp_1);
	temp_0.add(temp_2);
	//temp_0.add(temp_3);
	temp_0.add(temp_4);
	jDialog_c.add(temp_0);

	fontsize.addChangeListener(this);
	ok.addActionListener(this);
	cancel.addActionListener(this);
	temp_0.setPreferredSize(new Dimension(180, 150));

	repaint();
	addMouseListener(this);
	addMouseMotionListener(this);
	
	Tongbu();
}

public void Tongbu(){
	/**
	 * 同步
	 * masking 2012-05-21
	 */
	ArrayList<ShapeModel> modelList;
	try {
		modelList = paintSender.getUpdateList(this.boardID);
		
		System.out.println("getUpdateList------>"+modelList.size());
		for(int i=0;i<modelList.size();i++){
			shapeModelList.add(modelList.get(i));
			this.receiveShapeModel(modelList.get(i));
		}
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	drawMethod=2;
}


public void stateChanged(ChangeEvent e) {
	size = Integer.parseInt(fontsize.getValue().toString());
	if (size <= 0) {
		fontsize.setValue(new Integer(1));
		size = 1;
	}
}

public void actionPerformed(ActionEvent e) {
	if(e.getSource()==ok){
		FontToPaint=true;
	}
	else if(e.getSource()==cancel){
		FontToPaint=false;
	}
	jDialog.dispose();
}



public Dimension getPreferredSize() {
	return new Dimension(draw_panel_width, draw_panel_height);
}

public void openfile(String filename) {// 开启文档
	if(filename.endsWith(".msk")){
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ArrayList<ShapeModel> temp = (ArrayList<ShapeModel>) ois.readObject(); 
			ois.close();
			for(int i=0;i<temp.size();i++){
				paintSender.sendPaint(temp.get(i),boardID,meetingID);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

//masking
	
}

public void undo() {// 撤销

	if(canDraw==true){
	
//此处进行远程调用//将g2d_bufImg作为参数传递给服务器,即白板每完成一次操作，即将最新的图像传输给服务器
	try {
		//paintSender.sendPaint((ImgData)bufImg);
		paintSender.sendUndo(this.boardID);
		System.out.println("向客户端发送undo请求 ");
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}

	}
}


public void undo2() {// 撤销
	if(canReceive==true){
		//////////////////count--;
		bufImg = new BufferedImage(draw_panel_width, draw_panel_height,
				BufferedImage.TYPE_3BYTE_BGR);//可以显示三原色的画布
    jlbImg = new JLabel(new ImageIcon(bufImg));// 在JLabel上放置bufImg，用来绘图
	this.removeAll();
	this.add(jlbImg);
	jlbImg.setBounds(new Rectangle(0, 0, draw_panel_width,
			draw_panel_height));
    Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	g2d_bufImg.setPaint(Color.white);
	g2d_bufImg.fill(new Rectangle2D.Double(0, 0, draw_panel_width,
			draw_panel_height));
	Graphics2D g2d_bufImgdata= (Graphics2D) bufImg
			.getGraphics();
	g2d_bufImgdata.drawImage(bufImg, 0, 0, this);
	
	System.out.println("receive undo!!!!!!");
	
	        ShapeModel temp;
	    	temp=shapeModelList.get(shapeModelList.size()-1);
	   
	          shapeModelList.remove(shapeModelList.size()-1);
	          modelStack.push(temp);
	          paintModelList();
     	         if (shapeModelList.isEmpty())
	                   	jreundo[0].setEnabled(false);
	             if(canDraw==true){
		                jreundo[1].setEnabled(true);
	             }
	     repaint();
	
	}
}

public void redo() {// 重做
	if(canDraw==true){
		
//此处进行远程调用//将g2d_bufImg作为参数传递给服务器,即白板每完成一次操作，即将最新的图像传输给服务器	
	try {
		ShapeModel redoModel =modelStack.peek(); 
		paintSender.sendRedoModel(redoModel, this.boardID,meetingID);
		paintSender.sendRedo(this.boardID);
		System.out.println("向客户端发送redo请求 ");
		System.out.println("redo----------------->getmodel");
		System.out.println("redo----------------->success");
	} catch (Exception e) {
		System.out.println("redo----------------->fail");
	}
	
	}
}


public void redo2() {// 重做
	
	if(canReceive==true){
		
		//////////////count++;
		bufImg = new BufferedImage(draw_panel_width, draw_panel_height,
				BufferedImage.TYPE_3BYTE_BGR);//可以显示三原色的画布
    jlbImg = new JLabel(new ImageIcon(bufImg));// 在JLabel上放置bufImg，用来绘图
	this.removeAll();
	this.add(jlbImg);
	jlbImg.setBounds(new Rectangle(0, 0, draw_panel_width,
			draw_panel_height));
    Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	g2d_bufImg.setPaint(Color.white);
	g2d_bufImg.fill(new Rectangle2D.Double(0, 0, draw_panel_width,
			draw_panel_height));
	Graphics2D g2d_bufImgdata= (Graphics2D) bufImg
			.getGraphics();
	g2d_bufImgdata.drawImage(bufImg, 0, 0, this);

	    	ShapeModel temp;
		    temp=modelStack.pop();
		    
		        shapeModelList.add(temp);
		          paintModelList();
	
	              if (modelStack.empty())
		                     jreundo[1].setEnabled(false);
	               if(canDraw==true){
	                        jreundo[0].setEnabled(true);
	                }
	        repaint();
	
	}
}


public void mousePressed(MouseEvent e) {
	if(canDraw==true){
	   x1 = e.getX();
	   y1 = e.getY();
	  // modelFactory();
	   press = 1;//已经被按下，不是新起点
	}
}


public void mouseReleased(MouseEvent e) {
	if(canDraw==true){
	  x2 = e.getX();
	  y2 = e.getY();
	    //modelFactory();
	    draw(x1, y1, x2, y2);
	    //shapeModelList.add(shapeModel);
		repaint();
		toDraw();
		if(drawMethod==1||drawMethod==6){//铅笔和橡皮擦
			sendPandE();
		}
	}
}

public void mouseDragged(MouseEvent e) {
	if(canDraw==true){
	x2 = e.getX();
	y2 = e.getY();//x2,y2记录第二个点的坐标
	//modelFactory();
	draw(x1, y1, x2, y2);//生成新对象

	if (drawMethod == 1 || drawMethod == 6) {//铅笔和橡皮擦
	
		  sendPandE();
		  x1 = e.getX();
		  y1 = e.getY();//此时将第二个点坐标变为第一个点坐标，方便下次连续作图	
	}
	
	if (drawMethod != 8)//文字
		repaint();
	
	}
}

public void sendPandE(){
	try {
			paintSender.sendPaint(shapeModel,boardID,meetingID);
			System.out.println("向服务器发送数据 ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
}

public void mouseMoved(MouseEvent e) {
	if(canDraw==true){
	 x2 = e.getX();
	 y2 = e.getY();

	click = 0;
	if (drawMethod == 1 || drawMethod == 6)
		repaint();
	}
}


public void clear() {
	x1 = x2 = y1 = y2 = -1;
}

public void toDraw() {//鼠标释放后真正画到bufImg上
	
//此处进行远程调用//将g2d_bufImg作为参数传递给服务器,即白板每完成一次操作，即将最新的图像传输给服务器
	if(drawMethod!=1&&drawMethod!=6&&drawMethod!=0&&drawMethod!=8){
		           try {
			             paintSender.sendPaint(shapeModel,boardID,meetingID);
			             System.out.println("向服务器发送数据 ");
		          } catch (Exception e) {
			               // TODO: handle exception
			               e.printStackTrace();
		          }
		}
	else if(drawMethod==8&&FontToPaint==true){
		  try {
	             paintSender.sendPaint(shapeModel,boardID,meetingID);
	             System.out.println("向服务器发送数据 ");
       } catch (Exception e) {
	               // TODO: handle exception
	               e.printStackTrace();
       }
	}

}

public void toDraw2() {//鼠标释放后真正画到bufImg上
	if (x1 < 0 || y1 < 0)
		return;// 防止误按
	
	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();

	
	    if(drawMethod==6){
	       g2d_bufImg.setPaint(Color.white);
		   g2d_bufImg.setStroke(stroke);//重新调整画笔粗细
		   shapeModel.show(g2d_bufImg);
	    }
		if (drawMethod != 6&&drawMethod!=8) {//不是橡皮擦、文字
			g2d_bufImg.setPaint(color_border);
			g2d_bufImg.setStroke(stroke);
			shapeModel.show(g2d_bufImg);
		}
		if(drawMethod != 6&&drawMethod==8){//文字的情况
			//if(FontToPaint==true){
			FontRenderContext frc = g2d_bufImg.getFontRenderContext();
			//	jDialog.show();
				size=((MyFont)shapeModel).fontSize;
				String fieldword=((MyFont)shapeModel).textWord;
				String fieldfont=((MyFont)shapeModel).textFont;
				double sw = ((MyFont)shapeModel).sw;
				double sh = ((MyFont)shapeModel).sh;
				Font f = new Font(fieldfont,
						Font.ITALIC, size);
				TextLayout tl = new TextLayout(fieldword, f, frc);
				AffineTransform Tx = AffineTransform.getScaleInstance(1, 1);
				Tx.translate(sw,  sh);
				shape = tl.getOutline(Tx);
			//	shapeModel=myFont;
				g2d_bufImg.setPaint(color_border);
				g2d_bufImg.setStroke(stroke);
				g2d_bufImg.draw(shape);
				//shapeModel.show(g2d_bufImg);
		//	}
		}
		
	repaint();
	clear();
	// 記錄可重做最大次數，並讓重做不可按//
	/////////////////redo_lim = count++;
	jreundo[1].setEnabled(false);


	// 判斷座標為新起點//
	press = 0;

	// 讓復原MenuItem可以點選//
	if ((!shapeModelList.isEmpty())&&canDraw==true)
		jreundo[0].setEnabled(true);
}
public void paintFont(){
	//if(FontToPaint==true){
	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	FontRenderContext frc = g2d_bufImg.getFontRenderContext();
	//	jDialog.show();
		size=((MyFont)shapeModel).fontSize;
		String fieldword=((MyFont)shapeModel).textWord;
		String fieldfont=((MyFont)shapeModel).textFont;
		double sw = ((MyFont)shapeModel).sw;
		double sh = ((MyFont)shapeModel).sh;
		Font f = new Font(fieldfont,
				Font.ITALIC, size);
		TextLayout tl = new TextLayout(fieldword, f, frc);
		AffineTransform Tx = AffineTransform.getScaleInstance(1, 1);
		Tx.translate(sw,  sh);
		shape = tl.getOutline(Tx);
	//	shapeModel=myFont;
		g2d_bufImg.setPaint(color_border);
		g2d_bufImg.setStroke(stroke);
		g2d_bufImg.draw(shape);
		repaint();
		clear();
	//}
}

public void mouseEntered(MouseEvent e) {
}

public void mouseExited(MouseEvent e) {
}

public void mouseClicked(MouseEvent e) {
	if (click == 1) {// 連點兩下時
		toDraw();
	}
	click = 1;
}


@SuppressWarnings({ "unused", "deprecation" })
public void draw(int input_x1, int input_y1, int input_x2, int input_y2) {
	if (drawMethod == 2) {// 直线時，让shapeModel为myLine
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myLine=new MyLine();
		myLine.setPoint(startP, endP);
		myLine.lineWidth=getLineWithSelect();
		shapeModel=myLine;
	} else if (drawMethod == 3) {// 矩形时，让shapeModel为myRectangle
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myRectangle=new MyRectangle();
		myRectangle.setPoint(startP, endP);
		myRectangle.lineWidth=getLineWithSelect();
		shapeModel=myRectangle;
	} else if (drawMethod == 4) {// 圆角矩型
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myRoundRectangle=new MyRoundRectangle();
		myRoundRectangle.setPoint(startP, endP);
		myRoundRectangle.lineWidth=getLineWithSelect();
		shapeModel=myRoundRectangle;
	} else if (drawMethod == 5) {// 橢圓時
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myOval=new MyOval();
		myOval.setPoint(startP, endP);
		myOval.lineWidth=getLineWithSelect();
		shapeModel=myOval;
	}
	else if (drawMethod == 7) {// 菱形
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myLin = new MyLin();
		myLin.setPoint(startP, endP);
		myLin.lineWidth=getLineWithSelect();
		shapeModel = myLin;
		
	} else if (drawMethod == 1 || drawMethod == 6) {// 铅笔＆橡皮擦
		Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
		Point startP=new Point(input_x1,input_y1);
		Point endP=new Point(input_x2,input_y2);
      //  myLine.setPoint(startP,endP);
	//	shapeModel= myLine;
		if (drawMethod == 1){//铅笔时将画笔设置为黑色，否则为白色
			myPensil=new MyPensil();
			myPensil.setPoint(startP, endP);
			myPensil.lineWidth=getLineWithSelect();
			shapeModel=myPensil;
			g2d_bufImg.setPaint(color_border);
		}
		else{
			myEraser=new MyEraser();
			myEraser.setPoint(startP, endP);
			myEraser.lineWidth=getLineWithSelect();
			shapeModel=myEraser;
			g2d_bufImg.setPaint(Color.white);
		}
		g2d_bufImg.setStroke(stroke);//重新调整画笔粗细
		shapeModel.show(g2d_bufImg);		
	}
	else if (drawMethod == 8) {// 文字
		Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
		FontRenderContext frc = g2d_bufImg.getFontRenderContext();
		jDialog.show();
		Font f = new Font(textField_font.getText(),
				Font.ITALIC, size);
		TextLayout tl = new TextLayout(textField_word.getText(), f, frc);
		
		 sw=input_x2;
		 sh=input_y2+tl.getBounds().getHeight();

myFont=new MyFont(size,sw,sh,textField_font.getText(),textField_word.getText());
		//myFont=new MyFont(tl.getOutline(Tx));
		shapeModel=myFont;
	//	shape = tl.getOutline(Tx);
	}
	else if(drawMethod==9){//联系箭头
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myAssoShape=new MyAssoShape();
		myAssoShape.setPoint(startP, endP);
		myAssoShape.lineWidth=getLineWithSelect();
		shapeModel=myAssoShape;
	}else if(drawMethod==10){//组合箭头
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myAggrShape=new MyAggrShape();
		myAggrShape.setPoint(startP, endP);
		myAggrShape.lineWidth=getLineWithSelect();
		shapeModel=myAggrShape;
	}else if(drawMethod==11){//依赖箭头
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myUsageShape.setPoint(startP, endP);
		myUsageShape.lineWidth=getLineWithSelect();
		shapeModel =myUsageShape; 
	}else if(drawMethod==12){//实现箭头
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myGeneShape.setPoint(startP, endP);
		myGeneShape.lineWidth=getLineWithSelect();
		shapeModel = myGeneShape;
	}else if(drawMethod==13){//类图
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myClassShape=new MyClassShape();
		myClassShape.setPoint(startP, endP);
		myClassShape.lineWidth=getLineWithSelect();
		shapeModel=myClassShape;
	}else if(drawMethod==14){//包图
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myPackageShape=new MyPackageShape();
		myPackageShape.setPoint(startP, endP);
		myPackageShape.lineWidth=getLineWithSelect();
		shapeModel=myPackageShape;
	}else if(drawMethod==15){//开始状态
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myStartState=new MyStartState();
		myStartState.setPoint(startP, endP);
		myStartState.lineWidth=getLineWithSelect();
		shapeModel=myStartState;
	}else if(drawMethod==16){//结束状态
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myEndState=new MyEndState();
		myEndState.setPoint(startP, endP);
		myEndState.lineWidth=getLineWithSelect();
		shapeModel=myEndState;
	}else if(drawMethod==17){//时间柱
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myTimeShape=new MyTimeShape();
		myTimeShape.setPoint(startP, endP);
		myTimeShape.lineWidth=getLineWithSelect();
		shapeModel=myTimeShape;
	}else if(drawMethod==18){//小人
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myActorShape=new MyActorShape();
		myActorShape.setPoint(startP, endP);
		myActorShape.lineWidth=getLineWithSelect();
		shapeModel=myActorShape;
	}else if(drawMethod==19){//节点
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myNodeShape=new MyNodeShape();
		myNodeShape.setPoint(startP, endP);
		myNodeShape.lineWidth=getLineWithSelect();
		shapeModel=myNodeShape;
	}else if(drawMethod==20){//组件
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myCompoShape=new MyCompoShape();
		myCompoShape.setPoint(startP, endP);
		myCompoShape.lineWidth=getLineWithSelect();
		shapeModel=myCompoShape;
	}
}

public void paint(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	super.paint(g2d);// 重繪底層JPanel以及上面所有元件
	
	if (press == 1 &&  !(x1 < 0 || y1 < 0)) {// 繪圖在最上面的JLabel上，並判斷是不是起點才畫
		//draw(x1, y1, x2, y2);
		if(drawMethod==0)//mousepointer时不重绘
			return;
		if (drawMethod == 6)//橡皮擦
			return;
		if(drawMethod!=8){ //不是文字
		g2d.setPaint(color_border);
		g2d.setStroke(stroke);
		shapeModel.show(g2d);
		}
        if(drawMethod==8){//是文字
        	return;

        }
	}
	
	// 跟踪游标的圆形//跟踪橡皮擦游标的圆形
	if (drawMethod == 1 || drawMethod == 6) {//铅笔和橡皮擦
	    g2d.setPaint(Color.black);
		g2d.setStroke(basicStroke_pen);
		
		g2d.setPaint(Color.black);
		g2d.setStroke(basicStroke_pen);
		ellipse2D_pan
				.setFrame(x2 - number / 2, y2
						- number / 2, number,
						number);
		g2d.draw(ellipse2D_pan);
	}
}
//undo、redo之后将之前的model重绘
public void paintModelList(){
	System.out.println("paintModelList!!!!!!");
	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	for(int i=0;i<shapeModelList.size();i++){
	//	ShapeModel model=shapeModelList.get(i);
		shapeModel=shapeModelList.get(i);
		setLineWidth(shapeModel.lineWidth);
		
		if(shapeModel.id==6){//铅笔
			g2d_bufImg.setPaint(Color.white);
			g2d_bufImg.setStroke(stroke);
			System.out.println("paint old model"+i+"..");
			System.out.print(shapeModel.id);
			System.out.println(shapeModel.startP.x+","+shapeModel.startP.y);
			shapeModel.show(g2d_bufImg);
		}
		if(shapeModel.id!=6&&shapeModel.id!=8){//不是铅笔也不是文字
			g2d_bufImg.setPaint(color_border);
			g2d_bufImg.setStroke(stroke);
			System.out.println("paint old model"+i+"..");
			System.out.print(shapeModel.id);
			System.out.println(shapeModel.startP.x+","+shapeModel.startP.y);
			shapeModel.show(g2d_bufImg);
		}
		if(shapeModel.id!=6&&shapeModel.id==8){//文字
			paintFont();
		}
		//	repaint()
	}
}

public void receiveShapeModel(ShapeModel model){

       shapeModel=model;
       setLineWidth(shapeModel.lineWidth);
       System.out.println("客户端接受数据");
       x1=model.startP.x;
       y1=model.startP.y;
       x2=model.endP.x;
       y2=model.endP.y;
       System.out.println("x1:"+x1+"y1:"+y1+"x2:"+x2+"y2:"+y2);
       drawMethod=model.id;
       if(drawMethod<9){
    	   jToggleButtonBasic[drawMethod].setSelected(true);
       }
       else {
		   jToggleButtonUML[drawMethod-9].setSelected(true);
	   }
       toDraw2();
       repaint();
  }
}

public class PaintReceiverImp extends UnicastRemoteObject 
implements PaintReceiver{//服务器可调用的客户端远程对象

protected PaintReceiverImp() throws RemoteException {
	super();
	// TODO Auto-generated constructor stub
}
@Override
public void receiveShapeModel(int boardID,ShapeModel model) throws RemoteException {
	// TODO Auto-generated method stub
	drawPanelList.get(boardID).shapeModelList.add(model);
	drawPanelList.get(boardID).receiveShapeModel(model);
}
@Override
public void receiveUndo(int boardID) throws RemoteException {
	// TODO Auto-generated method stub
	drawPanelList.get(boardID).undo2();
}
@Override
public void receiveRedo(int boardID) throws RemoteException {
	// TODO Auto-generated method stub
	drawPanelList.get(boardID).redo2();
}
@Override
	public void receiveNewBoard(int boardID) throws RemoteException {
	// masking tongbu new board
		newBoardFromServer(boardID);

	}
@Override
public void receiveDeleteBoard(int boardID) throws RemoteException {
	// TODO Auto-generated method stub
		deleteBoardFromServer(boardID);
}
}
}

class MyJScrollPane extends JScrollPane{
	int id =0;
	MyJScrollPane(int _id,Component view){
		super(view) ;
		id = _id ;
	}
}

