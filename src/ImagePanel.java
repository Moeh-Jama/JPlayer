import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	
    private static BufferedImage image;
    public ImagePanel() {
     
    }
    public static void setImage(String newImage) throws IOException
    {
    	try {
    		image = ImageIO.read(new File(newImage));
    	}
    	catch(IOException ex){
    		
    	}
    	
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
    }

}