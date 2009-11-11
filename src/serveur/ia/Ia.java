/*
 * Created on 29 mai 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package serveur.ia;

import java.util.Random;
import java.util.Vector;

import modele.objet.Batiment;
import modele.personne.Personnage;

import com.jme.math.Vector3f;
import communication.CommServeur;

/**
 * @author jacquema
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Ia {
	protected Personnage perso;
	protected CommServeur commServeur;
	protected Vector3f destination = null;
	protected float pas;
	protected Random random;
	
	public Ia(CommServeur commServeur, Personnage perso) {
		this.perso = perso;
		this.commServeur = commServeur;
		random = new Random();
	}
	
	private float abs(float a) {
		if(a<0)
			return -1*a;
		else
			return a;
	}
	
	public void deplacement() {
		Vector3f pos = perso.getLocalTranslation();
		//pas = perso.getMobilite().getValeur()/100;
		pas = 1.2f;
		//System.out.println("pas : " + pas);
		float dx = destination.x-pos.x;
		float dz = destination.z-pos.z;
		if(abs(dx)<pas) {
			pos.x=destination.x;
		} else {
			if(dx>0) {
				pos.x+=pas;
			} else {
				pos.x-=pas;
			}
		}
		if(abs(dz)<pas) {
			pos.z=destination.z;
		} else {
			if(dz>0) {
				pos.z+=pas;
			} else {
				pos.z-=pas;
			}
		}
		if(pos.z==destination.z && pos.x==destination.x) {
			destination=null;
		}
	}
	
	private Batiment getAutreStore() {
		Vector batiments = commServeur.serveur.getMonde().getBatiments();
		Vector select = new Vector();
		for(int i=0; i<batiments.size(); i++) {
			Batiment bati = (Batiment)batiments.get(i);
			if(bati.getNom()=="store" && bati.getId()!=perso.getBatiment().getId())
				select.add(bati);
		}
		if(select.size()==0) {
			return null;
		} else {
			int x = random.nextInt()%select.size();
			return (Batiment)select.get(x);
		}
	}
	
	public void act() {
		Vector3f pos = perso.getLocalTranslation();
		if(destination!=null) {
			deplacement();
			commServeur.notifierDeplacement(perso, pos);
			//System.out.println("deplacement :" + perso.getLocalTranslation());
		} else {
			// on choisis une nouvelle destination
			int x = random.nextInt()%10;
			if(x==0) {
				// on va vers son propre store
				destination=perso.getBatiment().getLocalTranslation();
			} else if (x==5) {
				// on va vers un store voisin si il en existe
				Batiment bati = getAutreStore();
				if(bati!=null) 
					destination = bati.getLocalTranslation();
			} else {
				// on se deplace aléatoirement
				float dx = (float)random.nextFloat()*60f;
				float dz = (float)random.nextFloat()*60f;
				// System.out.println("dx, dy : " + dx + "," + dz);
				dx=30f-dx;
				dz=30f-dz;
				// System.out.println("dx, dy : " + dx + "," + dz);
				destination=new Vector3f(pos.x+dx,0f,pos.z+dz);
				// System.out.println("nouvelle destination : " + destination);
			}
		}
	}

}
