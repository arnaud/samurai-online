/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.personne;

import java.io.Serializable;
import java.util.Vector;

import modele.objet.Transportable;

/**
 * @author gautieje
 *
 */


public class Depot implements Serializable {

	private Vector objets;
	
	public Depot(){
		objets =  new Vector();
	}
	
	public void removeDepot() {
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
	
	public void limiteDepot(int size) {
		objets.setSize(size);
	}
	
	public int nbObjets() {
		return objets.size();
	}
}


