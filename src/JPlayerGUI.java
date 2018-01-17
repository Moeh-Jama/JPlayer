import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class JPlayerGUI extends JFrame{
	private String songTitle="";
	private String currentSong="";
	private double setVolume = 0.1;
	ArrayList<Song> library = new ArrayList<Song>();
	
	
	final JFXPanel fxPanel = new JFXPanel();
	static MediaPlayer[] media = new MediaPlayer[1];
	public JPlayerGUI() {
		//Get the library.
		library = Main.returnLibrary();
		
		this.setTitle("JPlayer"+songTitle);	//Have it change so that the current song is shown
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		JPanel panel = new JPanel();
		
		//Functionalities
		JComboBox<?> songList = new JComboBox<Object>(makeMusicList());
		songList.setSelectedIndex(0);
		ListenForSelection Lsong = new ListenForSelection();
		songList.addActionListener(Lsong);
		panel.add(songList);
		
		//Buttons
		JButton Play = new JButton("PLAY");
		ListenForControl Pcontrols = new ListenForControl("Play");
		Play.addActionListener(Pcontrols);
		panel.add(Play);
		JButton Pause = new JButton("Pause");
		ListenForControl P2controls = new ListenForControl("Pause");
		Pause.addActionListener(P2controls);
		panel.add(Pause);
		JButton Next = new JButton("Next");
		ListenForControl Ncontrols = new ListenForControl("Next");
		Next.addActionListener(Ncontrols);
		panel.add(Next);
		JButton Back = new JButton("Back");
		ListenForControl Bcontrols = new ListenForControl("Back");
		Back.addActionListener(Bcontrols);
		panel.add(Back);
		
		//Song Duration
		int start_index = 0;
		int end_index = 100;
		int current_index = 0;
		JSlider song_duration = new JSlider(JSlider.HORIZONTAL, start_index, end_index, current_index);
		ListenForDuration lForDuration = new ListenForDuration();
		song_duration.addChangeListener(lForDuration);
		panel.add(song_duration);
		
		//Song volume
		int Lo_Volume = 0;
		int Hi_Volume = 100;
		int Curr_Volume = 5;    //initial frames per second

		JSlider volume = new JSlider(JSlider.HORIZONTAL,Lo_Volume, Hi_Volume, Curr_Volume);

		ListenForVolume lVolume = new ListenForVolume();
		volume.addChangeListener( lVolume);
		volume.setMajorTickSpacing(10);
		volume.setMinorTickSpacing(1);
		volume.setPaintTicks(true);
		volume.setPaintLabels(true);
		panel.add(volume);
		
		this.add(panel);
		this.setVisible(true);
		
	}
	private void changeTitle(String cSong) {
		songTitle = cSong;
		this.setTitle("JPlayer: "+songTitle);
	}
	private  String[] makeMusicList() {
		String s = "";
		for(Song i: library)
		{
			s+=i.getTitle()+"%";
		}
		String[] musicList = s.split("%");
		return musicList;
	}
	private String getSong(String songName) {
		for(Song song: library) {
			if(song.getTitle().equals(songName)) {
				return song.getPath();
			}
		}
		return "-1";
	}

	
	private int getCurrentSongIndex() {
		int index = 0;
		for(Song i: library) {
			if(i.getPath().equals(currentSong)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	
	
	private class ListenForSelection implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			JComboBox<?> g = (JComboBox<?>) event.getSource();
			String selectedSong = (String) g.getSelectedItem();
			String songToPlay = getSong(selectedSong);
			changeTitle(selectedSong);
		}
		
	}
	
	private class ListenForControl implements ActionListener{
		String currentAction;
		public ListenForControl(String string) {
			currentAction = string;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch(currentAction) {
			case "Play":{
				//Play song or Resume
				System.out.println("Playing Song");
				//If There is no current song then pick first song
				if(currentSong.equals("")) {
					currentSong = library.get(0).getPath();
					changeTitle(library.get(0).getTitle());
					Media hit = new Media(new File(currentSong).toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(hit);
					media[0] = mediaPlayer;
					media[0].play();
				}
				else {
					//else resume song
					media[0].play();
				}
				break;
			}
			case "Pause":{
				//Pause Song
				media[0].pause();
				break;
			}
			case "Next":{
				//forward song, in library
				int currentIndex = getCurrentSongIndex();
				if(currentIndex == library.size()-1) {
					currentSong = library.get(0).getPath();
					changeTitle(library.get(0).getTitle());
					Media hit = new Media(new File(currentSong).toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(hit);
					media[0].stop();
					media[0] = mediaPlayer;
					media[0].play();
				}else {
					currentSong = library.get(currentIndex+1).getPath();
					changeTitle(library.get(currentIndex+1).getTitle());
					Media hit = new Media(new File(currentSong).toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(hit);
					if(media[0]!=null) {
						media[0].stop();
					}
					media[0] = mediaPlayer;
					media[0].play();
				}
				break;
			}
			case "Back":{
				//play previous song.
				int currentIndex = getCurrentSongIndex();
				if(currentIndex == 0) {
					currentSong = library.get(library.size()-1).getPath();
					changeTitle(library.get(library.size()-1).getTitle());
					Media hit = new Media(new File(currentSong).toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(hit);
					if(media[0]!=null) {
						media[0].stop();
					}
					media[0] = mediaPlayer;
					media[0].play();
				}else {
					currentSong = library.get(currentIndex-1).getPath();
					changeTitle(library.get(currentIndex-1).getTitle());
					Media hit = new Media(new File(currentSong).toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(hit);
					media[0].stop();
					media[0] = mediaPlayer;
					media[0].play();
				}
				break;
			}
			}
		}
		
	}
	
	private class ListenForDuration implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent event) {
			// TODO Auto-generated method stub
			JSlider cb = (JSlider) event.getSource();
			double sliderValue = cb.getValue();
			double something  = (sliderValue/100);
			double proper_time_interval = media[0].getStopTime().toSeconds() * (something);
			media[0].seek(media[0].getStopTime().seconds(proper_time_interval));
		}
		
	}
	private class ListenForVolume implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			//Method is called when slider changes state.
			//When this occurs the slider value is assigned to 
			//SliderValue which is then divided by 100 
			//so as it adheres to the correct range values.
			JSlider cb = (JSlider)e.getSource();
			double sliderValue = cb.getValue();
			System.out.println("Volume is: "+sliderValue);
			setVolume  = (sliderValue/100);
			System.out.println(setVolume);
			media[0].setVolume(setVolume);
		}
		
	}
}
