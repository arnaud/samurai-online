/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.monde;

import java.util.Collection;

import modele.objet.ExtendedNode;

import com.jme.math.Vector3f;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Zone extends ExtendedNode {
	
	private float rayon; //on considère des zones sphériques
	
	public Zone () {
		this (0);
	}
	
	public Zone (float rayon) {
		this (new Vector3f(), rayon);
	}
	
	public Zone (Vector3f position, float rayon) {
		setPosition(position);
		this.rayon = rayon;
	}
	
	public float getRayon() {
		return rayon;
	}

	public void setRayon(float rayon) {
		this.rayon = rayon;
	}

	public boolean isInsideZone (Vector3f position) {
		float diffX = this.getPositionX() - position.x;
		float diffY = this.getPositionY() - position.y;
		float diffZ = this.getPositionZ() - position.z;
		return diffX*diffX + diffY*diffY + diffZ*diffZ < this.rayon * this.rayon;
	}
	
	public boolean isInsideZoneObjectif(Vector3f position) {
		float diffX = this.getPositionX() - position.x;
		float diffY = this.getPositionZ() - position.z;
		//System.out.println(">> Perso["+position.x+","+position.z+"] Zone["+this.getPositionX()+","+this.getPositionZ()+"] ");
		return diffX*diffX + diffY*diffY< this.rayon * this.rayon;
	}
	
	public Collection objetInsideZone () {
		//TODO: parcourir tous les objets dans la carte
		//et retourner ceux qui sont dans la zone
		return null;
	}
}