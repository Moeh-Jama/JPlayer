import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class JPlayerMain extends JFrame {

	
	//JFileChooser chooser = new JFileChooser();
	
	//Global variables used to allow the application  to be dynamic
	static String Path = ".\\MusicFolder\\";
	static String currentSong = "";
	static double setVolume = 0.1;
	static ArrayList<String> accessibleSongs = new ArrayList<String>();
	final JFXPanel fxPanel = new JFXPanel();
	//static MediaPlayer mediaPlayer;
	static MediaPlayer[] media = new MediaPlayer[1];
	static boolean CHANGE  = false;

	public static void main(String[] args) throws IOException  {
		// TODO Auto-generated method stub
		
		String filePath = ".\\MusicFolder\\";
		final File folder = new File(filePath);
		listFilesForFolder(folder);
		for(String i: accessibleSongs)
		{
			System.out.println(i);
		}
		currentSong = accessibleSongs.get(0);
		new JPlayerMain();
	}
	
	public static void listFilesForFolder(final File folder) {
		String songs = " ";
		for(final File fileEntry: folder.listFiles())
		{
			if (fileEntry.isDirectory())
			{
				listFilesForFolder(fileEntry);
			}
			else {
				//Prints out file name.
				if(fileEntry.getName().endsWith(".mp3")) {
					//System.out.println(fileEntry.getName());
					accessibleSongs.add(fileEntry.getName());
				}
				
			}
		}
	}
	
	public static void Favourites()
	{
		
	}
	public static void addFavourite() {
		
	}
	public static void removeFavourite() {
		
	}
	public static String[] makeMusicList() {
		String s = "";
		for(String i: accessibleSongs)
		{
			s+=i+"%";
		}
		String[] musicList = s.split("%");
		
		//System.out.println(s.split(" "));
		for(int i =0; i< musicList.length; i++)
		{
			System.out.println(musicList[i]);
		}
		
		return musicList;
	}
	
	public JPlayerMain() throws IOException {
		this.setTitle("JAMA MP3");
		this.setSize(1200, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel thePanel = new JPanel();
		thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));
		
		
		
		JPanel wrapper = new JPanel();
		//Play All Songs or favourites
		String[] ListType = {"All Songs", "Favourite Songs"};
		JComboBox<?> userPref = new JComboBox<Object>(ListType);
		userPref.setSelectedIndex(0);
		ListenForCombo lsongList = new ListenForCombo();
		userPref.addActionListener(lsongList);;
		wrapper.add(userPref);
		
		
		//TODO add function that updates musicList and SongList to current
		//Library List.
		JComboBox<?> songList = new JComboBox<Object>(makeMusicList());
		songList.setSize(30, 50);
		//songList.setSize(width, height);
		songList.setSelectedIndex(4);
		//ListenForCombo lsongList = new ListenForCombo();
		songList.addActionListener(lsongList);
		songList.setAlignmentX(Component.CENTER_ALIGNMENT);
		wrapper.add(songList);
		thePanel.add(wrapper);
		
		
		
		
		//Image Area
		
		/*JPanel imagePanel = new JPanel();
		imagePanel.setSize(600, 400);
		JPanel newPane = new JPanel();
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		ImagePanel j = new ImagePanel();
		j.setImage(".\\ImageFolder\\JPlayer.png");
		//imagePanel.add(j);
		j.setAlignmentX(Component.LEFT_ALIGNMENT);
		thePanel.add(j);*/
		
		
		
		
		//Controls.
		
		JPanel controls = new JPanel();
		
		JButton skip_backward = new JButton("|<");
		skip_backward.setAlignmentX(Component.CENTER_ALIGNMENT);
		ListenForSkip_B lskip_B = new ListenForSkip_B();
		skip_backward.addActionListener(lskip_B);
		controls.add(skip_backward);
		
		JButton btn1 = new JButton("Play");
		btn1.setAlignmentX(Component.CENTER_ALIGNMENT);
		ListenForButton lBtn1 = new ListenForButton();
		btn1.addActionListener(lBtn1);
		controls.add(btn1);
		
		JButton btn2 = new JButton("Pause");
		btn2.setAlignmentX(Component.CENTER_ALIGNMENT);
		ListenForButton2 lBtn2 = new ListenForButton2();
		btn2.addActionListener(lBtn2);
		controls.add(btn2);
		
		
		JButton skip_forward = new JButton(">|");
		skip_forward.setAlignmentX(Component.CENTER_ALIGNMENT);
		ListenForSkip_F lskip_f = new ListenForSkip_F();
		skip_forward.addActionListener(lskip_f);
		controls.add(skip_forward);
		
		thePanel.add(controls);
		
		// DURATION
		JLabel duration = new JLabel("Duration");
		thePanel.add(duration);
		int start_index = 0;
		int end_index = 100;
		int current_index = 0;
		JSlider song_duration = new JSlider(JSlider.HORIZONTAL, start_index, end_index, current_index);
		ListenForDuration lForDuration = new ListenForDuration();
		song_duration.addChangeListener(lForDuration);
		thePanel.add(song_duration);
		
		JLabel vol = new JLabel("VOLUME");
		thePanel.add(vol);
		
		int FPS_MIN = 0;
		int FPS_MAX = 100;
		int FPS_INIT = 5;    //initial frames per second

		JSlider volume = new JSlider(JSlider.HORIZONTAL,FPS_MIN, FPS_MAX, FPS_INIT);
		ListenForVolume lVolume = new ListenForVolume();
		volume.addChangeListener( lVolume);
		
		volume.setMajorTickSpacing(10);
		volume.setMinorTickSpacing(1);
		volume.setPaintTicks(true);
		volume.setPaintLabels(true);
		
		thePanel.add(volume);
		this.add(thePanel);
		this.setVisible(true);
	}
	
	public static void playSong()
	{
		Media hit = new Media(new File(Path+currentSong).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		media[0] = mediaPlayer;
		media[0].setVolume(setVolume);
		media[0].play();
		System.out.println("A Volume is: "+media[0].getVolume());
	}
	public static void changeSong() {
		Media hit = new Media(new File(Path+currentSong).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		if(media[0]!= null)
		{
			media[0].stop();
		}
		System.out.println(setVolume);
		media[0] = mediaPlayer;
		media[0].play();
		media[0].setVolume(setVolume);
		System.out.println("B Volume is: "+media[0].getVolume());
	}
	public static void resume() throws InterruptedException
	{
		media[0].play();
		System.out.println("C Volume is: "+media[0].getVolume());
	}
	private class ListenForButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(media[0] == null)
			{
				System.out.println("TRUE");
				playSong();
			}
			else {
				System.out.println("Resume");
				try {
					resume();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class ListenForButton2 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Pause...
			media[0].pause();
		}
		
	}
	
	private class ListenForCombo implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JComboBox<?> cb = (JComboBox<?>)e.getSource();
	        String songName = (String)cb.getSelectedItem();
	        System.out.println("JJJJ |"+songName+"|");
	        
	        
	        if(songName.equals("All Songs") || songName.equals("Favourite Songs"))
	        {
	        	System.out.println("ITS NOT A SONG");
	        }
	        else {
	        	System.out.println("IT IS A SONG");
	        	if(currentSong.equals(songName))
		        {
		        	//If the song is the same as the one currently playing, ignore.
		        	System.out.println("Ignore");
		        }
		        else {
		        	currentSong = songName;
					System.out.println("OUR E IS: "+songName);
					changeSong();
		        }
	        }
	        
	        
	        
		}
		
	}
	
	
	
	private class ListenForComboTwo implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JComboBox<?> cb = (JComboBox<?>)e.getSource();
	        String songName = (String)cb.getSelectedItem();
	        System.out.println(songName);
	        if(songName.equals("Favourite Songs"))
	        {
	        	//TODO print Favourites
	        }
	        else {
	        	
	        }
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
	
	private class ListenForDuration implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			//Changes duration of song depending on the slider location.
			JSlider cb = (JSlider)e.getSource();
			double sliderValue = cb.getValue();
			double something  = (sliderValue/100);
			double proper_time_interval = media[0].getStopTime().toSeconds() * (something);
			media[0].seek(media[0].getStopTime().seconds(proper_time_interval));
		}
		
	}
	
	
	
	private class ListenForSkip_F implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("E IS ACTIVE: ");
			int i=0;
			while(i < accessibleSongs.size())
			{
				System.out.println(currentSong +" " + accessibleSongs.get(i));
				if(currentSong.equals(accessibleSongs.get(i)) && i!=accessibleSongs.size()-1)
				{
					System.out.println("NEW SONG: "+accessibleSongs.get(i+1));
					currentSong = accessibleSongs.get(i+1);
					changeSong();
					break;
				}
				else if(currentSong.equals(accessibleSongs.get(i)) && i==accessibleSongs.size()-1)
				{
					currentSong = accessibleSongs.get(accessibleSongs.size()-1);
					changeSong();
					break;
				}
				i++;
			}
			System.out.println("Done");
		}
		
	}
	private class ListenForSkip_B implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int i=0;
			while(i < accessibleSongs.size())
			{
				System.out.println(currentSong +" " + accessibleSongs.get(i));
				if(currentSong.equals(accessibleSongs.get(i)) && i!=0)
				{
					System.out.println("NEW SONG: "+accessibleSongs.get(i-1));
					currentSong = accessibleSongs.get(i+1);
					changeSong();
					break;
				}
				else if(currentSong.equals(accessibleSongs.get(i)) && i==0) {
					System.out.println("NEW SONG: "+accessibleSongs.get(accessibleSongs.size()-1));
					currentSong = accessibleSongs.get(accessibleSongs.size()-1);
					changeSong();
					break;
				}
				i++;
			}
		}
		
	}
	
	
	
}
