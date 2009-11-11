/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.objet;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import modele.caracteristique.Caracteristique;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;


/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ObjetAbstrait extends ExtendedNode {
	protected String nom;
	protected Collection caracteristiques;
	
	public ObjetAbstrait(){
		this ("?");
	}
	
	public ObjetAbstrait(String nom){
		this.nom=nom;
		this.caracteristiques=new Vector();
	}
	
	public Vector3f getDirection(){
		Matrix3f mat = this.getLocalRotation().toRotationMatrix();
		return new Vector3f(mat.m00, mat.m11, mat.m22);
	}
	
	public void setDirection(Vector3f vectDirection){
		Matrix3f mat = new Matrix3f();
		mat.m00 = vectDirection.x;
		mat.m11 = vectDirection.y;
		mat.m22 = vectDirection.z;
		this.setLocalRotation(mat);
	}
	
	public void setNom(String nom){
		this.nom=nom;
	}
	
	public String getNom(){
		return this.nom;
	}
	
	public void addCaracteristique(Caracteristique element){
		this.caracteristiques.add(element);
	}
	
	public Caracteristique getCaracteristique(String caractType) {
		for (Iterator it = caracteristiques.iterator(); it.hasNext();) {
			Caracteristique caracteristique = (Caracteristique) it.next();
			if (caracteristique.getNom().equals (caractType)) return caracteristique;
		}
		return null;
	}
	
	public void setCaracteristiques(Collection cara) {
		caracteristiques = cara;
	}
	
	public Collection getCaracteristiques() {
		return caracteristiques;
	}
}
