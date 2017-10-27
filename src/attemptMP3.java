import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
public class attemptMP3 extends JFrame {

	
	//JFileChooser chooser = new JFileChooser();
	
	
	static String Path = ".\\MusicFolder\\";
	static String currentSong = "";
	static double setVolume = 0.1;
	static ArrayList<String> accessibleSongs = new ArrayList<String>();
	final JFXPanel fxPanel = new JFXPanel();
	//static MediaPlayer mediaPlayer;
	static MediaPlayer[] media = new MediaPlayer[1];
	static ArrayList<MediaPlayer> mediaLib = new ArrayList<MediaPlayer>();
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
		new attemptMP3();
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
	
	
	public attemptMP3() {
		this.setTitle("JAMA MP3");
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel thePanel = new JPanel();
		thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));
		
		JButton btn1 = new JButton("Play");
		btn1.setAlignmentX(Component.CENTER_ALIGNMENT);
		ListenForButton lBtn1 = new ListenForButton();
		btn1.addActionListener(lBtn1);
		thePanel.add(btn1);
		JButton btn2 = new JButton("Pause");
		btn2.setAlignmentX(Component.CENTER_ALIGNMENT);
		ListenForButton2 lBtn2 = new ListenForButton2();
		btn2.addActionListener(lBtn2);
		thePanel.add(btn2);
		
		
		//ComboBox
		String s = "";
		for(String i: accessibleSongs)
		{
			s+=i+"%";
		}
		System.out.println("-----------------------");
		String[] musicList = s.split("%");
		
		//System.out.println(s.split(" "));
		for(int i =0; i< musicList.length; i++)
		{
			System.out.println(musicList[i]);
		}
		//TODO add function that updates musicList and SongList to current
		//Library List.
		JComboBox songList = new JComboBox(musicList);
		songList.setSize(200, 300);
		songList.setSelectedIndex(4);
		ListenForCombo lsongList = new ListenForCombo();
		songList.addActionListener(lsongList);
		thePanel.add(songList);
		
		JLabel vol = new JLabel("VOLUME");
		thePanel.add(vol);
		
		int FPS_MIN = 0;
		int FPS_MAX = 10;
		int FPS_INIT = 5;    //initial frames per second

		JSlider volume = new JSlider(JSlider.HORIZONTAL,FPS_MIN, FPS_MAX, FPS_INIT);
		ListenForVolume lVolume = new ListenForVolume();
		volume.addChangeListener( lVolume);
		
		/*volume.setMajorTickSpacing(10);
		volume.setMinorTickSpacing(1);
		volume.setPaintTicks(true);
		volume.setPaintLabels(true);*/
		
		thePanel.add(volume);
		this.add(thePanel);
		this.setVisible(true);
	}
	
	public static void playSong()
	{
		Media hit = new Media(new File(Path+currentSong).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		media[0] = mediaPlayer;
		media[0].play();
		media[0].setVolume(setVolume);
	}
	public static void changeSong() {
		Media hit = new Media(new File(Path+currentSong).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		if(media[0]!= null)
		{
			media[0].stop();
		}
		media[0] = mediaPlayer;
		media[0].play();
	}
	public static void resume() throws InterruptedException
	{
		media[0].setVolume(0.1);
		media[0].play();
		TimeUnit.SECONDS.sleep(2);
		media[0].setVolume(1);
		System.out.println(media[0].getVolume());
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
			// TODO Auto-generated method stub
			media[0].pause();
		}
		
	}
	
	private class ListenForCombo implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JComboBox cb = (JComboBox)e.getSource();
	        String songName = (String)cb.getSelectedItem();
	        currentSong = songName;
			System.out.println("OUR E IS: "+songName);
			changeSong();
		}
		
	}
	
	private class ListenForVolume implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			JSlider cb = (JSlider)e.getSource();
			int s = cb.getValue();
			System.out.println("Volume is: "+s);
			double t = s;
			double time  = (t/10);
			System.out.println(time);
			setVolume = time;
			setVolume = s;
			media[0].setVolume(time);
		}
		
	}
}
