/*
 * Created on 27 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.caracteristique;

import modele.action.Competence;
import modele.personne.Personnage;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Experience extends CaracteristiqueDynamique {
	//Pas util pour le moment
	//private Competence competence;
	
	public Experience (int valeur, Personnage personnage, Competence competence) {
		super ("XP"+competence.getNom(),
				competence.getExpressionXpMin(), valeur, competence.getExpressionXpMax(),
				personnage);
		//this.competence = competence;
		personnage.getNiveau().addExperience(this);
		personnage.getNiveau().update();
	}
}
