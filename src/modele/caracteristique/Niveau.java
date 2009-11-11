/*
 * Created on 27 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.caracteristique;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import modele.personne.Personnage;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Niveau extends Caracteristique {
	// Ensemble des experiences qui caractérise le niveau (fonction log)
	private Collection experiences;
	
	public Niveau (String nom, int valeurMin, int valeurMax, Personnage perso) {
		super (nom, valeurMin, 1, valeurMax, perso);
		this.experiences = new Vector (0);
		update ();
	}
	
	public boolean addExperience (Experience experience) {
		if (!this.experiences.contains(experience))
			return this.experiences.add(experience);
		else return false;
	}
	
	public void update () {
		int niveau = 0;
		for (Iterator itXp = experiences.iterator(); itXp.hasNext();) {
			Experience Xp = (Experience) itXp.next ();
			niveau += Xp.getValeur();
		}
		setValeur((int) Math.log(niveau));
	}
}
