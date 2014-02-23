package board;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.font.*;


@SuppressWarnings("serial")
public class PaintBoard extends JPanel implements ActionListener,ChangeListener {
	//private Container c = getContentPane();
	
	
	////////////////////////////////////private JPanel c=new JPanel();//��panel
	private String menuBar[] = { "�ļ�(F)", "�༭(E)", "��ͼ(V)", "˵��(H)" };//�˵�������
	private String menuItem[][] = { //�˵�ѡ������
			{ "�½�(N)|78", "��(O)|79", "����(S)|83", "���Ϊ(A)", "�˳�(X)|88" },
			{ "����(U)|90", "�ظ�(R)|89",  },
			{ "������(T)|84" }, { "���ڰװ�(A)" } };
	
	//, "״̬��(S)", "������(M)", "ɫ��(C)|76""����(T)|87", "����(C)|68", "ճ��(P)|85"
	private JMenuItem jMenuItem[][] = new JMenuItem[4][5];//�˵�ѡ��
	private JMenu jMenu[];//�˵���
	//�������ɫ����ɵĸ�ѡ��ѡ��
	private JCheckBoxMenuItem jCheckBoxMenuItem[] = new JCheckBoxMenuItem[2];
	//private String ButtonName[] = { "ֱ��", "����", "��Բ", "Բ�Ǿ���", "��������", "����",
	//		"�����", "Ǧ��", "��Ƥ��", "����", "ѡȡ" };
	private String ButtonNameBasic[]={"Ǧ��","ֱ��","����","Բ�Ǿ���","��Բ","��Ƥ��","����Բ",
			 "����","����"};
	private String ButtonNameUML[]={"��ϵ��ͷ","��ϼ�ͷ","������ͷ","ʵ�ּ�ͷ","��ͼ","��ͼ",
			"��ʼ״̬","����״̬","ʱ����","С��","�ڵ�","���"};
	private JToggleButton jToggleButtonBasic[];
	private JSpinner lineWidthSelect;
	public int number = 5;
	private JToggleButton jToggleButtonUML[];
	private ButtonGroup buttonGroup;
	private JPanel jPanel[] = new JPanel[5];// 0�ڲ�,1��ͼ��,2������,3ɫ��,4������
	/*private String toolname[] = { "img/tool1.gif", "img/tool2.gif",
			"img/tool3.gif", "img/tool4.gif", "img/tool5.gif", "img/tool8.gif",
			"img/tool9.gif", "img/tool7.gif", "img/tool6.gif",
			"img/tool10.gif", "img/tool11.gif" };
			*/
	//private Icon tool[] = new ImageIcon[11];
	private int i, j;
	private int drawMethod = 0;//��¼Ҫ����������ͼ�� 
	private int draw_panel_width = 900;//����Ĭ�Ͽ��
	private int draw_panel_height = 550;//����Ĭ�ϸ߶�
	public  DrawPanel drawPanel;//�������
	public  UnderDrawPanel underDrawPanel;//���屳�����

	private Stroke stroke;
	@SuppressWarnings("unused")
	private String isFilled;
	private Paint color_border; 
	
	//�趨JMenuBar,������JMenuItem�����ÿ�ݼ�	
	public PaintBoard() {
	
		
		color_border=new Color(0,0,0);//����������Ϊ��ɫ
		
		JMenuBar bar = new JMenuBar();
		jMenu = new JMenu[menuBar.length];
		for (i = 0; i < menuBar.length; i++) {
			jMenu[i] = new JMenu(menuBar[i]);
			jMenu[i].setMnemonic(menuBar[i].split("\\(")[1].charAt(0));
			bar.add(jMenu[i]);
		}

		for (i = 0; i < menuItem.length; i++) {
			for (j = 0; j < menuItem[i].length; j++) {
				if (i == 0 && j == 4 || i == 1 && j == 2)
					jMenu[i].addSeparator();
				if (i != 2) {
					jMenuItem[i][j] = new JMenuItem(
							menuItem[i][j].split("\\|")[0]);
					if (menuItem[i][j].split("\\|").length != 1)
						jMenuItem[i][j]
								.setAccelerator(KeyStroke.getKeyStroke(
										Integer.parseInt(menuItem[i][j]
												.split("\\|")[1]),
										ActionEvent.CTRL_MASK));
					jMenuItem[i][j].addActionListener(this);
					jMenuItem[i][j].setMnemonic(menuItem[i][j].split("\\(")[1]
							.charAt(0));

					jMenu[i].add(jMenuItem[i][j]);
				} else {
					jCheckBoxMenuItem[j] = new JCheckBoxMenuItem(
							menuItem[i][j].split("\\|")[0]);
					if (menuItem[i][j].split("\\|").length != 1)
						jCheckBoxMenuItem[j].setAccelerator(KeyStroke
								.getKeyStroke(Integer.parseInt(menuItem[i][j]
										.split("\\|")[1]),
										ActionEvent.CTRL_MASK));
					jCheckBoxMenuItem[j].addActionListener(this);
					jCheckBoxMenuItem[j].setMnemonic(menuItem[i][j]
							.split("\\(")[1].charAt(0));
					jCheckBoxMenuItem[j].setSelected(true);
					jMenu[i].add(jCheckBoxMenuItem[j]);
				}
			}
		}
		////////////////////////this.setJMenuBar(bar);
		this.setLayout(new BorderLayout());
		for (i = 0; i < 5; i++)
			jPanel[i] = new JPanel();
	
		@SuppressWarnings("unused")
		BasicStroke stroke2 = (BasicStroke) stroke;
		stroke = new BasicStroke(5, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER);

		buttonGroup = new ButtonGroup();
		//����������
		JToolBar jToolBarBasic = new JToolBar("��������", JToolBar.VERTICAL);
		jToggleButtonBasic = new JToggleButton[ButtonNameBasic.length];
		JPanel jp1=new JPanel();
		jp1.add(jToolBarBasic);
		jp1.setLayout(new FlowLayout());
		jp1.setBounds(new Rectangle(0, 0, 100, 160));
		jp1.setBorder(new TitledBorder(null, "��������",
				TitledBorder.LEFT, TitledBorder.TOP));
		for (i = 0; i < ButtonNameBasic.length; i++) {
			//tool[i] = new ImageIcon(toolname[i]);
			//jToggleButtonBasic[i] = new JToggleButton(tool[i]);
			jToggleButtonBasic[i] = new JToggleButton();
			jToggleButtonBasic[i].setText(ButtonNameBasic[i]);
			jToggleButtonBasic[i].addActionListener(this);
			jToggleButtonBasic[i].setFocusable(false);
			buttonGroup.add(jToggleButtonBasic[i]);
		}
		jToolBarBasic.add(jToggleButtonBasic[0]);
		jToolBarBasic.add(jToggleButtonBasic[1]);
		jToolBarBasic.add(jToggleButtonBasic[2]);
		jToolBarBasic.add(jToggleButtonBasic[3]);
		jToolBarBasic.add(jToggleButtonBasic[4]);
		jToolBarBasic.add(jToggleButtonBasic[5]);
		jToolBarBasic.add(jToggleButtonBasic[6]);
		jToolBarBasic.add(jToggleButtonBasic[7]);
		jToolBarBasic.add(jToggleButtonBasic[8]);
		jToggleButtonBasic[0].setSelected(true);
		jToolBarBasic.setLayout(new GridLayout(3, 3, 2, 2));
		
		
		
		//UMLͼԪ
		JToolBar jToolBarUML=new JToolBar("UMLͼԪ",JToolBar.VERTICAL);
		jToggleButtonUML=new JToggleButton[ButtonNameUML.length];
		JPanel jp3=new JPanel();
		jp3.add(jToolBarUML);
		jp3.setLayout(new FlowLayout());
		jp3.setBounds(new Rectangle(0, 0, 100, 160));
		jp3.setBorder(new TitledBorder(null, "UMLͼԪ",
				TitledBorder.LEFT, TitledBorder.TOP));
		for (i = 0; i < ButtonNameUML.length; i++) {
			//tool[i] = new ImageIcon(toolname[i]);
			//jToggleButtonBasic[i] = new JToggleButton(tool[i]);
			jToggleButtonUML[i] = new JToggleButton();
			jToggleButtonUML[i].setText(ButtonNameUML[i]);
			jToggleButtonUML[i].addActionListener(this);
			jToggleButtonUML[i].setFocusable(false);
			buttonGroup.add(jToggleButtonUML[i]);
		}
		jToolBarUML.add(jToggleButtonUML[0]);
		jToolBarUML.add(jToggleButtonUML[1]);
		jToolBarUML.add(jToggleButtonUML[2]);
		jToolBarUML.add(jToggleButtonUML[3]);
		jToolBarUML.add(jToggleButtonUML[4]);
		jToolBarUML.add(jToggleButtonUML[5]);
		jToolBarUML.add(jToggleButtonUML[6]);
		jToolBarUML.add(jToggleButtonUML[7]);
		jToolBarUML.add(jToggleButtonUML[8]);
		jToolBarUML.add(jToggleButtonUML[9]);
		jToolBarUML.add(jToggleButtonUML[10]);
		jToolBarUML.add(jToggleButtonUML[11]);
		jToolBarUML.setLayout(new GridLayout(4, 3, 2, 2));
		//���ʴ�ϸ
		JPanel jp2=new JPanel();
	    lineWidthSelect = new JSpinner();
		lineWidthSelect.setValue(new Integer(5));
		lineWidthSelect.addChangeListener(this);
		
		jp2.add(lineWidthSelect);
		jp2.setLayout(new FlowLayout());
		jp2.setBounds(new Rectangle(0, 0, 100, 160));
		jp2.setBorder(new TitledBorder(null, "�߿�",
				TitledBorder.LEFT, TitledBorder.TOP));
		
		jPanel[2].setLayout(new GridLayout(3,1,2,2));
		jPanel[2].add(jp1);
		jPanel[2].add(jp2);
		jPanel[2].add(jp3);
		
		
		jToolBarBasic.setFloatable(false);// �޷��ƶ�
		jToolBarUML.setFloatable(false);

		drawPanel = new DrawPanel();
		underDrawPanel = new UnderDrawPanel();
		underDrawPanel.setLayout(null);
		underDrawPanel.add(drawPanel);
		drawPanel.setBounds(new Rectangle(2, 2, draw_panel_width,
				draw_panel_height));

		jPanel[0].setLayout(new BorderLayout());
		jPanel[0].add(underDrawPanel, BorderLayout.CENTER);
		jPanel[0].add(jPanel[2], BorderLayout.WEST);
		jPanel[0].add(jPanel[3], BorderLayout.SOUTH);
		jPanel[0].add(jPanel[4], BorderLayout.EAST);

		underDrawPanel.setBorder(BorderFactory
				.createBevelBorder(BevelBorder.LOWERED));
		underDrawPanel.setBackground(new Color(128, 128, 128));
		jPanel[3].setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
				new Color(172, 168, 153)));

		this.add(jPanel[0], BorderLayout.CENTER);
		
		
		setSize(1200, 700);
		////////////////////////////////setTitle("�װ�");
		/////////////////////////////////setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//show();
	}
     //�洢
	public void save() {
		FileDialog fileDialog = new FileDialog(new Frame(), "��ָ��һ���ļ���",
				FileDialog.SAVE);
		//fileDialog.show();
		if (fileDialog.getFile() == null)
			return;
		drawPanel.filename = fileDialog.getDirectory() + fileDialog.getFile();
	}

	//������������ť�¼�
	public void actionPerformed(ActionEvent e) {
		for (i = 0; i < ButtonNameBasic.length; i++) {
			if (e.getSource() == jToggleButtonBasic[i]) {
				drawMethod = i;
				drawPanel.clear();
				drawPanel.repaint();
			}
		}
		for (i = 0; i < ButtonNameUML.length; i++) {
			if (e.getSource() == jToggleButtonUML[i]) {
				drawMethod = i+9;
				drawPanel.clear();
				drawPanel.repaint();
				System.out.println("id:"+(i+9));
			}
		}
		BasicStroke stroke2 = (BasicStroke) stroke;
       //�������ʴ�ϸ�ĸı�
		if (e.getSource() == lineWidthSelect)
			stroke = new BasicStroke(stroke2.getLineWidth(),
					BasicStroke.CAP_ROUND, stroke2.getLineJoin(),
					stroke2.getMiterLimit(), stroke2.getDashArray(),
					stroke2.getDashPhase());

		
	

		if (e.getSource() == jMenuItem[1][0]) {
			drawPanel.undo();
		} else if (e.getSource() == jMenuItem[1][1]) {
			drawPanel.redo();
		} 
		else if (e.getSource() == jMenuItem[0][0]) {// �½�ͼֽ
			underDrawPanel.remove(drawPanel);
			drawPanel = null;
			drawPanel = new DrawPanel();
			underDrawPanel.add(drawPanel);
			drawPanel.setBounds(new Rectangle(2, 2, draw_panel_width,
					draw_panel_height));
			underDrawPanel.ctrl_area.setLocation(draw_panel_width + 3,
					draw_panel_height + 3);
			underDrawPanel.ctrl_area2.setLocation(draw_panel_width + 3,
					draw_panel_height / 2 + 3);
			underDrawPanel.ctrl_area3.setLocation(draw_panel_width / 2 + 3,
					draw_panel_height + 3);
			repaint();
		} else if (e.getSource() == jMenuItem[0][1]) {// ���ļ�
			FileDialog fileDialog = new FileDialog(new Frame(), "ѡ��һ���ĵ�",
					FileDialog.LOAD);
			//fileDialog.show();
			if (fileDialog.getFile() == null)
				return;
			underDrawPanel.removeAll();
			drawPanel = null;
			drawPanel = new DrawPanel();
			underDrawPanel.add(drawPanel);
			drawPanel.setBounds(new Rectangle(2, 2, draw_panel_width,
					draw_panel_height));

			drawPanel.openfile(fileDialog.getDirectory() + fileDialog.getFile());
		} else if (e.getSource() == jMenuItem[0][2]) {// �洢ͼֽ
			if (drawPanel.filename == null) {
				save();
			} else {
				try {
					int dotpos = drawPanel.filename.lastIndexOf('.');
					ImageIO.write(drawPanel.bufImg, drawPanel.filename
							.substring(dotpos + 1),
							new File(drawPanel.filename));
				} catch (IOException even) {
					JOptionPane.showMessageDialog(null, even.toString(),
							"�޷��洢�ĵ�", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (e.getSource() == jMenuItem[0][3]) {// ���ͼֽ
			save();
			try {
				int dotpos = drawPanel.filename.lastIndexOf('.');
				ImageIO.write(drawPanel.bufImg, drawPanel.filename
						.substring(dotpos + 1), new File(drawPanel.filename));
			} catch (IOException even) {
				JOptionPane.showMessageDialog(null, even.toString(), "�޷��洢�ĵ�",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == jMenuItem[0][4]) {// �˳�
			System.exit(0);
		} else if (e.getSource() == jMenuItem[3][0]) {// ����
		}
		
		for (i = 0; i < 1; i++) {
			if (jCheckBoxMenuItem[i].isSelected())
				jPanel[i + 2].setVisible(true);
			else
				jPanel[i + 2].setVisible(false);
		}
		
	}
	
	@Override//����ͨ����ť�ı仭�ʴ�ϸ
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
	
//������ͼ���	
	public class UnderDrawPanel extends JPanel implements MouseListener,
			MouseMotionListener {
		public int x, y;
		float data[] = { 2 };
		//3�������Ż����Сpanel
		public JPanel ctrl_area = new JPanel(), ctrl_area2 = new JPanel(),
				ctrl_area3 = new JPanel();

		public UnderDrawPanel() {
			this.setLayout(null);
			this.add(ctrl_area);//���½�С��
			this.add(ctrl_area2);//�ұ�С��
			this.add(ctrl_area3);//�±�С��

			ctrl_area.setBounds(new Rectangle(draw_panel_width + 3,
					draw_panel_height + 3, 5, 5));
			ctrl_area.setBackground(new Color(0, 0, 0));
			ctrl_area2.setBounds(new Rectangle(draw_panel_width + 3,
					draw_panel_height / 2, 5, 5));
			ctrl_area2.setBackground(new Color(0, 0, 0));
			ctrl_area3.setBounds(new Rectangle(draw_panel_width / 2,
					draw_panel_height + 3, 5, 5));
			ctrl_area3.setBackground(new Color(0, 0, 0));
			ctrl_area.addMouseListener(this);
			ctrl_area.addMouseMotionListener(this);
			ctrl_area2.addMouseListener(this);
			ctrl_area2.addMouseMotionListener(this);
			ctrl_area3.addMouseListener(this);
			ctrl_area3.addMouseMotionListener(this);
		}

		public void mouseClicked(MouseEvent e) {
		}
		public void mousePressed(MouseEvent e) {
		}
		
//����ͷź��ػ滭���С
		public void mouseReleased(MouseEvent e) {
			draw_panel_width = x;
			draw_panel_height = y;

			ctrl_area.setLocation(draw_panel_width + 3, draw_panel_height + 3);
			ctrl_area2.setLocation(draw_panel_width + 3,
					draw_panel_height / 2 + 3);
			ctrl_area3.setLocation(draw_panel_width / 2 + 3,
					draw_panel_height + 3);
			drawPanel.setSize(x, y);
			drawPanel.resize();
			repaint();
		}

		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}
  //����ƶ������м�¼������꣬���ı仭���С
		public void mouseDragged(MouseEvent e) {
			if (e.getSource() == ctrl_area2) {//�ұ�С��
				x = e.getX() + draw_panel_width;
				y = draw_panel_height;
			} else if (e.getSource() == ctrl_area3) {//�±�С��
				x = draw_panel_width;
				y = e.getY() + draw_panel_height;
			} else {//���½�С��
				x = e.getX() + draw_panel_width;
				y = e.getY() + draw_panel_height;
			}
			repaint();
		}

		public void mouseMoved(MouseEvent e) {
		}

		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			super.paint(g2d);

			g2d.setPaint(new Color(128, 128, 128));
			g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_MITER, 10, data, 0));
			g2d.draw(new Rectangle2D.Double(-1, -1, x + 3, y + 3));
		}
	}

//��ͼ���	
public class DrawPanel extends JPanel implements MouseListener,
	MouseMotionListener, ItemListener, ActionListener, ChangeListener {// ���뻭��
			
		PaintSender paintSender;//�ͻ��˿ɵ��õ�Զ�̷���������
		PaintReceiver paintReceiver;//�������ɵ��õ�Զ�̿ͻ��˶���
		BufferedImage imgPaint; //�ͻ��� ��imgPaint��ʱ�洢�ӷ��������͹�����img����
		int counter = 0;
public BufferedImage bufImg;//  ��¼���»��棬���ڴ�������
private BufferedImage bufImg_data[];//   ��¼���л���Բ�棬����ֵԽ��Խ�£����Ϊ����      
private JLabel jlbImg;
//count��¼����������,Press�ж������Ƿ�Ϊ�����
private int x1 = -1, y1 = -1, x2, y2, count, redo_lim, press,
		   click;
@SuppressWarnings("unused")
private float data[] = { 5 };
private Ellipse2D.Double ellipse2D_pan = new Ellipse2D.Double();
private BasicStroke basicStroke_pen = new BasicStroke(1,
		BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
///private BasicStroke basicStroke_select = new BasicStroke(1,
//		BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10, data, 0);

private Shape shape;
private ShapeModel shapeModel;
private MyLine myLine=new MyLine();//ֱ��
private MyRectangle myRectangle=new MyRectangle();//����
private MyRoundRectangle myRoundRectangle=new MyRoundRectangle();//Բ�Ǿ���
private MyBlackCircle myBlackCircle=new MyBlackCircle();//����Բ
private MyOval myOval=new MyOval();//��Բ
private MyClassShape myClassShape=new MyClassShape();//��ͼ
private MyPackageShape myPackageShape=new MyPackageShape();//��ͼ
private MyActorShape myActorShape=new MyActorShape();//С��
private MyAssoShape myAssoShape=new MyAssoShape();//��ϵ
private MyAggrShape myAggrShape=new MyAggrShape();//���
private MyCompoShape myCompoShape=new MyCompoShape();//���
private MyNodeShape myNodeShape=new MyNodeShape();//�ڵ�
private MyTimeShape myTimeShape=new MyTimeShape();//ʱ����

public String filename;
private JTextField textField_font = new JTextField("Fixedsys", 16),
		textField_word = new JTextField("JAVA", 16);
private int size = 100;
private JSpinner fontsize = new JSpinner();
private JDialog jDialog;
private JCheckBox bold, italic;
private JButton ok, cancel;
public int pie_shape = Arc2D.PIE;
private int valBold = Font.BOLD;
private int valItalic = Font.ITALIC;

public void resize() {// �ı��С
	bufImg = new ImgData(draw_panel_width, draw_panel_height,
			ImgData.TYPE_3BYTE_BGR);//������ʾ��ԭɫ�Ļ���
	jlbImg = new JLabel(new ImageIcon(bufImg));// ��JLabel�Ϸ���bufImg���Á��L�D
	this.removeAll();
	this.add(jlbImg);
	jlbImg.setBounds(new Rectangle(0, 0, draw_panel_width,
			draw_panel_height));

	// ����ԭ��ͼ��//
	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	g2d_bufImg.setPaint(Color.white);
	g2d_bufImg.fill(new Rectangle2D.Double(0, 0, draw_panel_width,
			draw_panel_height));
	g2d_bufImg.drawImage(bufImg_data[count], 0, 0, this);
	// ��¼�������������������������ɰ�
	redo_lim = count++;
	jMenuItem[1][1].setEnabled(false);

	// ����һ��BufferedImage�͑B��bufImg_data[count]���K��bufImg�L�u��bufImg_data[count]//
	bufImg_data[count] = new ImgData(draw_panel_width,
			draw_panel_height, ImgData.TYPE_3BYTE_BGR);
	Graphics2D g2d_bufImg_data = (Graphics2D) bufImg_data[count]
			.getGraphics();
	g2d_bufImg_data.drawImage(bufImg, 0, 0, this);

	// �ж�����Ϊ�����
	press = 0;

	// �ø�ԭMenuItem���Ե�ѡ
	if (count > 0)
		jMenuItem[1][0].setEnabled(true);
}

public DrawPanel() {
	String ip="172.25.67.165";
    String urlString="rmi://"+ip+":"+PaintSender.PORT+"/"+PaintSender.NAME;
	try {
		paintSender=(PaintSender) Naming.lookup(urlString);
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
	
	
	bufImg_data = new ImgData[1000];
	bufImg = new ImgData(draw_panel_width, draw_panel_height,
			ImgData.TYPE_3BYTE_BGR);
	jlbImg = new JLabel(new ImageIcon(bufImg));// ��JLabel�Ϸ���bufImg���Á��ͼ

	this.setLayout(null);
	this.add(jlbImg);
	jlbImg.setBounds(new Rectangle(0, 0, draw_panel_width,
			draw_panel_height));

	jMenuItem[1][0].setEnabled(false);
	jMenuItem[1][1].setEnabled(false);

	// �����հ�//
	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	g2d_bufImg.setPaint(Color.white);
	g2d_bufImg.fill(new Rectangle2D.Double(0, 0, draw_panel_width,
			draw_panel_height));

	bufImg_data[count] = new ImgData(draw_panel_width,
			draw_panel_height, ImgData.TYPE_3BYTE_BGR);
	Graphics2D g2d_bufImg_data = (Graphics2D) bufImg_data[count]
			.getGraphics();
	g2d_bufImg_data.drawImage(bufImg, 0, 0, this);
	
	// Font
	////////////////////////////////////jDialog = new JDialog(PaintBoard., "��ѡ�����֡����͡���С������", true);
	fontsize.setValue(new Integer(100));
	bold = new JCheckBox("����", true);
	italic = new JCheckBox("б��", true);
	ok = new JButton("ȷ��");
	cancel = new JButton("ȡ��");
	JPanel temp_0 = new JPanel(new GridLayout(5, 1));
	JPanel temp_1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel temp_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel temp_3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel temp_4 = new JPanel(new FlowLayout());
	JPanel temp_5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	////////////////////////////////////////Container jDialog_c = jDialog.getContentPane();

	////////////////////////////////////////jDialog_c.setLayout(new FlowLayout());
	//////////////////////////////////jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	/////////////////////////////////////jDialog.setSize(250, 200);
	temp_5.add(new JLabel("����:"));
	temp_5.add(textField_word);
	temp_1.add(new JLabel("���w:"));
	temp_1.add(textField_font);
	temp_2.add(new JLabel("��С:"));
	temp_2.add(fontsize);
	temp_3.add(new JLabel("����:"));
	temp_3.add(bold);
	temp_3.add(italic);
	temp_4.add(ok);
	temp_4.add(cancel);
	temp_0.add(temp_5);
	temp_0.add(temp_1);
	temp_0.add(temp_2);
	temp_0.add(temp_3);
	temp_0.add(temp_4);
	/////////////////////////////////////////jDialog_c.add(temp_0);

	bold.addItemListener(this);
	italic.addItemListener(this);
	fontsize.addChangeListener(this);
	ok.addActionListener(this);
	cancel.addActionListener(this);
	temp_0.setPreferredSize(new Dimension(180, 150));

	repaint();
	addMouseListener(this);
	addMouseMotionListener(this);
}

public void stateChanged(ChangeEvent e) {
	size = Integer.parseInt(fontsize.getValue().toString());
	if (size <= 0) {
		fontsize.setValue(new Integer(1));
		size = 1;
	}
}

public void actionPerformed(ActionEvent e) {
	///////////////////////////////////jDialog.dispose();
}

public void itemStateChanged(ItemEvent e) {
	if (e.getSource() == bold)
		if (e.getStateChange() == ItemEvent.SELECTED)
			valBold = Font.BOLD;
		else
			valBold = Font.PLAIN;
	if (e.getSource() == italic)
		if (e.getStateChange() == ItemEvent.SELECTED)
			valItalic = Font.ITALIC;
		else
			valItalic = Font.PLAIN;
}

public Dimension getPreferredSize() {
	return new Dimension(draw_panel_width, draw_panel_height);
}

public void openfile(String filename) {// �����ĵ�
	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	ImageIcon icon = new ImageIcon(filename);
	g2d_bufImg.drawImage(icon.getImage(), 0, 0, this);

	count++;
	bufImg_data[count] = new ImgData(draw_panel_width,
			draw_panel_height, ImgData.TYPE_3BYTE_BGR);
	Graphics2D g2d_bufImg_data = (Graphics2D) bufImg_data[count]
			.getGraphics();
	g2d_bufImg_data.drawImage(bufImg, 0, 0, this);

	repaint();
}

public void undo() {// ����
	count--;

	draw_panel_width = bufImg_data[count].getWidth();
	draw_panel_height = bufImg_data[count].getHeight();
	drawPanel.setSize(draw_panel_width, draw_panel_height);

	bufImg = new ImgData(draw_panel_width, draw_panel_height,
			ImgData.TYPE_3BYTE_BGR);
	jlbImg = new JLabel(new ImageIcon(bufImg));// ��JLabel�Ϸ���bufImg��������ͼ
	this.removeAll();
	this.add(jlbImg);
	jlbImg.setBounds(new Rectangle(0, 0, draw_panel_width,
			draw_panel_height));

	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	g2d_bufImg.setPaint(Color.white);
	g2d_bufImg.fill(new Rectangle2D.Double(0, 0, draw_panel_width,
			draw_panel_height));
	g2d_bufImg.drawImage(bufImg_data[count], 0, 0, this);
//�˴�����Զ�̵���//��g2d_bufImg��Ϊ�������ݸ�������,���װ�ÿ���һ�β������������µ�ͼ�����������
	try {
		//paintSender.sendPaint((ImgData)bufImg);
		paintSender.sendPaint(shapeModel);
		System.out.println("��ͻ��˷������� "+i);
	} catch (Exception e) {
		// TODO: handle exception
	}

	underDrawPanel.ctrl_area.setLocation(draw_panel_width + 3,
			draw_panel_height + 3);
	underDrawPanel.ctrl_area2.setLocation(draw_panel_width + 3,
			draw_panel_height / 2 + 3);
	underDrawPanel.ctrl_area3.setLocation(draw_panel_width / 2 + 3,
			draw_panel_height + 3);

	underDrawPanel.x = draw_panel_width;
	underDrawPanel.y = draw_panel_height;

	if (count <= 0)
		jMenuItem[1][0].setEnabled(false);
	jMenuItem[1][1].setEnabled(true);
	repaint();
}

public void redo() {// ����
	count++;

	draw_panel_width = bufImg_data[count].getWidth();
	draw_panel_height = bufImg_data[count].getHeight();
	drawPanel.setSize(draw_panel_width, draw_panel_height);

	bufImg = new ImgData(draw_panel_width, draw_panel_height,
			ImgData.TYPE_3BYTE_BGR);
	jlbImg = new JLabel(new ImageIcon(bufImg));// ��JLabel�Ϸ���bufImg���Á��ͼ
	this.removeAll();
	this.add(jlbImg);
	jlbImg.setBounds(new Rectangle(0, 0, draw_panel_width,
			draw_panel_height));

	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	g2d_bufImg.setPaint(Color.white);
	g2d_bufImg.fill(new Rectangle2D.Double(0, 0, draw_panel_width,
			draw_panel_height));
	g2d_bufImg.drawImage(bufImg_data[count], 0, 0, this);
	
//�˴�����Զ�̵���//��g2d_bufImg��Ϊ�������ݸ�������,���װ�ÿ���һ�β������������µ�ͼ�����������	
	try {
	//	paintSender.sendPaint((ImgData)bufImg);
		paintSender.sendPaint(shapeModel);
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	
	underDrawPanel.ctrl_area.setLocation(draw_panel_width + 3,
			draw_panel_height + 3);
	underDrawPanel.ctrl_area2.setLocation(draw_panel_width + 3,
			draw_panel_height / 2 + 3);
	underDrawPanel.ctrl_area3.setLocation(draw_panel_width / 2 + 3,
			draw_panel_height + 3);

	underDrawPanel.x = draw_panel_width;
	underDrawPanel.y = draw_panel_height;

	if (redo_lim < count)
		jMenuItem[1][1].setEnabled(false);
	jMenuItem[1][0].setEnabled(true);
	repaint();
}

public void mousePressed(MouseEvent e) {
	x1 = e.getX();
	y1 = e.getY();
	press = 1;//�Ѿ������£����������
}

public void mouseReleased(MouseEvent e) {
	x2 = e.getX();
	y2 = e.getY();
		repaint();
		toDraw();	
}

public void clear() {
	x1 = x2 = y1 = y2 = -1;
}

public void toDraw() {//����ͷź���������bufImg��
	if (x1 < 0 || y1 < 0)
		return;// ��ֹ��
	draw(x1, y1, x2, y2);
	// ����ͼ����bufImg//ÿ�εõ��Ļ��ʱ�����������paint��stroke������ΪĬ��ֵ
	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();

		if (drawMethod != 5&&drawMethod!=8) {//������Ƥ��������
			g2d_bufImg.setPaint(color_border);
			g2d_bufImg.setStroke(stroke);
			shapeModel.show(g2d_bufImg);
		}
		if(drawMethod != 5&&drawMethod==8){//���ֵ����
			g2d_bufImg.setPaint(color_border);
			g2d_bufImg.setStroke(stroke);
			g2d_bufImg.draw(shape);
		}

//�˴�����Զ�̵���//��g2d_bufImg��Ϊ�������ݸ�������,���װ�ÿ���һ�β������������µ�ͼ�����������
		try {
		//	paintSender.sendPaint((ImgData)bufImg);
			paintSender.sendPaint(shapeModel);
			System.out.println("��������������� ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
	repaint();
	clear();
	// ӛ䛿��������Δ����K׌�������ɰ�//
	redo_lim = count++;
	jMenuItem[1][1].setEnabled(false);

 //����һ��BufferedImage�͑B��bufImg_data[count]���K��bufImg�L�u��bufImg_data[count]
	bufImg_data[count] = new ImgData(draw_panel_width,
			draw_panel_height, ImgData.TYPE_3BYTE_BGR);
	Graphics2D g2d_bufImg_data = (Graphics2D) bufImg_data[count]
			.getGraphics();
	g2d_bufImg_data.drawImage(bufImg, 0, 0, this);

	// �Д����˞������c//
	press = 0;

	// ׌��ԭMenuItem�����c�x//
	if (count > 0)
		jMenuItem[1][0].setEnabled(true);
}

public void toDraw2() {//����ͷź���������bufImg��
	if (x1 < 0 || y1 < 0)
		return;// ��ֹ��
	draw(x1, y1, x2, y2);
	// ����ͼ����bufImg//ÿ�εõ��Ļ��ʱ�����������paint��stroke������ΪĬ��ֵ
	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();

		if (drawMethod != 5&&drawMethod!=8) {//������Ƥ��������
			g2d_bufImg.setPaint(color_border);
			g2d_bufImg.setStroke(stroke);
			shapeModel.show(g2d_bufImg);
		}
		if(drawMethod != 5&&drawMethod==8){//���ֵ����
			g2d_bufImg.setPaint(color_border);
			g2d_bufImg.setStroke(stroke);
			g2d_bufImg.draw(shape);
		}
		
	repaint();
	clear();
	// ӛ䛿��������Δ����K׌�������ɰ�//
	redo_lim = count++;
	jMenuItem[1][1].setEnabled(false);

 //����һ��BufferedImage�͑B��bufImg_data[count]���K��bufImg�L�u��bufImg_data[count]
	bufImg_data[count] = new ImgData(draw_panel_width,
			draw_panel_height, ImgData.TYPE_3BYTE_BGR);
	Graphics2D g2d_bufImg_data = (Graphics2D) bufImg_data[count]
			.getGraphics();
	g2d_bufImg_data.drawImage(bufImg, 0, 0, this);

	// �Д����˞������c//
	press = 0;

	// ׌��ԭMenuItem�����c�x//
	if (count > 0)
		jMenuItem[1][0].setEnabled(true);
}

public void mouseEntered(MouseEvent e) {
}

public void mouseExited(MouseEvent e) {
}

public void mouseClicked(MouseEvent e) {
	if (click == 1) {// �B�c���r
		toDraw();
	}
	click = 1;
}

public void mouseDragged(MouseEvent e) {
	x2 = e.getX();
	y2 = e.getY();//x2,y2��¼�ڶ����������

	if (drawMethod == 0 || drawMethod == 5) {//Ǧ�ʺ���Ƥ��
		draw(x1, y1, x2, y2);
		x1 = e.getX();
		y1 = e.getY();//��ʱ���ڶ����������Ϊ��һ�������꣬�����´�������ͼ
	}
	if (drawMethod != 8)//����
		repaint();
}

public void mouseMoved(MouseEvent e) {
	 x2 = e.getX();
	 y2 = e.getY();

	click = 0;
	if (drawMethod == 0 || drawMethod == 5)
		repaint();
}

@SuppressWarnings({ "unused", "deprecation" })
public void draw(int input_x1, int input_y1, int input_x2, int input_y2) {
	if (drawMethod == 1) {// ֱ�ߕr����shapeModelΪmyLine
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myLine.setPoint(startP, endP);
		shapeModel=myLine;
	} else if (drawMethod == 2) {// ����ʱ����shapeModelΪmyRectangle
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myRectangle.setPoint(startP, endP);
		shapeModel=myRectangle;
	} else if (drawMethod == 3) {// Բ�Ǿ���
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myRoundRectangle.setPoint(startP, endP);
		shapeModel=myRoundRectangle;
	} else if (drawMethod == 4) {// �E�A�r
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myOval.setPoint(startP, endP);
		shapeModel=myOval;
	}else if (drawMethod == 6) {// ����Բʱ
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myBlackCircle.setPoint(startP, endP);
		shapeModel=myBlackCircle;
	} else if (drawMethod == 7) {// ����
		
	} else if (drawMethod == 0 || drawMethod == 5) {// Ǧ�ʣ���Ƥ��
		Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
		Point startP=new Point(input_x1,input_y1);
		Point endP=new Point(input_x2,input_y2);
        myLine.setPoint(startP,endP);
		shapeModel= myLine;
		if (drawMethod == 0)//Ǧ��ʱ����������Ϊ��ɫ������Ϊ��ɫ
			g2d_bufImg.setPaint(color_border);
		else
			g2d_bufImg.setPaint(Color.white);
		g2d_bufImg.setStroke(stroke);//���µ������ʴ�ϸ
		myLine.show(g2d_bufImg);
	}
	else if (drawMethod == 8) {// ����
		Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
		FontRenderContext frc = g2d_bufImg.getFontRenderContext();
		///////////////////////////////////////jDialog.show();
		Font f = new Font(textField_font.getText(),
				valBold + valItalic, size);
		TextLayout tl = new TextLayout(textField_word.getText(), f, frc);
		double sw = tl.getBounds().getWidth();
		double sh = tl.getBounds().getHeight();

		AffineTransform Tx = AffineTransform.getScaleInstance(1, 1);
		Tx.translate(input_x2, input_y2 + sh);
		shape = tl.getOutline(Tx);
	}
	else if(drawMethod==9){//��ϵ��ͷ
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myAssoShape.setPoint(startP, endP);
		shapeModel=myAssoShape;
	}else if(drawMethod==10){//��ϼ�ͷ
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myAggrShape.setPoint(startP, endP);
		shapeModel=myAggrShape;
	}else if(drawMethod==11){//������ͷ
		
	}else if(drawMethod==12){//ʵ�ּ�ͷ
		
	}else if(drawMethod==13){//��ͼ
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myClassShape.setPoint(startP, endP);
		shapeModel=myClassShape;
	}else if(drawMethod==14){//��ͼ
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myPackageShape.setPoint(startP, endP);
		shapeModel=myPackageShape;
	}else if(drawMethod==15){//��ʼ״̬
		
	}else if(drawMethod==16){//����״̬
		
	}else if(drawMethod==17){//ʱ����
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myTimeShape.setPoint(startP, endP);
		shapeModel=myTimeShape;
	}else if(drawMethod==18){//С��
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myActorShape.setPoint(startP, endP);
		shapeModel=myActorShape;
	}else if(drawMethod==19){//�ڵ�
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myNodeShape.setPoint(startP, endP);
		shapeModel=myNodeShape;
	}else if(drawMethod==20){//���
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myCompoShape.setPoint(startP, endP);
		shapeModel=myCompoShape;
	}
}

public void paint(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	super.paint(g2d);// ���L�׌�JPanel�Լ���������Ԫ��
	
	if (press == 1 &&  !(x1 < 0 || y1 < 0)) {// �L�D���������JLabel�ϣ��K�Д��ǲ������c�Ů�
		draw(x1, y1, x2, y2);//
		if (drawMethod == 5)//��Ƥ��
			return;
		if(drawMethod!=8){ //��������
		g2d.setPaint(color_border);
		g2d.setStroke(stroke);
		shapeModel.show(g2d);
		}
        if(drawMethod==8)//������
		shapeModel.show(g2d);
        // g2d.draw(shape);
	}
	

	// �����α��Բ��//������Ƥ���α��Բ��
	if (drawMethod == 0 || drawMethod == 5) {//Ǧ�ʺ���Ƥ��
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

public void receiveShapeModel(ShapeModel model){
    shapeModel=model;
    System.out.println("�ͻ��˽�������");
    x1=model.startP.x;
    y1=model.startP.y;
    x2=model.endP.x;
    y2=model.endP.y;
    System.out.println("x1:"+x1+"y1:"+y1+"x2:"+x2+"y2:"+y2);
    drawMethod=model.id;
    
    toDraw2();
    repaint();
}

/*public void receivePaint(BufferedImage paint) {
	// TODO Auto-generated method stub
   imgPaint=paint;
  // Ȼ��paint��ʾ��paintBoard��drawPanel��
  // drawPanel.bufImg=(BufferedImage)imgPaint;
  Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	g2d_bufImg.setPaint(Color.white);
	g2d_bufImg.fill(new Rectangle2D.Double(0, 0, draw_panel_width,
			draw_panel_height));
	g2d_bufImg.drawImage(bufImg_data[count], 0, 0,this);
   // repaint();
}
*/
	}
public class PaintReceiverImp extends UnicastRemoteObject 
implements PaintReceiver{//�������ɵ��õĿͻ���Զ�̶���


protected PaintReceiverImp() throws RemoteException {
	super();
	// TODO Auto-generated constructor stub
}
@Override
public void receiveShapeModel(ShapeModel model) throws RemoteException {
	// TODO Auto-generated method stub
	drawPanel.receiveShapeModel(model);
}

}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		PaintBoard app = new PaintBoard();
		app.setVisible(true);
		//app.setExtendedState(Frame.MAXIMIZED_BOTH);
		//////////////////app.setLocationRelativeTo(null);
	}
}


