/* 文件名：FileTransportServer.java
 * 日期：2006-11-7
 * 作者：谭奇宇
 */

package fileShare;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

import sql.FileAccessSQL;
import sql.Interface.FileNetInterface;

import java.awt.*;
import java.awt.event.*;

//文件传输服务器端
public class FileTransportServer extends JFrame
{
	//文件传输实现类
	class FileTransportImpl extends UnicastRemoteObject implements IFileTransport
	
	{
		FileNetInterface sql=new FileAccessSQL();
		public FileTransportImpl() throws Exception
		{
			
		}
		
		public Vector<String> getFileList() throws Exception
		{
			return fileNameList;
		}
		
		public int getFileLength(String fileName) throws Exception
		{
				//File f = new File(fileName);
				//return (int) f.length();
			return (int) sql.getFileLength(4);
		}
		
		public byte[] getFile(String fileName, int start, int length) throws Exception
		{
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			//InputStream is = new FileInputStream(fileName);
			InputStream is =sql.downloadFile(4);
			byte[] buffer = new byte[length];
			int readSize;
			is.skip(start);
			if( (readSize = is.read(buffer, 0, length)) != -1 ) 
			{	
				byteArray.write(buffer, 0, readSize);
			}		
			//is.close();
			sql.closeStream();
			return byteArray.toByteArray();
		}
	}
	
	//按钮事件处理
	class ButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				JButton b = (JButton) e.getSource();
				if(b == addButton)
				{
					addFile();
				}
				else if(b == removeButton)
				{
					removeFile();
				}
				else if(b == portChangeButton)
				{
					changePort();
				}
			}
			catch(Exception ee)
			{
				JOptionPane.showMessageDialog(null, ee.getMessage());
			}
		}
	}
	
	//服务器窗体事件处理
	class ServerWindowListener extends WindowAdapter
	{
		//当关闭窗体时，撤销FileTransportImpl对象和端口之间的绑定
		public void windowClosing(WindowEvent e)
		{
			unbind();
		}
	}
	
	//窗体控件及其他成员变量
	private JLabel serverInfoLabel;
	private JLabel portLabel;
	private JButton portChangeButton;
	private JList fileList;
	private JButton addButton;
	private JButton removeButton;
	private Vector<String> fileNameList; //文件名称列表
	private DefaultListModel listModel;	
	private int port;
	private MyLog logger;
	
	//构造函数，初始化对象
	public FileTransportServer() throws Exception
	{
		this.port = IFileTransport.defaultPort;	//赋初值
		this.fileNameList = new Vector<String>();
		this.listModel = new DefaultListModel();
		this.logger = new MyLog("FileTransportServerLog.txt");
		
		ButtonActionListener bal = new ButtonActionListener();
		
		//初始化控件
		this.setSize(640, 480);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("利用RMI实现文件传输的服务器端程序（作者：谭奇宇）");
		
		Container cp = this.getContentPane();
		
		this.serverInfoLabel = new JLabel("服务器IP：" + InetAddress.getLocalHost().getHostAddress());
		this.serverInfoLabel.setBounds(20, 20, 200, 25);
		cp.add(this.serverInfoLabel);
		
		
		this.portLabel = new JLabel("端口号：" + this.port);
		this.portLabel.setBounds(230, 20, 100, 25);
		cp.add(this.portLabel);
		
		this.portChangeButton = new JButton("修改端口号");
		this.portChangeButton.setBounds(340, 20, 100, 25);
		this.portChangeButton.addActionListener(bal);
		cp.add(this.portChangeButton);		
		
		this.listModel = new DefaultListModel();
		this.fileList = new JList(this.listModel);
		this.fileList.setBorder(BorderFactory.createLoweredBevelBorder());
		this.fileList.setBounds(20, 50, 600, 350);
		cp.add(this.fileList);
		
		this.addButton = new JButton("添加共享文件");
		this.addButton.setBounds(240, 410, 150, 25);
		this.addButton.addActionListener(bal);
		cp.add(this.addButton);
		
		this.removeButton = new JButton("移除共享文件");
		this.removeButton.setBounds(400, 410, 150, 25);
		this.removeButton.setEnabled(false);
		this.removeButton.addActionListener(bal);
		cp.add(this.removeButton);
		
		this.addWindowListener(new ServerWindowListener());
	}
	
	//注册，生成FileTransportImpl并将注册到port端口
	public void register() throws Exception
	{
		FileTransportImpl ft = new FileTransportImpl();
		Registry reg;
		try
		{
			reg = LocateRegistry.getRegistry(this.port);
			reg.rebind("FileTransport", ft);
		}
		catch(RemoteException re)
		{
			reg = LocateRegistry.createRegistry(this.port);
			reg.bind("FileTransport", ft);
		}
		this.portLabel.setText("端口号：" + this.port);
	}
	
	//添加共享文件
	private void addFile() throws Exception
	{
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);
		if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			File[] selectedFiles = fc.getSelectedFiles();
			for(int i = 0; i < selectedFiles.length; i++)
			{
				this.fileNameList.add(selectedFiles[i].getAbsolutePath());
				this.listModel.addElement(selectedFiles[i].getName());
			}
			if(this.fileList.getSelectedIndex() < 0)
			{
				this.fileList.setSelectedIndex(0);
				this.removeButton.setEnabled(true);
			}
		}
	}
	
	//删除共享文件
	private void removeFile() throws Exception
	{
		int index = this.fileList.getSelectedIndex();
		if(index >= 0)
		{
			this.listModel.removeElementAt(index);
			this.fileNameList.removeElementAt(index);
		}
		
		if(this.listModel.size() > 0)
		{
			this.fileList.setSelectedIndex(0);
		}
		else
		{
			this.removeButton.setEnabled(false);
		}
	}
	
	//修改端口
	private void changePort() throws Exception
	{
		Object o = JOptionPane.showInputDialog(this, "输入端口号", "修改端口号", JOptionPane.YES_NO_OPTION, null, null, this.port);
		if(o == null)
		{
			return;
		}
		
		try
		{
			int v = Integer.parseInt(o.toString());
			if(v >= 1024 && v <= 65535)
			{
				this.unbind();
				this.port = v;				
				this.register();
			}
			else
			{
				JOptionPane.showMessageDialog(this, "此端口号不合适");
			}
		}
		catch(ClassCastException cce)
		{
			JOptionPane.showMessageDialog(this, "非法输入");
		}
	}
	
	//撤销绑定
	private void unbind()
	{
		try
		{
			Registry reg = LocateRegistry.getRegistry(this.port);
			reg.unbind("FileTransport");
		}
		catch(Exception ee)
		{
			logger.log("撤销绑定出错：" + ee.getMessage());
		}
	}
	
	
	public static void main(String[] args)
	{		try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedLookAndFeelException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		try
		{
			FileTransportServer ftServer = new FileTransportServer();
			ftServer.register();
			ftServer.setVisible(true);
		}
		catch(Exception ee)
		{
			JOptionPane.showMessageDialog(null, ee.getMessage());
		}
	}
}

