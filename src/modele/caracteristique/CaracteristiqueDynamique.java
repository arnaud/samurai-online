/*
 * Created on 27 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.caracteristique;

import java.util.Iterator;

import modele.objet.ObjetAbstrait;

import org.nfunk.jep.JEP;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CaracteristiqueDynamique extends Caracteristique {
	protected String expressionMax;
	protected String expressionMin;
	
	transient private JEP myParser;

	public CaracteristiqueDynamique (String nom,
			String expressionMin, int valeur, String expressionMax,
			ObjetAbstrait objet) {
		super (nom, objet);
		this.expressionMin = expressionMin;
		this.expressionMax = expressionMax;

		// Préparation de l'expression
		myParser = new JEP ();
		myParser.setImplicitMul(true);
		myParser.addStandardConstants();
		myParser.addStandardFunctions();
		
		// Initialisation de la valeur max et de la valeur courante
		update ();
		setValeur(valeur);
	}

	public void changerCaracteristiqueInfluente (ObjetAbstrait objet,
			String expressionMin, String expressionMax) {
		// Mise à jour
		this.objet = objet;
		this.expressionMin = expressionMin;
		this.expressionMax = expressionMax;

		update ();
	}
	
	public void update () {
		
		if (objet != null) {
			// Mise à jour des valeurs des variables
			for (Iterator itCaractInfluente = objet.getCaracteristiques().iterator(); itCaractInfluente.hasNext ();) {
				Caracteristique caractInfluente = (Caracteristique) itCaractInfluente.next();
				myParser.addVariable(caractInfluente.getNom(), caractInfluente.getValeur());
				myParser.addVariable(caractInfluente.getNom()+"Min", caractInfluente.getValeurMin());
				myParser.addVariable(caractInfluente.getNom()+"Max", caractInfluente.getValeurMax());
				
			}
			
			// Évaluation des expressions
			myParser.parseExpression(expressionMin);
			this.setValeurMin((int)myParser.getValue());
			myParser.parseExpression(expressionMax);
			this.setValeurMax((int)myParser.getValue());
		}
		
	}
	
	private void setValeurMin (int valeurMin) {
		this.valeurMin = valeurMin;
		if (valeur < valeurMin)
			setValeur (valeurMin);
	}
	
	private void setValeurMax (int valeurMax) {
		this.valeurMax = valeurMax;
		if (valeur > valeurMax)
			setValeur (valeurMax);
	}
}
