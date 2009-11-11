/*
 * Created on 27 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.caracteristique;

import modele.personne.Personnage;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Mobilite extends CaracteristiqueDynamique {
	public Mobilite(String nom,
			String expressionMin, int valeur, String expressionMax,
			Personnage perso) {
		super (nom, expressionMin, valeur, expressionMax, perso);
	}

}
