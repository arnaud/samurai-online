package client.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JComponent;

import client.gui.fenetre.Fenetre;

import com.jme.system.DisplaySystem;

/**
 * Cette classe s'occupe de gérer l'ensemble des fenêtres composant le GUI (interface).
 * Elle permet notamment de les positionner soit à des coordonnées passées en arguments,
 * soit de définir un accrochage à un des bords de l'écran.
 * @author kouyio
 *
 */
public class GuiDesktop {
	
	private DisplaySystem display;
	
	private Vector nodes;
	
	public GuiDesktop(DisplaySystem display){
		this.display = display;
		nodes = new Vector();
	}
	
	/*private void addFenetre(Fenetre fenetre, FenetrePosition position){
		FenetreDesktop fd = new FenetreDesktop(fenetre, position);
		nodes.add(fd);
	}*/
	
	public void addFenetre(Fenetre fenetre, int posx, int posy){
		FenetrePosition position = new FenetrePosition(display, posx, posy);
		FenetreDesktop fd = new FenetreDesktop(fenetre, position);
		nodes.add(fd);
	}
	
	public void addFenetre(Fenetre fenetre){
		FenetrePosition position = new FenetrePosition(display, fenetre.posx, fenetre.posy);
		FenetreDesktop fd = new FenetreDesktop(fenetre, position);
		nodes.add(fd);
	}
	
	public void removeFenetre(Fenetre fenetre){
		for(int i=0; i<nodes.size(); i++){
			if( ((FenetreDesktop)nodes.elementAt(i)).getFenetre().equals(fenetre) ){
				nodes.remove(i);
				break;
			}
		}
	}
	
	private FenetreDesktop getFenetreDesktop(Fenetre fenetre){
		for(int i=0; i<nodes.size(); i++){
			if( ((FenetreDesktop)nodes.elementAt(i)).getFenetre().equals(fenetre) ){
				return (FenetreDesktop)nodes.elementAt(i);
			}
		}
		return null;
	}
	
	/**
	 * Positionnement de la fenêtre
	 * @param fenetre
	 * @param pos
	 */
	public void setFenetrePosition(Fenetre fenetre, int pos){
		FenetreDesktop fd = getFenetreDesktop(fenetre);
		if(fd!=null){
			fd.getPosition().setPosition(pos);
			//FenetrePosition fp = fd.getPosition();
			updateFenetre(fenetre);
		}
		else
			System.err.println("Fenetre "+fenetre.getName()+" inconnue dans le GuiDesktop !");
	}
	
	/**
	 * Positionnement de la fenêtre
	 * @param fenetre
	 * @param posx
	 * @param posy
	 */
	public void setFenetrePosition(Fenetre fenetre, int posx, int posy){
		FenetreDesktop fd = getFenetreDesktop(fenetre);
		if(fd!=null){
			fd.getPosition().setPosition(posx, posy);
			fd.getFenetre().setPosition(posx, posy, 1);
			fenetre.setPosition(posx, posy, 1);
		}else
			System.err.println("Fenetre "+fenetre.getName()+" inconnue dans le GuiDesktop !");
	}
	
	/**
	 * Positionnement central de la fenêtre
	 * @param fenetre
	 * @param posx
	 * @param posy
	 */
	public void setFenetrePositionCentree(Fenetre fenetre){
		FenetreDesktop fd = getFenetreDesktop(fenetre);
		if(fd!=null){
			int x = (display.getWidth()-fenetre.width)/2;
			int y = (display.getHeight()-fenetre.height)/2;
			fenetre.setPosition(x, y, 1);
		}else
			System.err.println("Fenetre "+fenetre.getName()+" inconnue dans le GuiDesktop !");
	}
	
	/**
	 * Mise à jour de la fenêtre 'fenetre'
	 * @param fenetre
	 */
	private void updateFenetre(Fenetre fenetre){
		FenetreDesktop fd = getFenetreDesktop(fenetre);
		if(fd!=null){
			FenetrePosition position = fd.getPosition();
			fenetre.setPosition(position.posx, position.posy, fenetre.posz);
		}
		else
			System.err.println("Fenetre "+fenetre.getName()+" inconnue dans le GuiDesktop !");
	}
	
	/**
	 * Mise à jour de la fenêtre numéro 'num'
	 * @param num
	 */
	private void updateFenetre(int num){
		FenetreDesktop fd = (FenetreDesktop) nodes.elementAt(num);
		Fenetre fenetre = fd.getFenetre();
		FenetrePosition position = fd.getPosition();
		fenetre.setPosition(position.posx, position.posy, fenetre.posz);
	}
	
	/**
	 * Mise à jour des fenêtres
	 *
	 */
	private void updateFenetres(){
		for(int i=0; i<nodes.size(); i++)
			updateFenetre(i);
	}
	
	/*private int getNumberSideFixation(int side){
		int nb = 0;
		for(int i=0; i<nodes.size(); i++)
			if( ((FenetreDesktop)nodes.elementAt(i)).getPosition().isFixedSide(side) )
				nb++;
		return nb;
	}*/
	
	/**
	 * Récupère le numéro correspondant au côté de fixation
	 */
	private int getNumeroSideFixation(FenetrePosition position){
		int side = position.getFixedSide();
		int nb=0;
		for(int i=0; i<nodes.size(); i++)
			if( ((FenetreDesktop)nodes.elementAt(i)).getPosition().isFixedSide(side) ){
				if( ((FenetreDesktop)nodes.elementAt(i)).getPosition().equals(position) )
					break;
				nb++;
			}
		return nb;
	}
	
	/**
	 * Récupère la position de la Fenêtre dans le côté de fixation
	 * @param position
	 * @return
	 */
	private int getLateralPositionValue(FenetrePosition position){
		int value = 0;
		int nb = 0;
		int posFenetre = getNumeroSideFixation(position);
		//int nbFenetres = getNumberSideFixation(position.getFixedSide());
		for(int i=0; i<nodes.size() && nb<posFenetre; i++){
			FenetreDesktop fd = (FenetreDesktop) nodes.elementAt(i);
			if( fd.getPosition().isFixedSide(position.getFixedSide()) ){
				value += (position.getFixedSide()==2 || position.getFixedSide()==4) ? fd.getFenetre().width : fd.getFenetre().height;
				nb++;
			}
		}
		return value;
	}
	
	/**
	 * Récupère une fenêtre par son nom
	 * @param nom
	 * @return
	 */
	public Fenetre getFenetre(String nom){
		for(int i=0; i<nodes.size(); i++){
			Fenetre f = ((FenetreDesktop)nodes.elementAt(i)).getFenetre();
			if( f.getName().equals(nom) ){
				return f;
			}
		}
		return null;
	}
	
	public String toString(){
		String res = "guiDesktop :\n";
		for(int i=0; i<nodes.size(); i++)
			res += ((FenetreDesktop)nodes.elementAt(i)).toString();
		return res;
	}
	
	/**
	 * Informe une fenêtre de son positionnement.
	 * @author kouyio
	 *
	 */
	public class FenetreDesktop {
		
		private Fenetre fenetre;
		private FenetrePosition position;
		
		public FenetreDesktop(Fenetre fenetre, FenetrePosition position){
			this.fenetre = fenetre;
			this.position = position;
			addMouseHighlighter();
			try{
				fenetre.getContenu().setFenetreDesktop(this);
			}catch(Exception e){}
		}
		
		public Fenetre getFenetre(){
			return fenetre;
		}
		
		public FenetrePosition getPosition(){
			return position;
		}
		
		public void setFenetre(Fenetre fenetre){
			this.fenetre = fenetre;
		}
		
		public void setPosition(FenetrePosition position){
			this.position = position;
		}
		
		public String toString(){
			return " - fenetreDesktop :\n   - "+fenetre.toString()+"\n   - "+position.toString()+"\n";
		}
		
		/**
		 * Met en surbrillance une fenêtre lorsque le curseur de la souris passe au-dessus.
		 * Utilise une variation d'alpha pour la transparence de la couleur de background.
		 *
		 */
		private void addMouseHighlighter(){

			final JComponent panel = fenetre.getContenu();
			
			//TODO: Ne fonctionne pas -> à revoir
			
			if(panel!=null){
				final int deltaAlpha = 20;
				
				fenetre.getContenu().addMouseListener(new MouseAdapter(){
					public void mouseEntered(MouseEvent e) {
						Color current = panel.getBackground();
							int r = current.getRed();
							int g = current.getGreen();
							int b = current.getBlue();
							int t = current.getAlpha();
							try{
							Color newcolor = new Color(r,g,b,t+deltaAlpha);
							panel.setBackground(newcolor);
						}catch(IllegalArgumentException ex){
							System.err.println("Argument non valide : transparence = "+t);
						}
					}
	
					public void mouseExited(MouseEvent e) {
						Color current = panel.getBackground();
						int r = current.getRed();
						int g = current.getGreen();
						int b = current.getBlue();
						int t = current.getAlpha();
						try{
							Color newcolor = new Color(r,g,b,t-deltaAlpha);
							panel.setBackground(newcolor);
						}catch(IllegalArgumentException ex){
							System.err.println("Argument non valide : transparence = "+t);
						}
					}
				});
			}
		}
	}
	
	/**
	 * Positionne une fenêtre par rapport à l'écran.
	 * Permet de 'coller' une fenêtre à un bord.
	 * Une fenêtre peut être collée à deux bords adjacents.
	 * @author kouyio
	 *
	 */
	public class FenetrePosition {

		public final static int FIX_EAST = 1;
		public final static int FIX_SOUTH = 2;
		public final static int FIX_WEST = 3;
		public final static int FIX_NORTH = 4;

		private boolean isFixedEast;
		private boolean isFixedSouth;
		private boolean isFixedWest;
		private boolean isFixedNorth;
		
		private int posx;
		private int posy;
		private int width;
		private int height;
		private int screenWidth;
		private int screenHeight;
		
		public FenetrePosition(DisplaySystem display, int width, int height, int posx, int posy){
			isFixedEast = false;
			isFixedSouth = false;
			isFixedWest = false;
			isFixedNorth = false;
			this.posx = posx;
			this.posy = posy;
			this.width = width;
			this.height = height;
			screenWidth = display.getWidth();
			screenHeight = display.getHeight();
		}
		
		public FenetrePosition(DisplaySystem display, int width, int height, int position){
			this(display, width, height);
			setPosition(position);
		}
		
		public FenetrePosition(DisplaySystem display, int width, int height){
			this(display, width, height, 0, 0);
		}
		
		public void setPosition(int position){
			switch(position){
				case FIX_EAST: isFixedEast=true; break;
				case FIX_SOUTH: isFixedSouth=true; break;
				case FIX_WEST: isFixedWest=true; break;
				case FIX_NORTH: isFixedNorth=true; break;
			}
			update();
		}
		
		public void setPosition(int posx, int posy){
			removeAllFixations();
			this.posx = posx;
			this.posy = posy;
		}
		
		public boolean isFixedEast(){
			return isFixedEast;
		}
		
		public boolean isFixedSouth(){
			return isFixedSouth;
		}
		
		public boolean isFixedNorth(){
			return isFixedNorth;
		}
		
		public boolean isFixedWest(){
			return isFixedWest;
		}
		
		public boolean isFixedSide(int side){
			switch(side){
				case FIX_EAST: return isFixedEast;
				case FIX_SOUTH: return isFixedSouth;
				case FIX_WEST: return isFixedWest;
				case FIX_NORTH: return isFixedNorth;
			}
			return false;
		}
		
		public int getFixedSide(){
			if(isFixedSide(FIX_EAST)) return FIX_EAST;
			if(isFixedSide(FIX_SOUTH)) return FIX_SOUTH;
			if(isFixedSide(FIX_WEST)) return FIX_WEST;
			if(isFixedSide(FIX_NORTH)) return FIX_NORTH;
			return 0;
		}
		
		public void setFixedEast(){
			removeWestFixation();
			setEastFixation();
		}
		
		public void setFixedSouth(){
			removeNorthFixation();
			setSouthFixation();
		}
		
		public void setFixedWest(){
			removeEastFixation();
			setWestFixation();
		}
		
		public void setFixedNorth(){
			removeSouthFixation();
			setNorthFixation();
		}
		
		private void removeEastFixation(){
			isFixedEast = false;
		}
		
		private void removeSouthFixation(){
			isFixedSouth = false;
		}
		
		private void removeWestFixation(){
			isFixedWest = false;
		}
		
		private void removeNorthFixation(){
			isFixedNorth = false;
		}
		
		private void removeAllFixations(){
			removeEastFixation();
			removeSouthFixation();
			removeWestFixation();
			removeNorthFixation();
		}
		
		private void setEastFixation(){
			isFixedEast = true;
			posx = screenWidth - 2*width;
			posy = getLateralPositionValue(this);
		}
		
		private void setSouthFixation(){
			isFixedSouth = true;
			posx = getLateralPositionValue(this);
			posy = screenHeight - 2*height;
		}
		
		private void setWestFixation(){
			isFixedWest = true;
			posx = 0;
			posy = getLateralPositionValue(this);
		}
		
		private void setNorthFixation(){
			isFixedNorth = true;
			posx = getLateralPositionValue(this);
			posy = 0;
		}
		
		public void update(){
			if(isFixedEast)
				setEastFixation();
			if(isFixedSouth)
				setSouthFixation();
			if(isFixedWest)
				setWestFixation();
			if(isFixedNorth)
				setNorthFixation();
		}
		
		public String toString(){
			String fixation = "";
			if(isFixedNorth)
				fixation+="nord";
			if(isFixedSouth)
				fixation+="sud";
			if(isFixedEast)
				fixation+="est";
			if(isFixedWest)
				fixation+="ouest";
			return "position[posx="+posx+", posy="+posy+", width="+width+", height="+height+", fixation="+fixation+"]";
		}
		
		public void setHeight(int height){
			this.height = height;
			System.out.println(toString());
		}
	}
}
