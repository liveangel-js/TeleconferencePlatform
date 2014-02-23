package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



import logic.implement.ChatLogicImp;
import logic.interfaces.ChatLogic;
import manager.ClientManager;


public class ChatPanel extends JPanel {

	private JTextArea text_chat_area;

	private JTextField text_input;
	
	private ChatLogic controller;
	
	private JButton sendButton;

	public ChatPanel() {
		this.init();
	}

	// 初始化 界面
	private void init() {
		this.removeAll();
		controller=new ChatLogicImp(new MessageShower());
		this.setLayout(new BorderLayout());
		text_chat_area = new JTextArea(1, 1);
		text_chat_area.setLineWrap(true);
		text_chat_area.setWrapStyleWord(true);
		text_chat_area.setEditable(false);
		JScrollPane chat_scroll = new JScrollPane(text_chat_area);
		chat_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		text_input = new JTextField(9);
		 sendButton = new JButton("send");
		sendButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
		sendButton.addActionListener(new SendButtonListener());
		this.add(chat_scroll,BorderLayout.CENTER);
		JPanel input_panel=new JPanel();
		input_panel.add(text_input);
		input_panel.add(sendButton);
		this.add(input_panel,BorderLayout.SOUTH);
		Font f = new Font("微软雅黑", 0, 10);
		text_input.setFont(f);
		endMeeting();
	}
	
	public void startMeeting(){
		text_input.setEditable(true);
		sendButton.setEnabled(true);
	}
	
	
	public void endMeeting(){
		text_input.setEditable(false);
		sendButton.setEnabled(false);
	}
	
	
	
	
	public class MessageShower{
		//提供给逻辑层的接口以显示接收数据
		public void show(String message){
			text_chat_area.append(message);
		}
	}


	// 按钮监听事件,聊天输入
	private class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String message = text_input.getText();
			if(message.equals("")){
				JOptionPane.showMessageDialog(null, "请输入内容！");
				return;
			}
			controller.sendMessage(message);
			text_input.setText("");
		}
	}

	public static void main(String args[]) {
		ClientManager.setSystemLookAndFeel();
		JFrame frame = new JFrame("Simple Chat Client");
		frame.getContentPane().add(BorderLayout.CENTER, new ChatPanel());
		frame.setSize(300, 400);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
