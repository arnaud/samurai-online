package client.gui.fenetre.contenu.carte;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import client.test.ImageUtil;

/**
 * Affiche une image ayant les fonctionnalités suivantes :
 * - fonction de "zoom"
 * - fonction de "poursuite" c'est à dire que selon la position du personnage,
 *   l'image va afficher uniquement la zone de proximité.
 * @author kouyio
 */
public class FollowPicture extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
  private static Image img;      
  private Point image, perso;
  private final int LARGEUR_CARTE = 1984; // = 128 * 16 - 128 / 2;
  private final int ZOOM_MAX = 9;
  private final int ZOOM_MIN = 2;
  private int zoom;
  private BufferedImage bimage;

  /**
   * Loads the specified image, which must be a JPG, a GIF, or a PNG.
   * @param file the file to load
   */  
  public FollowPicture(String file) {
	zoom = 5;
	img = new ImageIcon(FollowPicture.class.getClassLoader().getResource(file)).getImage();
	image = new Point();
	perso = new Point();
	image.setLocation(img.getWidth(this), img.getHeight(this));
	
    bimage = ImageUtil.toBufferedImage(img);
    bimage = ImageUtil.appliquerTransparence(bimage, 120);
    //repaint(); 
  }
  
/**
 * x : -2500 (ouest) à +2500 (est)
 * y : -2500 (nord) à +2500 (sud)
 */
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (img == null) return;
    
    Graphics2D g2d = (Graphics2D)g;
    
	// Positionnement
    int realPosXperso = (int) (image.getX()*(perso.getX()+LARGEUR_CARTE/2)/LARGEUR_CARTE);
    int realPosYperso = (int) (image.getY()*(perso.getY()+LARGEUR_CARTE/2)/LARGEUR_CARTE);

    double sourceWidth = image.getX()/zoom;
    double sourceHeight = image.getY()/zoom;
    
    //  img - the specified image to be drawn. This method does nothing if img is null.
    //
    //  dx1 - the x coordinate of the first corner of the destination rectangle.
    int dx1 = 0;
    //  dy1 - the y coordinate of the first corner of the destination rectangle.
    int dy1 = 0;
    //  dx2 - the x coordinate of the second corner of the destination rectangle.
    int dx2 = getWidth();
    //  dy2 - the y coordinate of the second corner of the destination rectangle.
    int dy2 = getHeight();
    //  sx1 - the x coordinate of the first corner of the source rectangle.
    int sx1 = (int) (realPosXperso - sourceWidth/2);
    //  sy1 - the y coordinate of the first corner of the source rectangle.
    int sy1 = (int) (realPosYperso - sourceHeight/2);
    //  sx2 - the x coordinate of the second corner of the source rectangle.
    int sx2 = (int) (sx1 + sourceWidth);
    //  sy2 - the y coordinate of the second corner of the source rectangle.
    int sy2 = (int) (sy1 + sourceHeight);
    //  observer - object to be notified as more of the image is scaled and converted.
    ImageObserver observer = this;
    
	// Affichage de l'image
    g2d.drawImage(bimage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
    
    
    // Curseur du personnage
    int w = getWidth()/2;
    int h = getHeight()/2;
    g.setColor(Color.WHITE);
    g.drawLine(w,h-2,w,h+2);
    g.drawLine(w-2,h,w+2,h);
    
    //g.drawOval(getWidth()/2, getHeight()/2, 5, 5);
    //g.drawOval(getWidth()/2, getHeight()/2, 1, 1);
    //updateUI();
    
    g2d.dispose();
  }
  
  public boolean zoomIn(){
	  if(zoom < ZOOM_MAX)
		  zoom++;
	  return (zoom==ZOOM_MAX);
  }
  
  public boolean zoomOut(){
	  if(zoom > ZOOM_MIN)
		  zoom--;
	  return (zoom==ZOOM_MIN);
  }
  
  public int getZoomMax(){
	  return ZOOM_MAX;
  }
  
  public int getZoomMin(){
	  return ZOOM_MIN;
  }
  
  public int getNivZoom(){
	  return zoom;
  }
  
  /**
   * Met à jour la position du personnage et du visuel de la mini-carte.
   * Bien noter qu'il y a interversion des coordonnées.
   * @param x
   * @param y
   */
  public void updatePosition(float x, float y){
	  perso.setLocation(x, y);
	  convertPositionPersoToRealPosition();
	  repaint();
  }
  
  private void convertPositionPersoToRealPosition(){
	  double x = image.getX() * perso.getX() / LARGEUR_CARTE;
	  double y = image.getY() * perso.getY() / LARGEUR_CARTE;
	  perso.setLocation(x ,y);
  }
}