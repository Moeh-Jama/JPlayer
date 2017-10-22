import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class directoryReading {

	/*public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filePath = "C:\\Users\\MDJam\\Pictures";
		final File folder = new File(filePath);
		listFilesForFolder(folder);
		ImagePanel j = new ImagePanel();
		j.setImage("C:\\Users\\MDJam\\Pictures\\Capture.png");
		
		
		JFrame jf = new JFrame();
		jf.setTitle("Tut");
		jf.setSize(600,400);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(j);
		
		
		//j.paintComponent(jf);
	}*/
	
	public static void listFilesForFolder(final File folder) {
		for(final File fileEntry: folder.listFiles())
		{
			if (fileEntry.isDirectory())
			{
				listFilesForFolder(fileEntry);
			}
			else {
				//Prints out file name.
				if(fileEntry.getName().endsWith(".png")) {
					System.out.println(fileEntry.getName());
				}
				
			}
		}
	}
}
