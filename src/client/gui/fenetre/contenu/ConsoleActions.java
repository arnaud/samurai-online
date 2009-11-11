package client.gui.fenetre.contenu;


import client.modele.Monde;
import client.modele.Personnage;

import com.jme.math.Vector3f;
import com.jme.scene.Node;

public class ConsoleActions {

	public static String getVersion(){
		return "Version 0.6 [beta]";
	}
	
	private static String addHelpAction(String commande, String description){
		return "<br>- /"+commande+" - "+description;
	}
	
	public static String getHelp(){
		return "<u>Commandes accessibles :</u>"+
		addHelpAction("help", "affiche de l'aide")+
		addHelpAction("version", "affiche la version courante du jeu")+
		addHelpAction("display", "affiche la résolution et le nombre de couleurs")+
		addHelpAction("position", "affiche la position du personnage sur la carte")+
		addHelpAction("position [x] [y]", "définit la position du personnage sur la carte");//+
		//addHelpAction("fps on/off", "affiche ou cache le nombre d'images par seconde");
	}
	
	public static String getDisplay(){
		Monde game = (Monde) Monde.instance;
		int width = game.getDisplay().getWidth();
		int height = game.getDisplay().getHeight();
		int colors = game.getDisplay().getBitDepth();
		return "Résolution : "+width+" * "+height+" / "+colors;
	}
	
	public static String getPosition(){
		Monde game = (Monde) Monde.instance;
		Personnage perso = game.getPersonnage();
		Vector3f vectPos = perso.getPosition();
		return "Vous êtes en [x="+vectPos.x+", y="+vectPos.y+"]";
	}
	
	public static String setFps(boolean onOff){
		Monde game = (Monde) Monde.instance;
		Node rootNode = game.getRootNode();
		Node fpsNode = game.getFpsNode();
		
		if(onOff==true)
			if(!rootNode.hasChild(fpsNode)){
				rootNode.attachChild(fpsNode);
				return "Fps affichés à l'écran";
			}
		else
			if(rootNode.hasChild(fpsNode)){
				rootNode.detachChild(fpsNode);
				return "Fps affichés à l'écran";
			}
		return "Erreur lors de l'attachement du noeud fps!";
	}
	
	public static String setPosition(float posx, float posy) {
		Monde game = (Monde) Monde.instance;
		Personnage perso = game.getPersonnage();
		perso.setPositionX(posx);
		perso.setPositionY(posy);
		return "Personnage positionné en [x="+posx+", y="+posy+"]";
	}
}
