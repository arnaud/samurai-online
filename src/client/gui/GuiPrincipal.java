package client.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.UIManager;

import modele.action.Action;
import modele.action.Competence;
import client.gui.GuiDesktop.FenetrePosition;
import client.gui.fenetre.Fenetre;
import client.gui.fenetre.NodeFenetre;
import client.gui.fenetre.contenu.ContChatBox;
import client.gui.fenetre.contenu.ContConsole;
import client.gui.fenetre.contenu.ContDebug;
import client.gui.fenetre.contenu.ContMenuPrincipal;
import client.gui.fenetre.contenu.action.Icone;
import client.gui.fenetre.contenu.caract.ContCaracteristiques;
import client.gui.fenetre.contenu.carte.ContMiniMap;
import client.gui.fenetre.contenu.inventaire.ContInventaire;
import client.gui.fenetre.contenu.quete.ContQuetes;
import client.modele.Monde;

import com.jme.input.InputHandler;
import com.jme.input.MouseInput;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;
import communication.CommClient;

/**
 * Node mère des nodes de chacune des interfaces composant le GUI
 * @author Arnaud
 *
 */
public class GuiPrincipal extends Node {
	
	private static final long serialVersionUID = 1L;
	
	private Monde monde;
    private Curseur curseur;
    private GuiDesktop guiDesktop;
    //public static ContConsole console;

	private final Color COMMON_BG_COLOR = new Color(0, 0, 250, 20);
	private final Color COMMON_FG_COLOR = Color.WHITE;
    
    private Vector children;
    private ContChatBox contChatBox;

    public static Fenetre console;
    public static Fenetre chatBox;
    public static Fenetre mapBox;
    public static Fenetre mainMenu;
    public static Fenetre debug;
    public static Fenetre caracts;
    public static Fenetre inventaire;
    public static Fenetre quetes;
    public static Fenetre icone1;
    public static Fenetre icone2;
    public static Fenetre icone3;
    private CommClient commClient;

    /**
     * Constructeur par défaut, se rattache au rootNode du monde
     * @param monde
     * @param commClient
     */
	public GuiPrincipal(Monde monde, CommClient commClient) {
		super("GUI");
		this.commClient = commClient;
    	resetColors(COMMON_FG_COLOR, new Color(0, 0, 0, 0)/*new Color(0,0,0,0)*/);
		this.monde = monde;
		guiDesktop = new GuiDesktop(monde.getDisplay());
		
        this.setRenderQueueMode( Renderer.QUEUE_ORTHO );

        children = new Vector();
        populate();
        attachAllChildren();
        
        // don't cull the gui away
        this.setCullMode( Spatial.CULL_NEVER );
        // gui needs no lighting
        this.setLightCombineMode( LightState.OFF );
        // update the render states (especially the texture state of the deskop!)
        this.updateRenderState();
        // update the world vectors (needed as we have altered local translation of the desktop and it's
        //  not called in the update loop)
        this.updateGeometricState( 0, true );

        // finally show the system mouse cursor to allow the user to click our button
        MouseInput.get().setCursorVisible( true );
        //TODO Gérer le curseur graphique
//        curseur = new Curseur(monde);
        //curseur.setLocalTranslation(new Vector3f(display.getWidth()/2, display.getHeight()/2, 0));
        
        switchInventaire();
        switchQuetes();
	}
	
	/**
	 * Initialise les couleurs par défaut (transparence) pour les objets des classes de swing et awt
	 * @param fgColor
	 * @param bgColor
	 */
	public static void resetColors(Color fgColor, Color bgColor){
    	UIManager.put("Button.foreground", fgColor);
    	UIManager.put("Button.background", bgColor);
    	UIManager.put("Label.foreground", fgColor);
    	UIManager.put("Label.background", bgColor);
    	UIManager.put("Panel.foreground", fgColor);
    	UIManager.put("Panel.background", bgColor);
    	UIManager.put("TabbedPane.foreground", fgColor);
    	UIManager.put("TabbedPane.background", bgColor);
    	UIManager.put("TextPane.foreground", fgColor);
    	UIManager.put("TextPane.background", bgColor);
    	UIManager.put("ScrollPane.foreground", fgColor);
    	UIManager.put("ScrollPane.background", bgColor);
    	UIManager.put("Viewport.foreground", fgColor);
    	UIManager.put("Viewport.background", bgColor);
    	UIManager.put("EditorPane.foreground", fgColor);
    	UIManager.put("EditorPane.background", bgColor);
    	UIManager.put("TextField.foreground", fgColor);
    	UIManager.put("TextField.background", bgColor);
    	//UIManager.put("TitledBorder.foreground",fgColor);
    	UIManager.put("TitledBorder.titleColor", fgColor);
    	UIManager.put("TitledBorder.font", new Font(null, Font.BOLD, 12));
	}
	
	/**
	 * Remplit l'interface graphique de toutes ses fenêtres.
	 * Renseigne les caractéristiques (dimensions, zones de positionnement) pour chacune des fenêtres.
	 *
	 */
	private void populate() {
		
		InputHandler input = monde.getInput();
		DisplaySystem display = monde.getDisplay();
		Node rootNode = monde.getRootNode();
		
		console = new Fenetre(this, "console", input, display, 400, 200, 0, 0, 1);
		console.setContenu(new ContConsole());
		console.setBackground(COMMON_BG_COLOR);
		
		chatBox = new Fenetre(this, "chatBox", input, display, 350, 200, 0, 0, 1);
		chatBox.setContenu(contChatBox = new ContChatBox(rootNode, commClient));
		chatBox.setBackground(COMMON_BG_COLOR);
		
		commClient.setContChatBox(contChatBox);
		
		mapBox = new Fenetre(this, "mapBox", input, display, 170, 200, 0, 0, 1);
		mapBox.setContenu(new ContMiniMap());
		mapBox.setBackground(COMMON_BG_COLOR);
		
		mainMenu = new Fenetre(this, "mainMenu", input, display, 170, 120, 0, 0, 1);
		mainMenu.setContenu(new ContMenuPrincipal(monde));
		mainMenu.setBackground(COMMON_BG_COLOR);
		
		debug = new Fenetre(this, "debug", input, display, 170, 80, 0, 0, 1);
		debug.setContenu(new ContDebug());
		debug.setBackground(COMMON_BG_COLOR);
		
		caracts = new Fenetre(this, "caracts", input, display, 170, 200, 0, 0, 1);
		caracts.setContenu(new ContCaracteristiques(monde.getPersonnage()));
		caracts.setBackground(COMMON_BG_COLOR);

		inventaire = new Fenetre(this, "inventaire", input, display, 500, 380, 0, 0, 1);
		inventaire.setContenu(new ContInventaire(/*monde.getPersonnage()*/));
		inventaire.setBackground(COMMON_BG_COLOR);
		
		quetes = new Fenetre(this, "quetes", input, display, 400, 500, 0, 0, 1);
		quetes.setContenu(new ContQuetes(monde));
		quetes.setBackground(COMMON_BG_COLOR);
		
		/*Fenetre ex2 = new Fenetre(this, "ex2", input, display, 200, 100, 0, 0, 1);
		ex2.setContenu(new ContExample());*/
		
		icone1 = new Fenetre(this, "icone1", input, display, 60, 68, 0, 0, 1);
		Action action1 = new Action();
		action1.setCompetence(new Competence("deplacement"));
		icone1.setContenu(new Icone(action1));
		
		icone2 = new Fenetre(this, "icone2", input, display, 60, 68, 0, 0, 1);
		Action action2 = new Action();
		action2.setCompetence(new Competence("construire"));
		icone2.setContenu(new Icone(action2));
		
		icone3 = new Fenetre(this, "icone3", input, display, 60, 68, 0, 0, 1);
		Action action3 = new Action();
		action3.setCompetence(new Competence("deplacement"));
		icone3.setContenu(new Icone(action3));

		guiDesktop.addFenetre(mainMenu);
		guiDesktop.addFenetre(console);
		guiDesktop.addFenetre(chatBox);
		//guiDesktop.addFenetre(debug);
		guiDesktop.addFenetre(caracts);
		guiDesktop.addFenetre(inventaire);
		guiDesktop.addFenetre(quetes);
		guiDesktop.addFenetre(mapBox);
		guiDesktop.addFenetre(icone1);
		guiDesktop.addFenetre(icone2);
		guiDesktop.addFenetre(icone3);
		
		guiDesktop.setFenetrePosition(mainMenu, FenetrePosition.FIX_EAST);
		guiDesktop.setFenetrePosition(console, FenetrePosition.FIX_NORTH);
		guiDesktop.setFenetrePosition(chatBox, FenetrePosition.FIX_SOUTH);
		//guiDesktop.setFenetrePosition(debug, FenetrePosition.FIX_EAST);
		guiDesktop.setFenetrePositionCentree(inventaire);
		guiDesktop.setFenetrePositionCentree(quetes);
		guiDesktop.setFenetrePosition(caracts, FenetrePosition.FIX_EAST);
		guiDesktop.setFenetrePosition(mapBox, FenetrePosition.FIX_EAST);
		guiDesktop.setFenetrePosition(icone1, FenetrePosition.FIX_NORTH);
		guiDesktop.setFenetrePosition(icone2, FenetrePosition.FIX_NORTH);
		guiDesktop.setFenetrePosition(icone3, FenetrePosition.FIX_NORTH);
		
		//System.out.println(guiDesktop.toString());
		children.add(new NodeFenetre("node-mainMenu", mainMenu));
		children.add(new NodeFenetre("node-console", console));
		children.add(new NodeFenetre("node-chatBox", chatBox));
		//children.add(new NodeFenetre("node-debug", debug));
		children.add(new NodeFenetre("node-inventaire", inventaire));
		children.add(new NodeFenetre("node-quetes", quetes));
		children.add(new NodeFenetre("node-caracteristiques", caracts));
		children.add(new NodeFenetre("node-mapBox", mapBox));
		children.add(new NodeFenetre("node-icone1", icone1));
		children.add(new NodeFenetre("node-icone2", icone2));
		children.add(new NodeFenetre("node-icone3", icone3));
	}
	
	/**
	 * Attache tous les enfants du GuiPrincipal à celui-ci
	 *
	 */
	private void attachAllChildren(){
		for(int i=0; i<children.size(); i++){
			NodeFenetre nodef = (NodeFenetre)children.elementAt(i);
				attachChild(nodef);
		}
	}
	
	/*private String getChildrenName(int pos){
		return ((NodeFenetre)children.elementAt(pos)).getName();
	}
	
	private int getChildrenPosition(String nom){
		for(int i=0; i<children.size(); i++)
			if(getChildrenName(i).equals(nom))
				return i;
		return -1;
	}*/
	
	public Curseur getCurseur(){
		return curseur;
	}
	
	public void refresh(){
		for(int i=0; i<children.size(); i++){
			NodeFenetre nodef = ((NodeFenetre)children.elementAt(i));
			//if(!(nodef.getName().equals("curseur") && !isCurseurVisible))
				nodef.refresh();
		}
	}
	
	public GuiDesktop getGuiDesktop(){
		return guiDesktop;
	}

	/**
	 * Active/désactive l'affichage de la fenêtre d'inventaire
	 *
	 */
	public void switchInventaire(){
		for(Iterator it = children.iterator(); it.hasNext();){
			NodeFenetre nodef = (NodeFenetre) it.next();
			if(nodef.getName().equals("node-inventaire"))
				if(this.hasChild(nodef))
					this.detachChild(nodef);
				else
					this.attachChild(nodef);
		}
	}
	
	/**
	 * Active/désactive l'affichage de la fenêtre des quêtes
	 *
	 */
	public void switchQuetes(){
		for(Iterator it = children.iterator(); it.hasNext();){
			NodeFenetre nodef = (NodeFenetre) it.next();
			if(nodef.getName().equals("node-quetes"))
				if(this.hasChild(nodef))
					this.detachChild(nodef);
				else
					this.attachChild(nodef);
		}
	}

}
