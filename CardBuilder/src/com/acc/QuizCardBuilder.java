package com.acc;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

public class QuizCardBuilder {
	
	JFrame frame;
	ArrayList<QuizCard> cardList;
	JTextArea question;
	JTextArea answer;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QuizCardBuilder a = new QuizCardBuilder();
		a.go();

	}
	
	public void go() {
		//build the GUI for q and a
		frame = new JFrame("Quiz card builder");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif",Font.BOLD,24);
		
		question = new JTextArea(6,20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(bigFont);
		
		JScrollPane qScroll = new JScrollPane(question);
		qScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		answer = new JTextArea(6,20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);
		
		JScrollPane aScroll = new JScrollPane(answer);
		aScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JButton nextButton = new JButton("Next Card");
		
		cardList = new ArrayList<QuizCard>();
		JLabel qLabel = new JLabel("Question");
		JLabel aLabel = new JLabel("Answer");
		
		mainPanel.add(qLabel);
		mainPanel.add(qScroll);	
		mainPanel.add(aLabel);
		mainPanel.add(aScroll);
		mainPanel.add(nextButton);
		
		nextButton.addActionListener(new NextCardListner());
		//Create menu bar and its items
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		//add call backmethods
		newMenuItem.addActionListener(new NewMenuListner());
		saveMenuItem.addActionListener(new SaveMenuListner());
		menu.add(newMenuItem);
		menu.add(saveMenuItem);
		menuBar.add(menu);
		
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
		frame.setSize(500, 600);
		frame.setVisible(true);
		}
	
	public void clearCard() {
		question.setText("");
		answer.setText("");
		question.requestFocus();
	}
	
	
	public class NextCardListner implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			QuizCard card = new QuizCard(question.getText(), answer.getText());
			cardList.add(card);
			clearCard();
		}
	}
		
	public class NewMenuListner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			cardList.clear();
			clearCard();
			
		}
	}	
	public class SaveMenuListner implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			QuizCard card = new QuizCard(question.getText(), answer.getText());
			cardList.add(card);
			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());
		}
	}
	
	public void saveFile(File file) {
			try {
				FileWriter f = new FileWriter(file);
				BufferedWriter b = new BufferedWriter(f);
				
				for(QuizCard card:cardList) {
					b.write(card.getQuestion() + "/");
					b.write(card.getAnswer() + "\n");
				}
				b.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
}
