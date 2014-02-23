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
	
	
	////////////////////////////////////private JPanel c=new JPanel();//主panel
	private String menuBar[] = { "文件(F)", "编辑(E)", "视图(V)", "说明(H)" };//菜单栏名称
	private String menuItem[][] = { //菜单选项名称
			{ "新建(N)|78", "打开(O)|79", "保存(S)|83", "另存为(A)", "退出(X)|88" },
			{ "撤消(U)|90", "重复(R)|89",  },
			{ "工具箱(T)|84" }, { "关于白板(A)" } };
	
	//, "状态栏(S)", "属性栏(M)", "色块(C)|76""剪切(T)|87", "复制(C)|68", "粘贴(P)|85"
	private JMenuItem jMenuItem[][] = new JMenuItem[4][5];//菜单选项
	private JMenu jMenu[];//菜单栏
	//工具箱和色块组成的复选框选项
	private JCheckBoxMenuItem jCheckBoxMenuItem[] = new JCheckBoxMenuItem[2];
	//private String ButtonName[] = { "直线", "矩型", "椭圆", "圆角矩形", "贝氏曲线", "扇型",
	//		"多边形", "铅笔", "橡皮擦", "文字", "选取" };
	private String ButtonNameBasic[]={"铅笔","直线","矩形","圆角矩形","椭圆","橡皮擦","黑心圆",
			 "菱形","文字"};
	private String ButtonNameUML[]={"联系箭头","组合箭头","依赖箭头","实现箭头","类图","包图",
			"开始状态","结束状态","时间柱","小人","节点","组件"};
	private JToggleButton jToggleButtonBasic[];
	private JSpinner lineWidthSelect;
	public int number = 5;
	private JToggleButton jToggleButtonUML[];
	private ButtonGroup buttonGroup;
	private JPanel jPanel[] = new JPanel[5];// 0内层,1绘图区,2工具箱,3色彩,4属性栏
	/*private String toolname[] = { "img/tool1.gif", "img/tool2.gif",
			"img/tool3.gif", "img/tool4.gif", "img/tool5.gif", "img/tool8.gif",
			"img/tool9.gif", "img/tool7.gif", "img/tool6.gif",
			"img/tool10.gif", "img/tool11.gif" };
			*/
	//private Icon tool[] = new ImageIcon[11];
	private int i, j;
	private int drawMethod = 0;//记录要画的是哪种图形 
	private int draw_panel_width = 900;//画板默认宽度
	private int draw_panel_height = 550;//画板默认高度
	public  DrawPanel drawPanel;//画板面板
	public  UnderDrawPanel underDrawPanel;//画板背景面板

	private Stroke stroke;
	@SuppressWarnings("unused")
	private String isFilled;
	private Paint color_border; 
	
	//设定JMenuBar,并产生JMenuItem、设置快捷键	
	public PaintBoard() {
	
		
		color_border=new Color(0,0,0);//将画笔设置为黑色
		
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
		//基本工具栏
		JToolBar jToolBarBasic = new JToolBar("基本工具", JToolBar.VERTICAL);
		jToggleButtonBasic = new JToggleButton[ButtonNameBasic.length];
		JPanel jp1=new JPanel();
		jp1.add(jToolBarBasic);
		jp1.setLayout(new FlowLayout());
		jp1.setBounds(new Rectangle(0, 0, 100, 160));
		jp1.setBorder(new TitledBorder(null, "基本工具",
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
		
		
		
		//UML图元
		JToolBar jToolBarUML=new JToolBar("UML图元",JToolBar.VERTICAL);
		jToggleButtonUML=new JToggleButton[ButtonNameUML.length];
		JPanel jp3=new JPanel();
		jp3.add(jToolBarUML);
		jp3.setLayout(new FlowLayout());
		jp3.setBounds(new Rectangle(0, 0, 100, 160));
		jp3.setBorder(new TitledBorder(null, "UML图元",
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
		//画笔粗细
		JPanel jp2=new JPanel();
	    lineWidthSelect = new JSpinner();
		lineWidthSelect.setValue(new Integer(5));
		lineWidthSelect.addChangeListener(this);
		
		jp2.add(lineWidthSelect);
		jp2.setLayout(new FlowLayout());
		jp2.setBounds(new Rectangle(0, 0, 100, 160));
		jp2.setBorder(new TitledBorder(null, "边框",
				TitledBorder.LEFT, TitledBorder.TOP));
		
		jPanel[2].setLayout(new GridLayout(3,1,2,2));
		jPanel[2].add(jp1);
		jPanel[2].add(jp2);
		jPanel[2].add(jp3);
		
		
		jToolBarBasic.setFloatable(false);// 无法移动
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
		////////////////////////////////setTitle("白板");
		/////////////////////////////////setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//show();
	}
     //存储
	public void save() {
		FileDialog fileDialog = new FileDialog(new Frame(), "请指定一个文件名",
				FileDialog.SAVE);
		//fileDialog.show();
		if (fileDialog.getFile() == null)
			return;
		drawPanel.filename = fileDialog.getDirectory() + fileDialog.getFile();
	}

	//监听工具栏按钮事件
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
       //监听画笔粗细的改变
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
		else if (e.getSource() == jMenuItem[0][0]) {// 新建图纸
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
		} else if (e.getSource() == jMenuItem[0][1]) {// 打开文件
			FileDialog fileDialog = new FileDialog(new Frame(), "选择一个文档",
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
		} else if (e.getSource() == jMenuItem[0][2]) {// 存储图纸
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
							"无法存储文档", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (e.getSource() == jMenuItem[0][3]) {// 另存图纸
			save();
			try {
				int dotpos = drawPanel.filename.lastIndexOf('.');
				ImageIO.write(drawPanel.bufImg, drawPanel.filename
						.substring(dotpos + 1), new File(drawPanel.filename));
			} catch (IOException even) {
				JOptionPane.showMessageDialog(null, even.toString(), "无法存储文档",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == jMenuItem[0][4]) {// 退出
			System.exit(0);
		} else if (e.getSource() == jMenuItem[3][0]) {// 关于
		}
		
		for (i = 0; i < 1; i++) {
			if (jCheckBoxMenuItem[i].isSelected())
				jPanel[i + 2].setVisible(true);
			else
				jPanel[i + 2].setVisible(false);
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
	
//背景画图面板	
	public class UnderDrawPanel extends JPanel implements MouseListener,
			MouseMotionListener {
		public int x, y;
		float data[] = { 2 };
		//3个可缩放画板的小panel
		public JPanel ctrl_area = new JPanel(), ctrl_area2 = new JPanel(),
				ctrl_area3 = new JPanel();

		public UnderDrawPanel() {
			this.setLayout(null);
			this.add(ctrl_area);//右下角小块
			this.add(ctrl_area2);//右边小块
			this.add(ctrl_area3);//下边小块

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
		
//鼠标释放后重绘画板大小
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
  //鼠标移动过程中记录鼠标坐标，并改变画板大小
		public void mouseDragged(MouseEvent e) {
			if (e.getSource() == ctrl_area2) {//右边小块
				x = e.getX() + draw_panel_width;
				y = draw_panel_height;
			} else if (e.getSource() == ctrl_area3) {//下边小块
				x = draw_panel_width;
				y = e.getY() + draw_panel_height;
			} else {//右下角小块
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

//画图面板	
public class DrawPanel extends JPanel implements MouseListener,
	MouseMotionListener, ItemListener, ActionListener, ChangeListener {// 中央画布
			
		PaintSender paintSender;//客户端可调用的远程服务器对象
		PaintReceiver paintReceiver;//服务器可调用的远程客户端对象
		BufferedImage imgPaint; //客户端 用imgPaint暂时存储从服务器发送过来的img数据
		int counter = 0;
public BufferedImage bufImg;//  记录最新画面，并在此上作画
private BufferedImage bufImg_data[];//   记录所有画出圆面，索引值越大越新，最大为最新      
private JLabel jlbImg;
//count记录可重做次数,Press判断坐标是否为新起点
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
private MyLine myLine=new MyLine();//直线
private MyRectangle myRectangle=new MyRectangle();//矩形
private MyRoundRectangle myRoundRectangle=new MyRoundRectangle();//圆角矩形
private MyBlackCircle myBlackCircle=new MyBlackCircle();//黑心圆
private MyOval myOval=new MyOval();//椭圆
private MyClassShape myClassShape=new MyClassShape();//类图
private MyPackageShape myPackageShape=new MyPackageShape();//包图
private MyActorShape myActorShape=new MyActorShape();//小人
private MyAssoShape myAssoShape=new MyAssoShape();//联系
private MyAggrShape myAggrShape=new MyAggrShape();//组合
private MyCompoShape myCompoShape=new MyCompoShape();//组件
private MyNodeShape myNodeShape=new MyNodeShape();//节点
private MyTimeShape myTimeShape=new MyTimeShape();//时间柱

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

public void resize() {// 改变大小
	bufImg = new ImgData(draw_panel_width, draw_panel_height,
			ImgData.TYPE_3BYTE_BGR);//可以显示三原色的画布
	jlbImg = new JLabel(new ImageIcon(bufImg));// 在JLabel上放置bufImg，用來繪圖
	this.removeAll();
	this.add(jlbImg);
	jlbImg.setBounds(new Rectangle(0, 0, draw_panel_width,
			draw_panel_height));

	// 画出原本图形//
	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	g2d_bufImg.setPaint(Color.white);
	g2d_bufImg.fill(new Rectangle2D.Double(0, 0, draw_panel_width,
			draw_panel_height));
	g2d_bufImg.drawImage(bufImg_data[count], 0, 0, this);
	// 记录可重做最大次数，并让重做不可按
	redo_lim = count++;
	jMenuItem[1][1].setEnabled(false);

	// 新增一張BufferedImage型態至bufImg_data[count]，並將bufImg繪製至bufImg_data[count]//
	bufImg_data[count] = new ImgData(draw_panel_width,
			draw_panel_height, ImgData.TYPE_3BYTE_BGR);
	Graphics2D g2d_bufImg_data = (Graphics2D) bufImg_data[count]
			.getGraphics();
	g2d_bufImg_data.drawImage(bufImg, 0, 0, this);

	// 判断坐标为新起点
	press = 0;

	// 让复原MenuItem可以点选
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
	jlbImg = new JLabel(new ImageIcon(bufImg));// 在JLabel上放置bufImg，用來绘图

	this.setLayout(null);
	this.add(jlbImg);
	jlbImg.setBounds(new Rectangle(0, 0, draw_panel_width,
			draw_panel_height));

	jMenuItem[1][0].setEnabled(false);
	jMenuItem[1][1].setEnabled(false);

	// 画出空白//
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
	////////////////////////////////////jDialog = new JDialog(PaintBoard., "请选择文字、字型、大小与属性", true);
	fontsize.setValue(new Integer(100));
	bold = new JCheckBox("粗体", true);
	italic = new JCheckBox("斜体", true);
	ok = new JButton("确定");
	cancel = new JButton("取消");
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
	temp_5.add(new JLabel("文字:"));
	temp_5.add(textField_word);
	temp_1.add(new JLabel("字體:"));
	temp_1.add(textField_font);
	temp_2.add(new JLabel("大小:"));
	temp_2.add(fontsize);
	temp_3.add(new JLabel("屬性:"));
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

public void openfile(String filename) {// 开启文档
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

public void undo() {// 撤销
	count--;

	draw_panel_width = bufImg_data[count].getWidth();
	draw_panel_height = bufImg_data[count].getHeight();
	drawPanel.setSize(draw_panel_width, draw_panel_height);

	bufImg = new ImgData(draw_panel_width, draw_panel_height,
			ImgData.TYPE_3BYTE_BGR);
	jlbImg = new JLabel(new ImageIcon(bufImg));// 在JLabel上放置bufImg，用来绘图
	this.removeAll();
	this.add(jlbImg);
	jlbImg.setBounds(new Rectangle(0, 0, draw_panel_width,
			draw_panel_height));

	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	g2d_bufImg.setPaint(Color.white);
	g2d_bufImg.fill(new Rectangle2D.Double(0, 0, draw_panel_width,
			draw_panel_height));
	g2d_bufImg.drawImage(bufImg_data[count], 0, 0, this);
//此处进行远程调用//将g2d_bufImg作为参数传递给服务器,即白板每完成一次操作，即将最新的图像传输给服务器
	try {
		//paintSender.sendPaint((ImgData)bufImg);
		paintSender.sendPaint(shapeModel);
		System.out.println("向客户端发送数据 "+i);
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

public void redo() {// 重做
	count++;

	draw_panel_width = bufImg_data[count].getWidth();
	draw_panel_height = bufImg_data[count].getHeight();
	drawPanel.setSize(draw_panel_width, draw_panel_height);

	bufImg = new ImgData(draw_panel_width, draw_panel_height,
			ImgData.TYPE_3BYTE_BGR);
	jlbImg = new JLabel(new ImageIcon(bufImg));// 在JLabel上放置bufImg，用來绘图
	this.removeAll();
	this.add(jlbImg);
	jlbImg.setBounds(new Rectangle(0, 0, draw_panel_width,
			draw_panel_height));

	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
	g2d_bufImg.setPaint(Color.white);
	g2d_bufImg.fill(new Rectangle2D.Double(0, 0, draw_panel_width,
			draw_panel_height));
	g2d_bufImg.drawImage(bufImg_data[count], 0, 0, this);
	
//此处进行远程调用//将g2d_bufImg作为参数传递给服务器,即白板每完成一次操作，即将最新的图像传输给服务器	
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
	press = 1;//已经被按下，不是新起点
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

public void toDraw() {//鼠标释放后真正画到bufImg上
	if (x1 < 0 || y1 < 0)
		return;// 防止误按
	draw(x1, y1, x2, y2);
	// 画出图形至bufImg//每次得到的画笔必须重新设置paint和stroke，否则为默认值
	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();

		if (drawMethod != 5&&drawMethod!=8) {//不是橡皮擦、文字
			g2d_bufImg.setPaint(color_border);
			g2d_bufImg.setStroke(stroke);
			shapeModel.show(g2d_bufImg);
		}
		if(drawMethod != 5&&drawMethod==8){//文字的情况
			g2d_bufImg.setPaint(color_border);
			g2d_bufImg.setStroke(stroke);
			g2d_bufImg.draw(shape);
		}

//此处进行远程调用//将g2d_bufImg作为参数传递给服务器,即白板每完成一次操作，即将最新的图像传输给服务器
		try {
		//	paintSender.sendPaint((ImgData)bufImg);
			paintSender.sendPaint(shapeModel);
			System.out.println("向服务器发送数据 ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
	repaint();
	clear();
	// 記錄可重做最大次數，並讓重做不可按//
	redo_lim = count++;
	jMenuItem[1][1].setEnabled(false);

 //新增一張BufferedImage型態至bufImg_data[count]，並將bufImg繪製至bufImg_data[count]
	bufImg_data[count] = new ImgData(draw_panel_width,
			draw_panel_height, ImgData.TYPE_3BYTE_BGR);
	Graphics2D g2d_bufImg_data = (Graphics2D) bufImg_data[count]
			.getGraphics();
	g2d_bufImg_data.drawImage(bufImg, 0, 0, this);

	// 判斷座標為新起點//
	press = 0;

	// 讓復原MenuItem可以點選//
	if (count > 0)
		jMenuItem[1][0].setEnabled(true);
}

public void toDraw2() {//鼠标释放后真正画到bufImg上
	if (x1 < 0 || y1 < 0)
		return;// 防止误按
	draw(x1, y1, x2, y2);
	// 画出图形至bufImg//每次得到的画笔必须重新设置paint和stroke，否则为默认值
	Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();

		if (drawMethod != 5&&drawMethod!=8) {//不是橡皮擦、文字
			g2d_bufImg.setPaint(color_border);
			g2d_bufImg.setStroke(stroke);
			shapeModel.show(g2d_bufImg);
		}
		if(drawMethod != 5&&drawMethod==8){//文字的情况
			g2d_bufImg.setPaint(color_border);
			g2d_bufImg.setStroke(stroke);
			g2d_bufImg.draw(shape);
		}
		
	repaint();
	clear();
	// 記錄可重做最大次數，並讓重做不可按//
	redo_lim = count++;
	jMenuItem[1][1].setEnabled(false);

 //新增一張BufferedImage型態至bufImg_data[count]，並將bufImg繪製至bufImg_data[count]
	bufImg_data[count] = new ImgData(draw_panel_width,
			draw_panel_height, ImgData.TYPE_3BYTE_BGR);
	Graphics2D g2d_bufImg_data = (Graphics2D) bufImg_data[count]
			.getGraphics();
	g2d_bufImg_data.drawImage(bufImg, 0, 0, this);

	// 判斷座標為新起點//
	press = 0;

	// 讓復原MenuItem可以點選//
	if (count > 0)
		jMenuItem[1][0].setEnabled(true);
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

public void mouseDragged(MouseEvent e) {
	x2 = e.getX();
	y2 = e.getY();//x2,y2记录第二个点的坐标

	if (drawMethod == 0 || drawMethod == 5) {//铅笔和橡皮擦
		draw(x1, y1, x2, y2);
		x1 = e.getX();
		y1 = e.getY();//此时将第二个点坐标变为第一个点坐标，方便下次连续作图
	}
	if (drawMethod != 8)//文字
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
	if (drawMethod == 1) {// 直线時，让shapeModel为myLine
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myLine.setPoint(startP, endP);
		shapeModel=myLine;
	} else if (drawMethod == 2) {// 矩形时，让shapeModel为myRectangle
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myRectangle.setPoint(startP, endP);
		shapeModel=myRectangle;
	} else if (drawMethod == 3) {// 圆角矩型
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myRoundRectangle.setPoint(startP, endP);
		shapeModel=myRoundRectangle;
	} else if (drawMethod == 4) {// 橢圓時
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myOval.setPoint(startP, endP);
		shapeModel=myOval;
	}else if (drawMethod == 6) {// 黑心圆时
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myBlackCircle.setPoint(startP, endP);
		shapeModel=myBlackCircle;
	} else if (drawMethod == 7) {// 菱形
		
	} else if (drawMethod == 0 || drawMethod == 5) {// 铅笔＆橡皮擦
		Graphics2D g2d_bufImg = (Graphics2D) bufImg.getGraphics();
		Point startP=new Point(input_x1,input_y1);
		Point endP=new Point(input_x2,input_y2);
        myLine.setPoint(startP,endP);
		shapeModel= myLine;
		if (drawMethod == 0)//铅笔时将画笔设置为黑色，否则为白色
			g2d_bufImg.setPaint(color_border);
		else
			g2d_bufImg.setPaint(Color.white);
		g2d_bufImg.setStroke(stroke);//重新调整画笔粗细
		myLine.show(g2d_bufImg);
	}
	else if (drawMethod == 8) {// 文字
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
	else if(drawMethod==9){//联系箭头
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myAssoShape.setPoint(startP, endP);
		shapeModel=myAssoShape;
	}else if(drawMethod==10){//组合箭头
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myAggrShape.setPoint(startP, endP);
		shapeModel=myAggrShape;
	}else if(drawMethod==11){//依赖箭头
		
	}else if(drawMethod==12){//实现箭头
		
	}else if(drawMethod==13){//类图
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myClassShape.setPoint(startP, endP);
		shapeModel=myClassShape;
	}else if(drawMethod==14){//包图
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myPackageShape.setPoint(startP, endP);
		shapeModel=myPackageShape;
	}else if(drawMethod==15){//开始状态
		
	}else if(drawMethod==16){//结束状态
		
	}else if(drawMethod==17){//时间柱
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myTimeShape.setPoint(startP, endP);
		shapeModel=myTimeShape;
	}else if(drawMethod==18){//小人
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myActorShape.setPoint(startP, endP);
		shapeModel=myActorShape;
	}else if(drawMethod==19){//节点
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myNodeShape.setPoint(startP, endP);
		shapeModel=myNodeShape;
	}else if(drawMethod==20){//组件
		Point startP=new Point(input_x1, input_y1);
		Point endP=new Point(input_x2, input_y2);
		myCompoShape.setPoint(startP, endP);
		shapeModel=myCompoShape;
	}
}

public void paint(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	super.paint(g2d);// 重繪底層JPanel以及上面所有元件
	
	if (press == 1 &&  !(x1 < 0 || y1 < 0)) {// 繪圖在最上面的JLabel上，並判斷是不是起點才畫
		draw(x1, y1, x2, y2);//
		if (drawMethod == 5)//橡皮擦
			return;
		if(drawMethod!=8){ //不是文字
		g2d.setPaint(color_border);
		g2d.setStroke(stroke);
		shapeModel.show(g2d);
		}
        if(drawMethod==8)//是文字
		shapeModel.show(g2d);
        // g2d.draw(shape);
	}
	

	// 跟踪游标的圆形//跟踪橡皮擦游标的圆形
	if (drawMethod == 0 || drawMethod == 5) {//铅笔和橡皮擦
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
    System.out.println("客户端接受数据");
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
  // 然后将paint显示到paintBoard的drawPanel上
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
implements PaintReceiver{//服务器可调用的客户端远程对象


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


