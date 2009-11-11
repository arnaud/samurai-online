package modele.personne;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public class Groupe implements Serializable {

	private Collection personnages;
	private String  nomGroupe;
	private Personnage  responsable;
	
	public Groupe(String nomGroupe,Personnage responsable){
		this.nomGroupe=nomGroupe;
		this.responsable=responsable;
		personnages = new Vector();
		personnages.add(responsable); 
		
	}
	
	public void setNomGroupe(String nomGroupe) {
		this.nomGroupe=nomGroupe;
	}
	
	public String getNomGroupe() {
		 return this.nomGroupe;
	}
	
	public Personnage getResponsable() {
		 return responsable;
	}
	
	public boolean addPersonnage(Personnage element) {
		return personnages.add(element);
	}

	public boolean removePersonnage(Personnage element) {
		return personnages.remove(element);
	}

	public void supprimerGroupe() {
		Iterator iter;
		try{
			for(iter = personnages.iterator();iter.hasNext();){
				Personnage personnageAux=(Personnage)iter.next();
				personnageAux.setGroupe(null);
			}
			clearPersonnages();
		}catch(Exception e){
			System.out.println("Exception sG:" + e);
		}	
	}
	
	public int personnagesSize() {
		try{
			return personnages.size();
		}catch(Exception e){
			System.out.println("Exception pS:" + e);
			return 0;
		}
	}
	
	public boolean isPersonnagesEmpty() {
		return personnages.isEmpty();
	}

	public void setPersonnages(Collection value) {
		personnages = value;
	}
	
	public boolean containsPersonnage(Personnage element) {
		return personnages.contains(element);
	}

	public void clearPersonnages() {
		personnages.clear();
	}
}
