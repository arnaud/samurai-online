/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.objet;

import modele.personne.Inventaire;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Objet extends ObjetDecoratif implements Transportable{

	/**
	 * @param nom
	 */
	public Objet(String nom) {
		super(nom);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see modele.objets.Transportable#setEmplacement(modele.personnes.Inventaire)
	 */
	public void setEmplacement(Inventaire inventaire) {
		// TODO Auto-generated method stub
		
	}

}
