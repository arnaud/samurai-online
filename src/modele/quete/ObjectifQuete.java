package modele.quete;

import java.io.Serializable;

public class ObjectifQuete implements Serializable {

	private String description;
	private int numero;
	private boolean estRealise;
	private int id;
	
	public ObjectifQuete(int numero,String description){
		this.numero = numero;
		this.description = description;
		estRealise=false;
	}
	

	public void getInfos(){
		System.out.println("Objectif n"+numero+" : "+description);		
	}

	public int getNumero(){
		return numero;
	}
	
	public String getDescription(){
		return description;
	}
	
	public boolean isRealise(){
		return estRealise;
	}
	
	public void setReussi(){
		if(!estRealise){
			estRealise=true;
		}
	}

	public void setIdQuete(int idQuete) {
		id=idQuete;
	}
	
	public int getIdQuete() {
		return id;
	}
}
