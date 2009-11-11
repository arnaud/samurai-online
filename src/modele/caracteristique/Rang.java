/*
 * Created on 3 avr. 2006
 *
 * TODO 
 */
package modele.caracteristique;

import java.util.Collection;

public class Rang extends Caracteristique {
	
	private Collection competences;
	private int niveauMinimum;
	private Rang rangPrecedent;
	private Collection rangsSuivants;
	
	public Rang(String nom){
		this (nom, 0);
	}
	
	public Rang(String nom, int niveau){
		this (nom, niveau, null);
	}
	
	public Rang(String nom, int niveau, Collection competences){
		this (nom, niveau, competences, null, null);
	}
	
	public Rang(String nom, int niveau, Collection competences,
			Rang rangPrecedent, Collection rangsSuivants)  {
		super (nom, null);
		this.niveauMinimum=niveau;
		this.competences=competences;
		this.rangPrecedent=rangPrecedent;
		this.rangsSuivants=rangsSuivants;
	}
	
	public Collection getRangSuivant(){
		return rangsSuivants;
	}

	public Rang getRangPrecedent(){
		return rangPrecedent;
	}
	
	public int getNiveauMinimum(){
		return niveauMinimum;
	}
	
	public Collection getCompetences(){
		return competences;
	}
	
	public boolean isComparable (Caracteristique cara) {
		if (cara instanceof Rang) return true;
		else return false;
	}
	
	public boolean isInferior (Caracteristique cara) {
		if (cara instanceof Rang) {
			Rang rangPrec = ((Rang)cara).getRangPrecedent();
			while (rangPrec != null) {
				if (this == rangPrec) return true;
				rangPrec = ((Rang)cara).getRangPrecedent();
			}
			return false;
		} else return false; //TODO: gérer une exception ?
	}
	
	public String toString(){
		return "Rang : ["+nom+"]";
	}
}
