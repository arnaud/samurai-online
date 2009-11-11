package client.gui.fenetre.contenu.inventaire;
/*
 * Picture.java is used by the 1.4
 * TrackFocusDemo.java and DragPictureDemo.java examples.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.accessibility.Accessible;
import javax.swing.JComponent;

import client.test.ImageUtil;


class ImageObjet extends JComponent implements MouseListener, FocusListener, Accessible {
	
	private static final long serialVersionUID = 1L;
	
	Image image;
	private BufferedImage bimage;

    public ImageObjet(Image image) {

        this.image = image;
        setFocusable(true);
        addMouseListener(this);
        addFocusListener(this);
    }

    public void mouseClicked(MouseEvent e) {
        //Since the user clicked on us, let's get focus!
        requestFocusInWindow();
    }

    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }

    public void focusGained(FocusEvent e) {
        //Draw the component with a red border
        //indicating that it has focus.
        this.repaint();
    }

    public void focusLost(FocusEvent e) {
        //Draw the component with a black border
        //indicating that it doesn't have focus.
        this.repaint();
    }

    protected void paintComponent(Graphics graphics) {
    	if(bimage==null && image!=null){
    	    bimage = ImageUtil.toBufferedImage(image);
    	    bimage = ImageUtil.appliquerTransparence(bimage, 50);
    	}
    	Graphics2D g2d = (Graphics2D)graphics;

    	g2d.setColor(Color.WHITE);
        if(bimage!=null){
        	g2d.drawImage(bimage, 0, 0, this);
        }

        //Add a border, red if picture currently has focus
        if (isFocusOwner()) {
        	g2d.setColor(Color.RED);
            if(bimage!=null)
            	g2d.drawRect(0, 0, bimage.getWidth(this), bimage.getHeight(this));
        }
        g2d.dispose();
    }
}
