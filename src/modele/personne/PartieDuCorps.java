/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.personne;
import java.io.Serializable;

import modele.objet.Outil;
import modele.objet.Utilisable;


public class PartieDuCorps implements Serializable {
	private String Nom;
	private Outil objet;
		
	public PartieDuCorps(String Nom){
		this.Nom=Nom;
	}
	
	public String getNomPartieDuCorps() {
		return Nom;
	}

	public void setNomPartieDuCorps(String nom) {
		this.Nom = nom;
	}

	public Outil getObjet () {
		return this.objet;
	}
	public void setObjet (Outil objet) {
		this.objet = objet;
	}
	
	public Utilisable removeObjet() {
		Utilisable objetAux;
	    
	    objetAux=objet;
		this.objet=null;
		return objetAux;
	}
	
	public boolean hasObjet (String objetType) {
		if (this.objet == null) return false;
		return objetType.equals (this.objet.getNom());
	}

}
