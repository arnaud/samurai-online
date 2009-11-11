/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.objet;


/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Batiment extends ObjetConcret implements Utilisable{
	protected int id;
	
	/**
	 * @param nom
	 */
	public Batiment(String nom) {
		super(nom);
		setName("Batiment");
		this.isCollidable = true;
		// TODO Auto-generated constructor stub
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
}
