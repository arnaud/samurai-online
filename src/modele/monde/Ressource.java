/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.monde;

import java.io.Serializable;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Ressource implements Serializable  {

	private int valeur;
	
	public Ressource(){
		
	}
	
	public int getValeur(){
		return valeur;
	}
	
	public void setValeur(int valeur){
		this.valeur=valeur;
	}
}
