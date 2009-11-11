/*
 * Created on 13 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.objet;

import com.jme.math.Vector3f;
import com.jme.scene.Node;

/**
 * @author leymetar
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExtendedNode extends Node{

	public ExtendedNode(){
		super("");
	}
	
	public ExtendedNode(String nom){
		super(nom);
	}
	
	public Vector3f getPosition(){
		return getLocalTranslation();
	}
	
	public float getPositionX(){
		return getPosition().x;
	}
	
	public float getPositionY(){
		return getPosition().z;
	}
	
	public float getPositionZ(){
		return getPosition().y;
	}
	
	public void setPosition(Vector3f vectPosition){
		setLocalTranslation(vectPosition);
	}
	
	public void setPositionX(float newX){
		Vector3f pos = getPosition();
		pos.x = newX;
		setPosition(pos);
	}
	
	public void setPositionY(float newY){
		Vector3f pos = getPosition();
		pos.z = newY;
		setPosition(pos);
	}
	
	public void setHauteur(float newZ){
		Vector3f pos = getPosition();
		pos.y = newZ;
		setPosition(pos);
	}

}
