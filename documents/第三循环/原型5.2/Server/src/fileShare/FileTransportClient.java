/* �ļ�����FileTransportClient.java
 * ���ڣ�2006-11-7
 * ���ߣ�̷����
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

//�ļ�����ͻ���
public class FileTransportClient extends JFrame
{	
	//��Ա����
	private final int BUFFER_SIZE = 1024 * 1024;	//Ĭ��һ�δ�����ֽ���
	private String hostIP;	//������IP��ַ������
	private int hostPort;	//�������˿�
	private IFileTransport ft; //�ļ�����ӿ�
	private String downloadPath;	//Ĭ������·��
	private String saveFilePath;	//���浽���ص��ļ�ȫ·��
	private Vector<String> fileNameList; //�ļ������б�
	
	
	//����ؼ�
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
	
	//�ļ������߳�
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
	
	//��ť�¼�����
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
	
	//�����˵��¼�����
	class MenuItemActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(fileList.getSelectedIndex() < 0)
			{
				return;
			}
			
			JMenuItem item = (JMenuItem) e.getSource();
			if(item.getText().equals("����"))
			{
				save();	
			}
			else if(item.getText().equals("���Ϊ"))
			{
				saveAs();
			}
		}
	}
	
	//�ļ��б��¼�����
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
	//���캯���������ʼ��
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
		this.setTitle("����RMIʵ���ļ�����Ŀͻ��˳���");
		this.setResizable(false);
		Container cp = this.getContentPane();
		
		this.serverLabel = new JLabel("������IP��ַ������ : ");
		this.serverLabel.setBounds(20, 20, 140, 25);
		cp.add(this.serverLabel);
		
		this.serverTextField  = new JTextField(8);
		this.serverTextField.setBounds(170, 20, 150, 25);
		cp.add(this.serverTextField);
		
		this.portLabel = new JLabel("�˿ں� : ");
		this.portLabel.setBounds(340, 20, 50, 25);
		cp.add(this.portLabel);
		
		this.portTextField = new JTextField(4);
		this.portTextField.setBounds(400, 20, 80, 25);	
		cp.add(this.portTextField);
		

		
		this.pathLabel = new JLabel("Ĭ��·����" + this.downloadPath);
		this.pathLabel.setBounds(20, 55, 450, 25);
		cp.add(this.pathLabel);
		
		this.setupButton = new JButton("�޸�����·��");	
		this.setupButton.setBounds(460, 55, 140, 25);
		cp.add(this.setupButton);
		
		this.connectButton = new JButton("���ӷ�����");
		this.connectButton.setBounds(20, 90, 140, 25);
		cp.add(this.connectButton);
		
		this.refreshButton = new JButton("ˢ�¹����ļ��б�");
		this.refreshButton.setBounds(170, 90, 140, 25);
		cp.add(this.refreshButton);
		
		ButtonActionListener bal = new ButtonActionListener();
		this.connectButton.addActionListener(bal);
		this.refreshButton.addActionListener(bal);
		this.setupButton.addActionListener(bal);
		
		this.fileLabel = new JLabel("�����ļ��б�");
		this.fileLabel.setBounds(20, 125, 100, 25);
		cp.add(this.fileLabel);
		
		this.fileList = new JList(listModel);
		this.fileList.setBounds(20, 155, 600, 260);
		this.fileList.setBorder(BorderFactory.createLoweredBevelBorder());
		this.fileList.addMouseListener(new FileListListener());
		this.fileList.addListSelectionListener(new FileListListener());
		cp.add(this.fileList);
		
		MenuItemActionListener mial = new MenuItemActionListener();
		JMenuItem downloadItem = new JMenuItem("����");
		downloadItem.addActionListener(mial);
		JMenuItem saveAsItem = new JMenuItem("���Ϊ");
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
			//Ĭ�ϻ�ȡ��ǰ��������Ĭ�϶˿ں�
			this.serverTextField.setText(InetAddress.getLocalHost().getHostAddress());
			this.portTextField.setText( ((Integer)IFileTransport.defaultPort).toString() );
		}
		catch(java.net.UnknownHostException uhe)
		{
		}
	}
	
	//���ӷ���������ʾ�����ļ��б�
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
			JOptionPane.showMessageDialog(this, "���������Ӵ���������������ƺͶ˿ں��Ƿ���ȷ", "����", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//����Ĭ������·��
	private void setup()
	{
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			this.downloadPath = fc.getSelectedFile().getAbsolutePath();
			this.pathLabel.setText("Ĭ��·����" + this.downloadPath);
		}
	}
	
	//�����ļ�
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
				int ret = JOptionPane.showConfirmDialog(this, "�ļ��Ѵ��ڣ��Ƿ񸲸ǣ�", "��Ϣ", JOptionPane.YES_NO_OPTION);
				if(ret == JOptionPane.NO_OPTION)
				{
					return;
				}
			}
			
			OutputStream out = new FileOutputStream(this.saveFilePath);
			byte[] buffer;
			
			this.progressBar.setMaximum(length);
			this.progressBar.setVisible(true);
			this.progressLabel.setText("������ 0%");
			while(left > 0)
			{
				buffer = this.ft.getFile(fileName, start, this.BUFFER_SIZE);				
				out.write(buffer);
				left -= buffer.length;
				start += buffer.length;
				this.progressBar.setValue(start);
				this.progressBar.setIndeterminate(false);
				int percent = (int)(100 * ((double) start / (double)length));
				this.progressLabel.setText("������ " + percent + "%");
			}
			out.close();
			this.progressLabel.setText("���");
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//����ļ��б�
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
	
	//�����ļ���Ĭ��·��
	private void save()
	{
		String fileName = fileNameList.elementAt(fileList.getSelectedIndex());
		File f = new File(fileName);
		saveFilePath = downloadPath + System.getProperty("file.separator")+ f.getName();
		TransportThread tt = new TransportThread(fileName);
		tt.start();
	}
	
	//�ļ����Ϊ 
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

