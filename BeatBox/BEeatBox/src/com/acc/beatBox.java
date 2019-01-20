package com.acc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.sampled.BooleanControl;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.SeparatorUI;
import javax.swing.plaf.basic.BasicTreeUI.TreeTraverseAction;
import javax.swing.text.MaskFormatter;
import javax.swing.text.html.StyleSheet.BoxPainter;

public class beatBox {
	
	JFrame frame;
	JPanel mainPanel;
	ArrayList<JCheckBox> checkBoxlist;
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	
	String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", 
		       "Open Hi-Hat","Acoustic Snare", "Crash Cymbal", "Hand Clap", 
		       "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", 
		       "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", 
		       "Open Hi Conga"};
		int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		beatBox a = new beatBox();
		a.buildGui();
	}
	
	public void buildGui() {
		//set frame
		frame = new JFrame("BeatBOX");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		
		//background black
		JPanel background = new JPanel();
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		//
		checkBoxlist = new ArrayList<JCheckBox>();
		Box boxButton = new Box(BoxLayout.Y_AXIS);
		
		JButton start = new JButton("start");
		start.addActionListener(new MyStartListner());
		boxButton.add(start);
		
		JButton stop = new JButton("stop");
		stop.addActionListener(new MyStopListner());
		boxButton.add(stop);
		
		JButton upTempo = new JButton("UpTempo");
		upTempo.addActionListener(new UpTempo());
		boxButton.add(upTempo);
		
		JButton downTempo = new JButton("DownTempo");
		downTempo.addActionListener(new DownTempo());
		boxButton.add(downTempo);
		
		Box nameBox = new Box(BoxLayout.Y_AXIS);
		for(int i = 0;i<16;i++) {
			nameBox.add(new Label(instrumentNames[i]));
		}
		
		background.add(BorderLayout.WEST,nameBox);
		background.add(BorderLayout.EAST,boxButton);
		
		GridLayout grid = new GridLayout(16, 16,1,2);
		mainPanel = new JPanel(grid);
		background.add(BorderLayout.CENTER,mainPanel);
		
		for(int i=0;i<256;i++) {
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			checkBoxlist.add(c);
			mainPanel.add(c);
		}
		
		setMidi();
		
		frame.setBounds(50,50,300,300);
		frame.pack();
		frame.setSize(1000,600);
		frame.getContentPane().add(background);
		frame.setVisible(true);
		}
	
	public void setMidi() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ,4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);	
		}catch (Exception e){ e.printStackTrace();}
	}
	
	public void buildTrackandStart() {
		int[] trackList = null;
		
		sequence.deleteTrack(track);
		track = sequence.createTrack();
		
		for(int i=0;i<16;i++) {
			trackList = new int[16];
			int key = instruments[i];
			
			for (int j = 0; j < 16; j++ ) {         
	              JCheckBox jc = (JCheckBox) checkBoxlist.get(j + (16*i));
	              
	              if ( jc.isSelected()) {
	                 trackList[j] = key;
	                 System.out.println(key);
	              } else {
	                 trackList[j] = 0;
	              	}
	              } 
			makeTrack(trackList);
			track.add(makeEvent(176, 1, 127, 0, 16));
		}
		track.add(makeEvent(192,9,1,0,15));
		try{
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
			sequencer.setTempoInBPM(120);
	}catch (Exception e) {e.printStackTrace();
		// TODO: handle exception
	}
		}
	
	public void makeTrack(int[] list) {
		for(int i = 0;i<16;i++) {
			int key = list[i];
			
			if(key!=0) {
				track.add(makeEvent(144,9,key,100,i));
				track.add(makeEvent(128, 9, key, 100, i+1));
				}
		}
	}
	
	public MidiEvent makeEvent(int comd,int chan,int one,int two,int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd,chan,one,two);
			event = new MidiEvent(a, tick);
		}catch (Exception e) { e.printStackTrace();}
		return event;
	}
	
	class MyStartListner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			buildTrackandStart();
		}
	}
		
	class MyStopListner implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			sequencer.stop();		}
	}
	
	class UpTempo implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor*1.03));
		}
	}
	
	class DownTempo implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor*0.97));
		}
	}
}
