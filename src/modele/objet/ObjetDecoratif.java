/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.objet;

import java.util.Vector;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ObjetDecoratif extends ObjetAbstrait {
	protected int id;
	private Vector caracteristiques;
	
	/**
	 * @param nom
	 */
	public ObjetDecoratif(String nom) {
		super(nom);
		setName("ObjetDecoratif");
		this.isCollidable = true;
		// TODO Auto-generated constructor stub
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public int getId() {
		return id;
	}
	
}
