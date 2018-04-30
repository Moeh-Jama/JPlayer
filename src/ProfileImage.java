import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ProfileImage extends JPanel{
	

	static Image bg;// = new ImageIcon(res + "src\\image\\CluedBoard.jpg").getImage();

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, getWidth() - 1, getHeight() - 1, this);
	}
	
	
	public ProfileImage(Image newImage) {
		bg = newImage;
	}
	
	
	public static void setImage(String Title) {
		BufferedImage image;
		File path = new File("");
		String res = path.getAbsolutePath() + "\\Images";
		
		bg = new ImageIcon(res+Title).getImage();
	}
	
	public static Image getImage(String title) {
		BufferedImage image;
		File path = new File("");
		String res = path.getAbsolutePath() + "\\Images\\";
		System.out.println(res+title);
		Image ret =new ImageIcon(res+title).getImage();
		return ret;
	}
	
}
