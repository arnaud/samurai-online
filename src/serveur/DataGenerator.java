/*
 * Created on 13 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package serveur;

import java.util.Vector;

import modele.objet.Arme;
import modele.objet.Batiment;
import modele.objet.ObjetDecoratif;
import modele.objet.Outil;
import modele.personne.Personnage;

import com.jme.scene.Node;
import communication.CommServeur;
import communication.actions.StandardAction;
import communication.util.SetMove;

/**
 * @author gautieje
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DataGenerator {
	private Serveur serveur;
	private CommServeur commServeur;
	
	public DataGenerator(Serveur serveur, CommServeur commServeur) {
		this.serveur = serveur;
		this.commServeur = commServeur;
	}
	
	/**
	 * Méthode permettant de renvoyer la liste de personnages qui sont à proximité du perso.
	 * @param perso
	 * @return Retourne un vecteur de personnages qui sont à proximité du perso.
	 */
	public Vector persoNearThisOne(Personnage perso) {
		Node rootNode = serveur.getMonde().getRootNode();
		
		int rayon = 5000;
		float xr = perso.getPositionX();
		float yr = perso.getPositionY();
		
		Vector childNodes = (Vector)(serveur.getMonde().getPersonnages());
		Vector persoProximite = new Vector();
		
		for(int i=0; i<childNodes.size();i++) {
			try{
				Node perso2 = (Node)childNodes.get(i);
				if(perso2.getName().compareTo("Personnage")==0) {
					Personnage perso3 = (Personnage)perso2;
					float dx = xr - perso3.getPositionX();
					float dy = yr - perso3.getPositionY();
					if(perso3.getId()!=perso.getId() && dx*dx + dy*dy < rayon*rayon) {
						persoProximite.add(perso3);
					}
				}
			}catch(ClassCastException e){}
		}

		return persoProximite;
	}
	
	public boolean isPersoNearOne(Personnage perso,int idAutrePerso){
		Node rootNode = serveur.getMonde().getRootNode();
		
		int rayon = 25;
		float xr = perso.getPositionX();
		float yr = perso.getPositionY();
		
		Vector childNodes = (Vector)(serveur.getMonde().getPersonnages());
		
		for(int i=0; i<childNodes.size();i++) {
			try{
				Node perso2 = (Node)childNodes.get(i);
				if(perso2.getName().compareTo("Personnage")==0) {
					Personnage perso3 = (Personnage)perso2;
					float dx = xr - perso3.getPositionX();
					float dy = yr - perso3.getPositionY();
					if(perso3.getId()== idAutrePerso && dx*dx + dy*dy < rayon*rayon) {
						return true;
					}
				}
			}catch(ClassCastException e){}
		}
		return false;
	}	
	
	/**
	 * Méthode permettant de renvoyer la liste de bâtiments qui sont à proximité du perso.
	 * @param perso
	 * @return Retourne un vecteur de bâtiments qui sont à proximité du perso.
	 */
	public Vector batimentsNearThisOne(Personnage perso) {
		int rayon = 5000;
		float xr = perso.getPositionX();
		float yr = perso.getPositionY();
		
		Vector childNodes = serveur.getMonde().getBatiments();
		Vector batiProximite = new Vector();
		
		for(int i=0; i<childNodes.size();i++) {
			try{
				Node deco = (Node)childNodes.get(i);
				if(deco.getName().compareTo("Batiment")==0) {
					Batiment deco2 = (Batiment)deco;
					float dx = xr - deco2.getPositionX();
					float dy = yr - deco2.getPositionY();
					if(dx*dx + dy*dy < rayon*rayon) {
						batiProximite.add(deco2);
					}
				}
			}catch(ClassCastException e){}
		}

		return batiProximite;
	}
	
	public void deplacerPersonnage(Personnage perso, int idaction) {
		SetMove move = new SetMove(perso.getId(), perso.getLocalTranslation());
		byte[] objetToSend = StandardAction.getBytes(SetMove.class, move);


		
		// pour tous les perso à proximité envoyer ceci
		Vector personnages = persoNearThisOne(perso);
		for(int i=0;i<personnages.size();i++) {
			ClientConnecte client = serveur.getClientFromIdPerso(((Personnage)personnages.get(i)).getId());
			commServeur.sendTo(StandardAction.MOVE, client.getAdresse(), client.getPort(), objetToSend);
		}
		
		// pour le perso qui fait l'action
		ClientConnecte client = serveur.getClientFromIdPerso(perso.getId());
		commServeur.sendTo(StandardAction.MOVE, client.getAdresse(), client.getPort(), objetToSend);
	}

	/**
	 * @param perso
	 * @return
	 */
	public Vector decoNearThisOne(Personnage perso) {
		int rayon = 5000;
		float xr = perso.getPositionX();
		float yr = perso.getPositionY();
		
		Vector childNodes = (Vector)(serveur.getMonde().getObjetsDeco());
		System.err.println("childnode size : "+ childNodes.size());
		Vector decoProximite = new Vector();
		
		for(int i=0; i<childNodes.size();i++) {
			try{
				Node deco = (Node)childNodes.get(i);
				if(deco.getName().compareTo("ObjetDecoratif")==0) {
					ObjetDecoratif deco2 = (ObjetDecoratif)deco;
					float dx = xr - deco2.getPositionX();
					float dy = yr - deco2.getPositionY();
					if(dx*dx + dy*dy < rayon*rayon) {
						decoProximite.add(deco2);
					}
				}
			}catch(ClassCastException e){}
		}

		return decoProximite;
	}
	
	/**
	 * @param perso
	 * @return
	 */
	public Vector armesNearThisOne(Personnage perso) {
		int rayon = 5000;
		float xr = perso.getPositionX();
		float yr = perso.getPositionY();
		
		Vector childNodes = serveur.getMonde().getArmes();
		System.err.println("childnode size : "+ childNodes.size());
		Vector armesProximite = new Vector();
		
		for(int i=0; i<childNodes.size();i++) {
			try{
				Node objet = (Node)childNodes.get(i);
				//if(deco.getName().compareTo("ObjetDecoratif")==0) {
					Arme objet2 = (Arme)objet;
					float dx = xr - objet2.getPositionX();
					float dy = yr - objet2.getPositionY();
					if(dx*dx + dy*dy < rayon*rayon) {
						armesProximite.add(objet2);
					}
				//}
			} catch(ClassCastException e){}
		}

		return armesProximite;
	}
	
	/**
	 * @param perso
	 * @return
	 */
	public Vector outilsNearThisOne(Personnage perso) {
		int rayon = 5000;
		float xr = perso.getPositionX();
		float yr = perso.getPositionY();
		
		Vector childNodes = serveur.getMonde().getOutils();
		System.err.println("childnode size outils : "+ childNodes.size());
		Vector outilsProximite = new Vector();
		
		for(int i=0; i<childNodes.size();i++) {
			try{
				Node objet = (Node)childNodes.get(i);
				//if(deco.getName().compareTo("ObjetDecoratif")==0) {
					Outil objet2 = (Outil)objet;
					float dx = xr - objet2.getPositionX();
					float dy = yr - objet2.getPositionY();
					if(dx*dx + dy*dy < rayon*rayon) {
						outilsProximite.add(objet2);
					}
				//}
			} catch(ClassCastException e){}
		}

		return outilsProximite;
	}
	
}
