/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.personne;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

import modele.monde.Bois;
import modele.monde.Fer;
import modele.monde.Nourriture;
import modele.monde.Or;
import modele.monde.Pierre;
import modele.objet.Transportable;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Inventaire implements Serializable {

private Vector objets;

//ressources contenues dans l'inventaire
private Fer fer;
private Bois bois;
private Or or;
private Pierre pierre;
private Nourriture nourriture;
	
	public Inventaire(){
		objets = new Vector();
		or = new Or();
		fer = new Fer();
		pierre = new Pierre();
		bois = new Bois();
		nourriture =new Nourriture();
	}
	
	public Inventaire(Fer fer,Bois bois,Pierre pierre,Or or,Nourriture nourriture){
		this ();
		this.or=or;
		this.fer=fer;
		this.pierre=pierre;
		this.bois=bois;
		this.nourriture=nourriture;
	}

	public void removeInventaire() {
		objets.removeAllElements();
	}
	
	public void addObjet(Transportable objet) {
		objets.add(objet);	
	}
		
	public void removeObjet(Transportable objet) {
		objets.removeElement(objet);
	}
	
	public int sizeObjets() {
		return objets.size();
	}
	
	public Transportable getObjet(int i){
		return (Transportable)objets.elementAt(i);
	}
	
	public Or getOr() {
		return or;
	}
	
	public void setOr(Or or){
		this.or=or;
	}
	
	public Fer getFer() {
		return fer;
	}
	
	public void setFer(Fer fer){
		this.fer=fer;
	}
	
	public Pierre getPierre() {
		return pierre;
	}
	
	public void setPierre(Pierre pierre){
		this.pierre=pierre;
	}
	
	public Bois getBois() {
		return bois;
	}
	
	public void setBois(Bois bois){
		this.bois=bois;
	}
	
	public Nourriture getNourriture() {
		return nourriture;
	}
	
	public void setNourriture(Nourriture nourriture){
		this.nourriture=nourriture;
	}	
	
	public void setRessources(Or or,Fer fer,Pierre pierre,Bois bois,Nourriture nourriture){
		this.nourriture=nourriture;
		this.bois=bois;
		this.pierre=pierre;
		this.fer=fer;
		this.or=or;
	}	
	
	public void setValeurRessources(int valeurOr,int valeurFer,int valeurPierre,int valeurBois,int valeurNourriture){
		nourriture.setValeur(valeurNourriture);
		bois.setValeur(valeurBois);
		pierre.setValeur(valeurPierre);
		fer.setValeur(valeurFer);
		or.setValeur(valeurOr);
	}
	
	public String getRessourcesToString(){
		String texte="";
		
		texte = texte.concat("["+nourriture.getValeur()+ ",");
		texte = texte.concat(bois.getValeur()+ ",");
		texte = texte.concat(+pierre.getValeur()+ ",");
		texte = texte.concat(fer.getValeur()+ ",");
		texte = texte.concat(or.getValeur()+"]");

		return (String)texte;
	
	}
	
	
}
