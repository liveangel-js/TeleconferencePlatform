/* 文件名：FileTransportClient.java
 * 日期：2006-11-7
 * 作者：谭奇宇
 */

package fileShare;

import java.rmi.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;

//文件传输客户端
public class FileTransportClient extends JFrame
{	
	//成员变量
	private final int BUFFER_SIZE = 1024 * 1024;	//默认一次传输的字节数
	private String hostIP;	//服务器IP地址或名称
	private int hostPort;	//服务器端口
	private IFileTransport ft; //文件传输接口
	private String downloadPath;	//默认下载路径
	private String saveFilePath;	//保存到本地的文件全路径
	private Vector<String> fileNameList; //文件名称列表
	
	
	//窗体控件
	private DefaultListModel listModel;
	private JPopupMenu popupMenu;
	private JLabel serverLabel;
	private JLabel portLabel;	
	private JTextField serverTextField;
	private JTextField portTextField;	
	private JButton connectButton;
	private JButton refreshButton;
	private JButton setupButton;
	private JLabel pathLabel;
	private JLabel fileLabel;
	private JList fileList;
	private JProgressBar progressBar;
	private JLabel progressLabel;
	
	//文件传输线称
	class TransportThread extends Thread
	{
		private String fileName;
		
		public TransportThread(String filePath)
		{
			this.fileName = filePath;
		}
		
		public void run()
		{
			transport(this.fileName);
		}
	}
	
	//按钮事件处理
	class ButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton b = (JButton) e.getSource();
			if(b == connectButton || b == refreshButton)
			{
				connect();
			}
			else if(b == setupButton)
			{
				setup();
			}
		}
	}
	
	//弹出菜单事件处理
	class MenuItemActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(fileList.getSelectedIndex() < 0)
			{
				return;
			}
			
			JMenuItem item = (JMenuItem) e.getSource();
			if(item.getText().equals("下载"))
			{
				save();	
			}
			else if(item.getText().equals("另存为"))
			{
				saveAs();
			}
		}
	}
	
	//文件列表事件处理
	class FileListListener extends MouseAdapter implements ListSelectionListener
	{		
		public void valueChanged(ListSelectionEvent e)
		{
			progressBar.setValue(0);
			progressLabel.setText("");
		}
		
		public void mouseClicked(MouseEvent e)
		{
			if(e.getClickCount() == 2)
			{
				int i = fileList.getSelectedIndex();
				if(i >= 0)
				{
					Rectangle r = fileList.getCellBounds(i, i);
					if(r.contains(e.getPoint()))
					{
						save();
					}
				}
			}
		}
		
		public void mousePressed(MouseEvent e) 
		{
	        maybeShowPopup(e);
	    }

	    public void mouseReleased(MouseEvent e) 
	    {
	        maybeShowPopup(e);
	    }

	    private void maybeShowPopup(MouseEvent e) 
	    {
	        if (e.isPopupTrigger())
	        {
	        	int i = fileList.getSelectedIndex();
				if(i >= 0)
				{
					Rectangle r = fileList.getCellBounds(i, i);
					if(r.contains(e.getPoint()))
					{
						popupMenu.show(e.getComponent(), e.getX(), e.getY());
					}
				}	            
	        }
	    }
	}

	class TextFieldListener
	{
		
	}
	//构造函数，对象初始化
	public FileTransportClient()
	{	
		this.listModel  = new DefaultListModel();
		this.downloadPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "download";
		File f = new File(this.downloadPath);
		if(f.exists() == false)
		{
			f.mkdir();
		}
		
		this.setLayout(null);
		this.setSize(640, 480);
		this.setTitle("利用RMI实现文件传输的客户端程序");
		this.setResizable(false);
		Container cp = this.getContentPane();
		
		this.serverLabel = new JLabel("服务器IP地址或名称 : ");
		this.serverLabel.setBounds(20, 20, 140, 25);
		cp.add(this.serverLabel);
		
		this.serverTextField  = new JTextField(8);
		this.serverTextField.setBounds(170, 20, 150, 25);
		cp.add(this.serverTextField);
		
		this.portLabel = new JLabel("端口号 : ");
		this.portLabel.setBounds(340, 20, 50, 25);
		cp.add(this.portLabel);
		
		this.portTextField = new JTextField(4);
		this.portTextField.setBounds(400, 20, 80, 25);	
		cp.add(this.portTextField);
		

		
		this.pathLabel = new JLabel("默认路径：" + this.downloadPath);
		this.pathLabel.setBounds(20, 55, 450, 25);
		cp.add(this.pathLabel);
		
		this.setupButton = new JButton("修改下载路径");	
		this.setupButton.setBounds(460, 55, 140, 25);
		cp.add(this.setupButton);
		
		this.connectButton = new JButton("连接服务器");
		this.connectButton.setBounds(20, 90, 140, 25);
		cp.add(this.connectButton);
		
		this.refreshButton = new JButton("刷新共享文件列表");
		this.refreshButton.setBounds(170, 90, 140, 25);
		cp.add(this.refreshButton);
		
		ButtonActionListener bal = new ButtonActionListener();
		this.connectButton.addActionListener(bal);
		this.refreshButton.addActionListener(bal);
		this.setupButton.addActionListener(bal);
		
		this.fileLabel = new JLabel("共享文件列表：");
		this.fileLabel.setBounds(20, 125, 100, 25);
		cp.add(this.fileLabel);
		
		this.fileList = new JList(listModel);
		this.fileList.setBounds(20, 155, 600, 260);
		this.fileList.setBorder(BorderFactory.createLoweredBevelBorder());
		this.fileList.addMouseListener(new FileListListener());
		this.fileList.addListSelectionListener(new FileListListener());
		cp.add(this.fileList);
		
		MenuItemActionListener mial = new MenuItemActionListener();
		JMenuItem downloadItem = new JMenuItem("下载");
		downloadItem.addActionListener(mial);
		JMenuItem saveAsItem = new JMenuItem("另存为");
		saveAsItem.addActionListener(mial);
		this.popupMenu = new JPopupMenu();
		this.popupMenu.add(downloadItem);
		this.popupMenu.add(saveAsItem);
		
		this.progressBar = new JProgressBar();	
		this.progressBar.setBounds(20, 420, 480, 25);
		this.progressBar.setVisible(false);
		cp.add(this.progressBar);	
		
		this.progressLabel = new JLabel();
		this.progressLabel.setBounds(510, 420, 110, 25);
		cp.add(this.progressLabel);
		
		try
		{
			//默认获取当前主机名和默认端口号
			this.serverTextField.setText(InetAddress.getLocalHost().getHostAddress());
			this.portTextField.setText( ((Integer)IFileTransport.defaultPort).toString() );
		}
		catch(java.net.UnknownHostException uhe)
		{
		}
	}
	
	//连接服务器并显示共享文件列表
	private void connect()
	{
		try
		{
			this.hostIP = this.serverTextField.getText().trim();
			this.hostPort = Integer.parseInt(this.portTextField.getText());
			this.ft = (IFileTransport) Naming.lookup("//" + this.hostIP + ":" + this.hostPort + "/FileTransport");
			this.fillFileList();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "服务器连接错误，请检查服务器名称和端口号是否正确", "错误", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//设置默认下载路径
	private void setup()
	{
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			this.downloadPath = fc.getSelectedFile().getAbsolutePath();
			this.pathLabel.setText("默认路径：" + this.downloadPath);
		}
	}
	
	//传输文件
	private void transport(String fileName)
	{
		try
		{
			int length = this.ft.getFileLength(fileName);
			int start = 0;
			int left = length;
			
			File f = new File(this.saveFilePath);
			if(f.exists())
			{
				int ret = JOptionPane.showConfirmDialog(this, "文件已存在，是否覆盖？", "消息", JOptionPane.YES_NO_OPTION);
				if(ret == JOptionPane.NO_OPTION)
				{
					return;
				}
			}
			
			OutputStream out = new FileOutputStream(this.saveFilePath);
			byte[] buffer;
			
			this.progressBar.setMaximum(length);
			this.progressBar.setVisible(true);
			this.progressLabel.setText("已下载 0%");
			while(left > 0)
			{
				buffer = this.ft.getFile(fileName, start, this.BUFFER_SIZE);				
				out.write(buffer);
				left -= buffer.length;
				start += buffer.length;
				this.progressBar.setValue(start);
				this.progressBar.setIndeterminate(false);
				int percent = (int)(100 * ((double) start / (double)length));
				this.progressLabel.setText("已下载 " + percent + "%");
			}
			out.close();
			this.progressLabel.setText("完成");
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//填充文件列表
	private void fillFileList() throws Exception
	{
		this.fileNameList = ft.getFileList();
		this.listModel.clear();
		int i;
		for(i = 0; i < this.fileNameList.size(); i++)
		{
			File f = new File(this.fileNameList.elementAt(i));
			this.listModel.addElement(f.getName());
		}
		if(i > 0)
		{
			this.fileList.setSelectedIndex(0);
		}
	}
	
	//保存文件到默认路径
	private void save()
	{
		String fileName = fileNameList.elementAt(fileList.getSelectedIndex());
		File f = new File(fileName);
		saveFilePath = downloadPath + System.getProperty("file.separator")+ f.getName();
		TransportThread tt = new TransportThread(fileName);
		tt.start();
	}
	
	//文件另存为 
	private void saveAs()
	{
		JFileChooser fc = new JFileChooser(downloadPath);
		String fileName = fileNameList.elementAt(fileList.getSelectedIndex());
		File tf = new File(fileName);
		fc.setSelectedFile(new File(downloadPath + System.getProperty("file.separator")+ tf.getName()));
		if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			saveFilePath = f.getAbsolutePath();
			TransportThread tt = new TransportThread( fileNameList.elementAt(fileList.getSelectedIndex()));
			tt.start();	
		}
	}
	
	public static void main(String[] args)
	{
		try {
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
		FileTransportClient ftc = new FileTransportClient();
		ftc.setVisible(true);
	}
}

