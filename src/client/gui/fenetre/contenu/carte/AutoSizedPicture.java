package client.gui.fenetre.contenu.carte;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 * Displays a centered image in the available area. If the picture is too big
 * (width or height), a zoom will be applied in order to show the full image.
 * This class works also if the picture is in a jar file.
 * @author Michel Deriaz 
 */
public class AutoSizedPicture extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
private Image img;      


  /**
   * Loads the specified image, which must be a JPG, a GIF, or a PNG.
   * @param file the file to load
   */  
  public AutoSizedPicture(String file) {
	img = new ImageIcon(AutoSizedPicture.class.getClassLoader().getResource(file)).getImage();
    repaint(); 
  }
  

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (img == null) return;
    int w = img.getWidth(this);  
    int h = img.getHeight(this);
    boolean zoom = (w > getWidth() || h > getHeight());
    if (zoom) g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    else g.drawImage(img, (getWidth()-w)/2, (getHeight()-h)/2, this);
  }
}