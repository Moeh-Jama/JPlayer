import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class JPlayerGUI extends JFrame{
	
	
	private static JFrame frame = new JFrame();
	
	private String songTitle="";
	private String currentSong="";
	private double setVolume = 0.1;
	ArrayList<Song> library = new ArrayList<Song>();
	
	
	final JFXPanel fxPanel = new JFXPanel();
	static MediaPlayer[] media = new MediaPlayer[1];
	
	private static JPanel images = new JPanel();
	private static ProfileImage imageHolder;// = new ProfileImage();
	
	public JPlayerGUI() {
		//Get the library.
		library = Main.returnLibrary();
		
		frame.setTitle("JPlayer"+songTitle);	//Have it change so that the current song is shown
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(585,  500);
		JPanel mainPanel = new JPanel(new BorderLayout());
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
		song_duration.addMouseListener(lForDuration);
		//song_duration.addChangeListener(lForDuration);
		panel.add(song_duration);
		
		//Song volume
		int Lo_Volume = 0;
		int Hi_Volume = 100;
		int Curr_Volume = 5;    //initial frames per second

		JSlider volume = new JSlider(JSlider.HORIZONTAL,Lo_Volume, Hi_Volume, Curr_Volume);

		ListenForVolume lVolume = new ListenForVolume();
		volume.addMouseListener( lVolume);
		volume.setMajorTickSpacing(10);
		volume.setMinorTickSpacing(1);
		volume.setPaintTicks(true);
		volume.setPaintLabels(true);
		
		panel.add(volume);
		setUpImage();
		JPanel other = new JPanel();
		other.add(images);
		panel.setPreferredSize(new Dimension(500, 150));
		other.setPreferredSize(new Dimension(500, 300));
		mainPanel.add(panel, BorderLayout.NORTH);
		mainPanel.add(other, BorderLayout.SOUTH);
		//this.add(panel);
		frame.add(mainPanel);
		frame.setVisible(true);
		
	}
	private void setUpImage() {
		// TODO Auto-generated method stub
		imageHolder = new ProfileImage(ProfileImage.getImage("MainImage.png"))
				;
		imageHolder.setPreferredSize(new Dimension(250, 250));
		images.add(imageHolder);
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
					frame.repaint();
					
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
					frame.repaint();
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
					frame.repaint();
				}else {
					currentSong = library.get(currentIndex-1).getPath();
					changeTitle(library.get(currentIndex-1).getTitle());
					Media hit = new Media(new File(currentSong).toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(hit);
					media[0].stop();
					media[0] = mediaPlayer;
					media[0].play();
					frame.repaint();
				}
				break;
			}
			}
		}
		
	}
	
	private class ListenForDuration implements MouseListener{
		
		
		
		/*@Override
		public void stateChanged(ChangeEvent event) {
			// TODO Auto-generated method stub
			JSlider cb = (JSlider) event.getSource();
			double sliderValue = cb.getValue();
			double something  = (sliderValue/100);
			double proper_time_interval = media[0].getStopTime().toSeconds() * (something);
			media[0].seek(media[0].getStopTime().seconds(proper_time_interval));
		}*/

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			/*
			 * do something if user moves cursor away from panel
			 * 	for example release it with no 
			 */
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("RELEASED: "+e.getX()+" - "+e.getY());
				//JSlider cb = (JSlider) event.getSource();
				double currentTimeStamp = media[0].getBalance();
				System.out.println("TIME: "+currentTimeStamp);
				double sliderValue = e.getX();
				double something  = (sliderValue%100);
				System.out.println("something: "+something);
				//double proper_time_interval = media[0].getStopTime().toSeconds() * (something);
				media[0].seek(media[0].getStopTime().seconds(something));
		}
		
	}
	private class ListenForVolume implements MouseListener{

		

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Clicked: "+e.getX()+" - "+e.getY());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Entered: "+e.getX()+" - "+e.getY());
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Exited: "+e.getX()+" - "+e.getY());
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("PRESSED: "+e.getX()+" - "+e.getY());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			//Method is called when slider changes state.
			//When this occurs the slider value is assigned to 
			//SliderValue which is then divided by 100 
			//so as it adheres to the correct range values.
			JSlider cb = (JSlider)e.getSource();
			double sliderValue = e.getX();
			System.out.println("Volume is: "+sliderValue);
			setVolume  = (sliderValue%100)/100;
			System.out.println(setVolume);
			media[0].setVolume(setVolume);
		}
		
		
	}
}
