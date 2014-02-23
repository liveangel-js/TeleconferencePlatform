package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.TransferHandler;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import logic.implement.FileShareLogicImp;
import logic.interfaces.FileShareLogic;
import manager.ClientManager;

public class FileSharePanel extends JPanel {

	
	
	public FileSharePanel() {
		this.initUI();
		this.initLogic();
		this.updateFileList();
	
	}

	public void initUI() {

		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "FileShare"));

		//初始化文件下载上传进度面板
		procedureFrame = new ProcedureFrame();
		
		
        //初始化North部分,包括上传按钮,更新按钮,下载进度按钮,以及过滤文本框
		JPanel north_panel = new JPanel();
		button_upload = new JButton("upload");
		button_upload.addActionListener(new ButtonListener());
		north_panel.add(button_upload);
		

		button_update = new JButton("update");
		button_update.addActionListener(new ButtonListener());
		north_panel.add(button_update);
		
		
		button_progress=new JButton("progress");
		button_progress.addActionListener(new ButtonListener());
		north_panel.add(button_progress);
		

		JLabel label_filter = new JLabel("filter");
		north_panel.add(label_filter);
		text_filter = new JTextField(25);
		north_panel.add(text_filter);
		text_filter.getDocument().addDocumentListener(new MyDocumentListener());
		this.add(north_panel, BorderLayout.NORTH);
		
		
		
		//初始化化文件列表面板
		String[] columnNames={"FID","MID","Name","Size","Uploader","Time"};	
		//String[] columnNames = { "ID", "MID","文件名", "文件大小", "上传者", "上传时间" };
		tableModel = new DefaultTableModel(columnNames, 10) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		fileListTable = new JTable(tableModel);
		fileListTable.setTransferHandler(new MyTransferHandler());
		sorter = new TableRowSorter<TableModel>(tableModel);
		fileListTable.setRowSorter(sorter);
		int vsbPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS;
		int hsbPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		this.add(new JScrollPane(fileListTable, vsbPolicy, hsbPolicy));
		// fileListTable.setShowGrid(false);
		// fileListTable.setShowHorizontalLines(false);
		fileListTable.setShowVerticalLines(false);
		fileListTable.addMouseListener(new MyMouseListener());
		// fileListTable.setRowSelectionAllowed(false);
		// fileListTable.setEnabled(false);
		//fileListTable.setRowMargin(5);
		//fileListTable.setRowHeight(20);
		popupMenu = new JPopupMenu();
		downloadMenuItem = new JMenuItem();
		popupMenu.add(downloadMenuItem);
		downloadMenuItem.setText("download");
		downloadMenuItem.addActionListener(new MenuItemActionListener());
		
		
		downloadMenuItem.setIcon(new ImageIcon("image/fileshare/download.png"));
		button_update.setIcon(new ImageIcon("image/fileshare/update.png"));
		button_upload.setIcon(new ImageIcon("image/fileshare/upload.png"));
		button_progress.setIcon(new ImageIcon("image/fileshare/progress.png"));
	}

	
	
	
	
	public void initLogic() {
		logic = new FileShareLogicImp();
	}

	
	
	public void updateFileList() {
		
			while (tableModel.getRowCount() != 0) {
				tableModel.removeRow(0);
			}
			ArrayList<String> fileList = logic.getFileList();
			for (int i = 0; i < fileList.size(); i++) {
				String file = fileList.get(i);
				Object infos[] = file.split(",");
				tableModel.addRow(infos);
			}

	}

	
	public void downloadFile(String fileName) {
		JFileChooser file_chooser = new JFileChooser("download");
		file_chooser.setSelectedFile(new File("download/" + fileName));
		if (file_chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = file_chooser.getSelectedFile();
			String des = file.getAbsolutePath();
			System.out.println(des);
			try {
				logic.downloadFile(fileName, des, new ProcedureController());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



	public void uploadFile(String fileName) {
		try {
			logic.uploadloadFile(fileName, new ProcedureController());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> getUploadFile() {
		JFileChooser file_chooser = new JFileChooser();
		file_chooser.setMultiSelectionEnabled(true);
		ArrayList<String> files = new ArrayList<String>();
		if (file_chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File[] selectedFiles = file_chooser.getSelectedFiles();
			for (int i = 0; i < selectedFiles.length; i++) {
				files.add(selectedFiles[i].getAbsolutePath());
			}
		}
		return files;
	}

	class MyMouseListener extends MouseAdapter {



		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger()) {
				System.out.println("mousePressed");
				int row = fileListTable.rowAtPoint(e.getPoint());
				int column = fileListTable.columnAtPoint(e.getPoint());
				fileListTable.setRowSelectionInterval(row, row);
				fileListTable.setColumnSelectionInterval(column, column);
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
				
			}

		}

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				int row = fileListTable.convertRowIndexToModel(fileListTable
						.getSelectedRow());
				// int row = fileListTable.getSelectedRow();

				int column = fileListTable.getSelectedColumn();
				String file = (String) tableModel.getValueAt(row, 2);
				downloadFile(file);
				System.out.println("mouseClick2");
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {
				int row = fileListTable.rowAtPoint(e.getPoint());
				int column = fileListTable.columnAtPoint(e.getPoint());
				fileListTable.setRowSelectionInterval(row, row);
				fileListTable.setColumnSelectionInterval(column, column);
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
				System.out.println("mouseReleased");
			}
		}
	}

	class MenuItemActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int view_row=fileListTable.getSelectedRow();
			int model_row = fileListTable.convertRowIndexToModel(view_row);
			String file = (String) tableModel.getValueAt(model_row, 2);
			downloadFile(file);
		}

	}

	
	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == button_update) {
				updateFileList();
				return;
			}
			if (e.getSource() == button_upload) {
				ArrayList<String> files = getUploadFile();
				for (int i = 0; i < files.size(); i++) {
					uploadFile(files.get(i));
				}
				return;
			}
			if(e.getSource() == button_progress) {
				procedureFrame.setVisible(true);
			}
		}
	}


	
	public class ProcedureFrame extends JFrame {
		JPanel panel = new JPanel();

		private ProcedureFrame() {
			super("FileShare Procedure Info");
			this.setSize(250, 300);
			this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			Container container = this.getContentPane();
			int vsbPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS;
			int hsbPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
			container.add(new JScrollPane(panel, vsbPolicy, hsbPolicy));
		}
	}

	
	
	
	
	public class ProcedureController {
		private JProgressBar bar;
		private JLabel label_file;
		private JLabel label_state;
		private JLabel label_percent;

		

		private ProcedureController() {

		}

		public void init(String file, int max) {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "task"));
			label_file = new JLabel();
			panel.add(label_file);
			label_state = new JLabel();
			panel.add(label_state);
			label_percent = new JLabel();
			panel.add(label_percent);
			bar = new JProgressBar();
			JPanel bar_panel=new JPanel();
			bar_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
			bar_panel.add(bar);
			panel.add(bar_panel);
			procedureFrame.panel.add(panel);
			label_file.setText("fileName:" + file);
			label_state.setText("state:preparing");
			label_percent.setText("percent:0%");
			bar.setMaximum(max);
			procedureFrame.setVisible(true);
			procedureFrame.repaint();
		}

		public void set(String state, int percent, int value) {
			label_state.setText("state:" + state);
		    label_percent.setText("percent:" + percent + "%");
			bar.setValue(value);
			procedureFrame.repaint();
		}
	}

	class MyDocumentListener implements DocumentListener {		

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			System.out.println("insertUpdate");
			setFilter();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			System.out.println("removeUpdate");
			setFilter();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			System.out.println("changedUpdate");
			setFilter();
		}
		
		
		public void setFilter(){
			String text = text_filter.getText();
			if (text.length() == 0) {
				sorter.setRowFilter(null);
			} else {
				sorter.setRowFilter(RowFilter.regexFilter(text));
			}
		}
	}

	class MyTransferHandler extends TransferHandler {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7435768449178058616L;

		public boolean importData(JComponent comp, Transferable t) {
			int sure=JOptionPane.showConfirmDialog(null, "be sure to upload all the files?");
			if(sure!=JOptionPane.YES_OPTION){
				return false;
			}
			if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				try {
					List files = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
					for(int i=0;i<files.size();i++){
						File file = (File) files.get(i);
						upload(file);
					}
				} catch (UnsupportedFlavorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		}
		
		private void upload(File file){
			if(!file.isDirectory()){
				uploadFile(file.getAbsolutePath());
			}
			else{
				File []files=file.listFiles();
				for(int i=0;i<files.length;i++){
					upload(files[i]);
				}
			}
		}
		

		public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
			return true;
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8436118290977881706L;
	private JPopupMenu popupMenu;
	private JMenuItem downloadMenuItem;
	private JTable fileListTable;
	private DefaultTableModel tableModel;
	private FileShareLogic logic;
	private JButton button_upload;
	private JButton button_update;
	private JButton button_progress;
	private JTextField text_filter;
	private TableRowSorter<TableModel> sorter;
	private ProcedureFrame procedureFrame;

	public static void main(String args[]) {
		ClientManager.setSystemLookAndFeel();
		JFrame frame = new JFrame("文件共享");
		frame.getContentPane().add(BorderLayout.CENTER, new FileSharePanel());
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
