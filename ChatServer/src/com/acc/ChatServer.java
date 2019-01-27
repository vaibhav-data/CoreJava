package com.acc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatServer{
	
	ArrayList clientOutoutStream;
	
	public static void main(String[] args) {
		ChatServer a = new ChatServer();
		a.go();
	}
	
	public void go() {
		clientOutoutStream = new ArrayList<>();
		try {
			ServerSocket sock = new ServerSocket(5001);
			while(true) {
				Socket clientSocket = sock.accept();
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutoutStream.add(writer);
				
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				System.out.println("got a connection");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public class ClientHandler implements Runnable{
		BufferedReader reader;
		Socket sock;
		
		public ClientHandler(Socket clientSocket) {
			try {
				System.out.println("creating reader instance");
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String meesages;
			try {
				while((meesages= reader.readLine())!=null) {
					System.out.println("Calling send message method "+ meesages);
					tellEveryone(meesages);
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	public void tellEveryone(String message) {
		Iterator it = clientOutoutStream.iterator();
		while(it.hasNext()) {
			try {
			PrintWriter writer = (PrintWriter) it.next();
			System.out.println("sending message to clients" + message );
			writer.println(message);
			writer.flush();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		}
	}
}
