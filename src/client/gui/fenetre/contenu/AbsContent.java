package client.gui.fenetre.contenu;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import client.gui.GuiDesktop.FenetreDesktop;

public abstract class AbsContent extends JPanel implements ContentInterface, MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	// Dimensions de la fenêtre
	private boolean etatDimensionnement = true;
	private final int DIMENSION_MIN = 16;
	private int dimensionNormale;
	// Position de l'icône de redimensionnement
	private final int POSX1 = 12;
	private final int POSY1 = 5;
	private final int POSX2 = 21;
	private final int POSY2 = 14;
	// fenetreDesktop gère les informations liées
	// à l'emplacementdes fenêtres entre-elles
	FenetreDesktop fenetreDesktop;

	public AbsContent() {
		this("default");
	}

	public AbsContent(String nom) {
		this(nom, new BorderLayout());
	}
	
	public AbsContent(String nom, LayoutManager layout){
		super(layout);
		this.setBorder(BorderFactory.createTitledBorder(null, "     "+nom, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, DEFAULT_FG_COLOR));
		//this.setBackground(DEFAULT_BG_COLOR);
		this.addMouseListener(this);
	}
	
	public void updateDimensions(){
		if(etatDimensionnement){
			this.setSize((int)this.getSize().getWidth(), dimensionNormale);
		}else{
			this.setSize((int)this.getSize().getWidth(), DIMENSION_MIN);
		}
		System.out.println(this.getName()+" "+this.getSize().toString());
		updateUI();
	}
	
	public void setNormalHeight(int height){
		this.dimensionNormale = height;
	}

	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {
		System.out.println(this.getName()+" mouseClicked");
		int clicX = e.getX();
		int clicY = e.getY();
		boolean doubleClick = e.getClickCount()==2;
		boolean isDansZoneDimensionnement = (POSX1 <= clicX && clicX <= POSX2) && (POSY1 <= clicY && clicY <= POSY2);
		boolean isDansZoneBarre = (0 <= clicX && clicX <= this.getWidth()) && (0 <= clicY && clicY <= DIMENSION_MIN);
		boolean changeEtat = (doubleClick && isDansZoneBarre) || isDansZoneDimensionnement;
		if(changeEtat){
			System.out.println("changeEtat");
			etatDimensionnement = !etatDimensionnement;
			updateDimensions();
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	public void paint(Graphics g){
		super.paintComponent(g);
		paintComponent(g);
		paintBorder(g);
		try{
			paintChildren(g);
		}catch(UnsupportedOperationException e){}
		
		// Icône de redimensionnement
		g.setColor(DEFAULT_FG_COLOR);
		g.drawRoundRect(POSX1, POSY1, POSX2-POSX1, POSY2-POSY1, 2, 2);
		if(!etatDimensionnement){
			g.drawLine(POSX1+2, POSY1+2, POSX1+(POSX2-POSX1)/2, POSY2-2);
			g.drawLine(POSX2-2, POSY1+2, POSX1+(POSX2-POSX1)/2, POSY2-2);
		}else{
			g.drawLine(POSX1+2, POSY1+(POSY2-POSY1)/2+1, POSX2-2, POSY1+(POSY2-POSY1)/2+1);
		}
	}
	
	public void setFenetreDesktop(final FenetreDesktop fenetreDesktop){
		this.fenetreDesktop = fenetreDesktop;
		this.addComponentListener(new ComponentAdapter(){

			public void componentResized(ComponentEvent e) {
//				System.out.println("resized !");
				int newHeight = etatDimensionnement ? dimensionNormale : DIMENSION_MIN;
				fenetreDesktop.getPosition().setHeight( newHeight );
				fenetreDesktop.getFenetre().height = newHeight;
				fenetreDesktop.getFenetre().refresh();
			}

			public void componentMoved(ComponentEvent e) {
				System.out.println("moved !");
			}
			
		});
	}
	
	public abstract void refresh();
	
}
