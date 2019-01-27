package com.acc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.SocketSecurityException;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class ChatClient {
	JTextArea incoming;
	JTextField outgoing;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChatClient a = new ChatClient();
		a.go();
	}
	
	public void go(){
		JFrame frame = new JFrame("Chat Client");
		JPanel mainPanel = new JPanel();
		incoming = new JTextArea(15,50);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller= new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing = new JTextField(20);
		JButton sendButton = new JButton("send");
		sendButton.addActionListener(new SendMessage());
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		setNetwork();
		
		Thread t = new Thread(new IncomingReader());
		t.start();
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		
		
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
	
	public void setNetwork(){
		try {
			sock = new Socket("127.0.0.1",5001);
			InputStreamReader steamReader= new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(steamReader);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("Networking established");
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
	}
	
	public class SendMessage implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				writer.println(outgoing.getText());
				writer.flush();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
	}
	
public class IncomingReader implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String message;
		try{
			while((message = reader.readLine())!=null) {
				System.out.println("reading message from server" + message);
				incoming.append(message + "\n");
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}

}
