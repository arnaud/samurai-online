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
public class Arme extends ObjetConcret implements Transportable{
	protected int id;
	public final String partieDuCorps; // Partie qui peut utiliser l'outils
	// Mettre "main droite" pour tout les outils et "main gauche" pour ceux qui sont particuliers

	public Arme () {
		this ("?");
	}
	
	public Arme (String nom) {
		this (nom, null);
	}

	public Arme (String nom, String partieDuCorps) {
		super (nom);
		this.partieDuCorps = partieDuCorps;
	}

	/* (non-Javadoc)
	 * @see modele.objets.Transportable#setEmplacement(modele.personnes.Inventaire)
	 */
	public void setEmplacement(Inventaire inventaire) {
		// TODO Auto-generated method stub
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public int getId() {
		return id;
	}

}
