/*
 * Created on 27 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.caracteristique;

import modele.objet.ObjetConcret;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Vitalite extends CaracteristiqueDynamique {
	
	public Vitalite (String nom, String expressionMin,
			int valeur, String expressionMax, ObjetConcret objet) {
		super (nom, expressionMin, valeur, expressionMax, objet);
	}
	
	public void setValeur (int valeur) {
		if (valeur < this.valeurMin) {
			//TODO: il faut faire qqch
			//finDeVie.agir();
		} else super.setValeur (valeur);
	}
}
