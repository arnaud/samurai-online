
package modele.quete;

import java.io.Serializable;
import java.util.*;

import modele.personne.Personnage;



public class Quete implements Serializable {

	private Collection objectifsQuete;
	private int idQuete,nextObjectif;
	private String nomQuete,descriptionQuete;
	private Personnage responsable=null;
	private boolean estRealisee = false; 
	
	public Quete(int id, String nom){
		this.idQuete=id;
		this.nomQuete=nom;	
	}
	
	public Quete(int id, String nom,String description){
		this.idQuete=id;
		this.nomQuete=nom;	
		this.descriptionQuete=description;
		objectifsQuete = new Vector();
	}
	public Quete(int id,String nom,String description,Personnage perso){
		this(id,nom,description);
		this.responsable=perso;
	}

	public int getId(){
		return idQuete;
	}
	
	public void setId(int idQuete){
		this.idQuete=idQuete;
	}
	
	public String getnomQuete(){
		return nomQuete;
	}
	
	public void setnomQuete(String nomQuete){
		this.nomQuete=nomQuete;
	}
	
	public String getDescriptionQuete(){
		return descriptionQuete;
	}
	
	public void setDescriptionQuete(String description){
		this.descriptionQuete=description;
	}
	/**
	 *Methode retournant les informations concernant la quête
	 *
	 */
	public void getInfos() {
		System.out.println(" Nom de la quete :"+nomQuete+" ["+idQuete+"]");
		if(!this.isObjectifEmpty()){
			Iterator iter;
			try{
				for(iter = objectifsQuete.iterator();iter.hasNext();){
					ObjectifQuete objectif=(ObjectifQuete)iter.next();
					objectif.getInfos();
				}
			}catch(Exception e){
				System.out.println("Exception sG:" + e);
			}	
		}
	}
	/**
	 * Methode retournant le numero du prochain objectif a realiser de la quete
	 * @return nextObjectif numero du prochain objectif a realiser
	 */
	public int nextObjectif(){
		int[] numeros = new int[20];
		int index=0;
		try{
		if(!verifierQuete()){
			for(Iterator iter = objectifsQuete.iterator();iter.hasNext();){
				ObjectifQuete objectif=(ObjectifQuete)iter.next();
				objectif.getInfos();
				if (!objectif.isRealise()){
					numeros[index++]=objectif.getNumero();
				}
			}
			System.out.println("Tableau des objectifs"+numeros.toString());
			Arrays.sort(numeros);
			index=0;
			while (((int)numeros[index]==0)&&(index<20)){index++;}
			nextObjectif=(int)numeros[index];
			System.out.println("Prochain objectif "+nextObjectif );
		}else{
			nextObjectif = -1;			
		}
		}catch(Exception e){
			System.err.println("Exception :"+e);
			e.printStackTrace();
		}
		
		return nextObjectif;
	}
	
	public void addObjectif(ObjectifQuete element) {
		objectifsQuete.add(element);
	}
	
	public Collection getObjectifs() {
		return objectifsQuete;
	}

	public ObjectifQuete getObjectif(int numero) {
		if(!this.isObjectifEmpty()){
			try{
				for(Iterator iter = objectifsQuete.iterator();iter.hasNext();){
					ObjectifQuete objectif=(ObjectifQuete)iter.next();
					if( objectif.getNumero()==numero){
						return objectif;
					}
				}
			}catch(Exception e){
				System.out.println("Exception sG:" + e);
			}
		}
		return null;
	}
	
	public void setNextObjectif(int nextObjectif){
		this.nextObjectif=nextObjectif;
	}
	
	public int getNextObjectif(){
		return nextObjectif;
	}
	
	
	/**
	 * La Quete est declaree realisee automatiquement si tous ses objectifs sont realisees
	 * 
	 * @return
	 */
	
	public boolean verifierQuete(){
		
		for(Iterator iter = objectifsQuete.iterator();iter.hasNext();){
			ObjectifQuete objectif=(ObjectifQuete)iter.next();
			if (!objectif.isRealise()){
				return false;
			}
		}
		estRealisee=true;
		return true;
		
	}
	public boolean removeObjectif(ObjectifQuete element) {
		return objectifsQuete.remove(element);
	}

	public boolean isObjectifEmpty() {
		return objectifsQuete.isEmpty();
	}

	public void clearObjectif() {
		objectifsQuete.clear();
	}

	public boolean containsObjectif(ObjectifQuete element) {
		return objectifsQuete.contains(element);
	}

	public int objectifSize() {
		return objectifsQuete.size();
	}
	
	public boolean isRealisee(){
		return estRealisee;
	}
}
