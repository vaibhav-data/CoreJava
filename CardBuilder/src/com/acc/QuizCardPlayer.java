package com.acc;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.channels.ShutdownChannelGroupException;
import java.util.ArrayList;
import java.util.MissingFormatArgumentException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class QuizCardPlayer {
	
	private JFrame frame;
	private JTextArea display;
	private JTextArea answer;
	private JButton nextButton;
	private ArrayList<QuizCard> cardList;
	private int currentcardIndex;
	private QuizCard currentCard;
	private boolean isShowAnswer;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QuizCardPlayer a = new QuizCardPlayer();
		a.go();
	}
	
	public void go() {
		frame = new JFrame("Quiz card player");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif",Font.BOLD,24);
		display = new JTextArea(6,30);
		display.setFont(bigFont);
		display.setLineWrap(true);
		display.setEditable(false);
		
		answer = new JTextArea(6,30);
		answer.setFont(bigFont);
		answer.setLineWrap(true);
		answer.setEditable(false);
		
		JScrollPane qScroll = new JScrollPane(display);
		qScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane aScroll = new JScrollPane(answer);
		aScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		nextButton = new JButton("Next card");
		nextButton.addActionListener(new NewCardListener());
		JLabel q = new JLabel("Question");
		q.setFont(bigFont);
		JLabel a = new JLabel("Answer");
		a.setFont(bigFont);
		
		mainPanel.add(qScroll);
		mainPanel.add(nextButton);
		
		JMenuBar menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuitem = new JMenuItem("Open");
		loadMenuitem.addActionListener(new OpenMenuListerner());
		fileMenu.add(loadMenuitem);
		menu.add(fileMenu);
		
		frame.setJMenuBar(menu);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(640, 500);
		frame.setVisible(true);
	}
	
	public class OpenMenuListerner implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser file = new JFileChooser();
			file.showOpenDialog(frame);
			loadFile(file.getSelectedFile());
			
		}
	}
	
	public class NewCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(isShowAnswer) {
				display.setText(currentCard.getAnswer());
				nextButton.setText("Next Card");
				isShowAnswer = false;
			}else {
				if(currentcardIndex < cardList.size()) {
					showNextCard();
				}
				else {
					display.setText("That was last card");
					nextButton.setEnabled(false);
				}
			}
			
		}
		
	}
	private void loadFile(File file) {
		cardList = new ArrayList<QuizCard>();
		try {
			BufferedReader r = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = r.readLine()) != null) {
				makeCard(line);
			}
			r.close();
		}catch (IOException e) {
			// TODO: handle exception
		}
	}
	
	public void makeCard(String l) {
		String [] result = l.split("/");
		QuizCard card = new QuizCard(result[0],result[1]);
		cardList.add(card);
		System.out.println("make a card");
	}
	
	public void showNextCard() {
		currentCard = cardList.get(currentcardIndex);
		currentcardIndex++;
		display.setText(currentCard.getQuestion());
		nextButton.setText("Show answer");
		isShowAnswer = true;
	}

}
