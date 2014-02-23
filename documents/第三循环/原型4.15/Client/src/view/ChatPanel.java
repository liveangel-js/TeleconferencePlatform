package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import logic.ChatController;
import logic.ChatLogicInterface;

public class ChatPanel extends JPanel {

	private JTextArea incoming;

	private JTextField outgoing;
	
	private ChatLogicInterface controller;

	public ChatPanel() {
		this.init();
	}

	// 初始化 界面
	public void init() {
		controller=new ChatController();
		MessageReader reader=new MessageReader();
		reader.start();
		incoming = new JTextArea(15, 50);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		outgoing = new JTextField(20);
		JButton sendButton = new JButton("send");
		sendButton.addActionListener(new SendButtonListener());
		this.add(qScroller);
		this.add(outgoing);
		this.add(sendButton);
	}

	// 按钮监听事件
	private class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String message = outgoing.getText();
			controller.sendMessage(message); // 将输入的文字信息给逻辑层处理
			outgoing.setText("");
			outgoing.requestFocus();
		}
	}
	
	private class MessageReader extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				String message=controller.getMessage();
				incoming.append(message);
			}
		}
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame("Ludicrously Simple Chat Client");
		frame.getContentPane().add(BorderLayout.CENTER, new ChatPanel());
		frame.setSize(800, 400);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
