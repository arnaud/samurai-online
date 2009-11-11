package client.gui.fenetre.contenu.general;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

public class Bouton extends JButton {
	
	private static final long serialVersionUID = 1L;

	public Bouton() {
		this("");
	}
	
	public Bouton(String nom) {
		super(nom);
		setUI(new BasicButtonUI());
	}
	
	public void paint(Graphics g) {
		this.paintComponent(g);
		g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
	}

}
