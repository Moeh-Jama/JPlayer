import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class attemptMP3 extends JFrame {

	static String Path = ".\\MusicFolder\\";
	static String currentSong = "bib.mp3";
	static ArrayList<String> accessibleSongs = new ArrayList<String>();
	final JFXPanel fxPanel = new JFXPanel();
	Media hit = new Media(new File(Path+currentSong).toURI().toString());
	MediaPlayer mediaPlayer = new MediaPlayer(hit);
	public static void main(String[] args) throws IOException  {
		// TODO Auto-generated method stub
		
		String filePath = ".\\MusicFolder\\";
		final File folder = new File(filePath);
		listFilesForFolder(folder);
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
					System.out.println(fileEntry.getName());
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
		JButton btn1 = new JButton("Play");
		ListenForButton lBtn1 = new ListenForButton();
		btn1.addActionListener(lBtn1);
		thePanel.add(btn1);
		JButton btn2 = new JButton("Pause");
		ListenForButton2 lBtn2 = new ListenForButton2();
		btn2.addActionListener(lBtn2);
		thePanel.add(btn2);
		
		JLabel j1 = new JLabel(accessibleSongs.get(0));
		j1.add(thePanel);
		JTextField txA = new JTextField(accessibleSongs.get(0), 500);
		thePanel.add(txA);
		
		this.add(thePanel);
		this.setVisible(true);
	}
	
	
	
	
	private class ListenForButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			mediaPlayer.play();
		}
		
	}
	
	private class ListenForButton2 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			mediaPlayer.pause();
		}
		
	}
	
}
