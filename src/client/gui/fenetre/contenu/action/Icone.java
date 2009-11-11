package client.gui.fenetre.contenu.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import modele.action.Action;
import client.test.ImageUtil;

public class Icone extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	private Action action;
	private boolean surbrillance = false;
	
	public Icone(){
	}

	public Icone(Action action){
		this();
		this.action = action;
	}
    
	public void paint(Graphics g){
		
        Graphics2D g2d = (Graphics2D)g;
		
		// Texte
		g.setColor(Color.WHITE);
		g.setFont(new Font(null,Font.PLAIN, 9));
		g2d.drawString(getNom(), getWidth()/2-getNom().length()*2.4f, 10);
        
        // Create an oval shape that's as large as the component
        float fx = 2;
        float fy = -45;
        float fw = 56;
        float fh = 105;
        Shape shape = new java.awt.geom.Ellipse2D.Float(fx, fy, fw, fh);
    
        // Set the clipping area
        g2d.setClip(shape);
    
        // Draw an image
		
		//Graphics2D g2d = (Graphics2D)g;
        Image image = (new ImageIcon(Icone.class.getClassLoader().getResource("data/images/"+getNom()+".jpg"))).getImage();
        
        // Image bufferisée
        BufferedImage bimage = ImageUtil.toBufferedImage(image);
        
        // Transparence
        int degre;
        if(surbrillance)
        	degre = 220;
        else
        	degre = 150;
        bimage = ImageUtil.appliquerTransparence(bimage, degre);
		
		// Filtrage
		bimage = ImageUtil.filtrer(bimage, ImageUtil.FILTRAGE_EMBOSS);
		
		// Affichage
		g2d.drawImage(bimage, null, 6, 13);
		
        g2d.dispose();
        g.dispose();
	}
	
	public String getNom(){
		if(action==null)
			return "?";
		else
			return action.getNom();
	}
	
	public void setAction(Action action){
		this.action = action;
	}
	
	public void removeAction(){
		action = null;
	}

	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {
		surbrillance = true;
	}

	public void mouseExited(MouseEvent e) {
		surbrillance = false;
	}
	
}
