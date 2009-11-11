/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.caracteristique;

import java.io.Serializable;
import java.util.Iterator;

import modele.objet.ObjetAbstrait;


/**
 * @author canonlo
 * 
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class Caracteristique implements Serializable {
	protected String nom;
	protected ObjetAbstrait objet;
	
	protected int valeurMin;
	protected int valeur;
	protected int valeurMax;
	
	public Caracteristique () {
		this ("?", null);
	}
	
	public Caracteristique (String nom, ObjetAbstrait objet) {
		this (nom, 1, 1, objet);
	}

	public Caracteristique (String nom, int valeurMin, int valeurMax, ObjetAbstrait objet) {
		this (nom, valeurMin, valeurMin, valeurMax, objet);
	}
	
	public Caracteristique (String nom, int valeurMin, int valeur, int valeurMax, ObjetAbstrait objet) {
		this.nom = nom;
		this.valeurMin = valeurMin;
		this.valeur = valeur;
		this.valeurMax = valeurMax;
		this.objet = objet;
	}
	
	public void setValeur (int valeur) {
		this.valeur = valeur;
		if (this.valeur < this.valeurMin)
			this.valeur = this.valeurMin;
		else if (this.valeur > this.valeurMax)
			this.valeur = this.valeurMax;
		
		if (objet != null) {
			// Propagation des changements
			for (Iterator it = objet.getCaracteristiques().iterator(); it.hasNext();) {
				Caracteristique caraCourante = (Caracteristique) it.next();
				if (caraCourante instanceof CaracteristiqueDynamique)
					((CaracteristiqueDynamique)caraCourante).update();
			}
		}
	}
	
	public int getValeur(){
		return valeur;
	}
	
	public int getValeurMax () {
		return this.valeurMax;
	}

	public int getValeurMin () {
		return this.valeurMin;
	}
	
	public String getNom() {
		return nom;
	}
	
	public boolean isComparable (Caracteristique cara) {
		return this.getNom().equals(cara.getNom());
	}
	
	public boolean isInferior (Caracteristique cara) {
		return this.getValeur() < cara.getValeur();
	}
	
	public String toString () {
		return this.getNom() + " = " + this.getValeur() + " ["
		+ this.getValeurMin() + "; " + this.getValeurMax() + "]";
	}
}
