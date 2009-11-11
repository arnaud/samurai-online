/*
 * Created on 14 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.modele;

import util.loader.ArmeLoader;

/**
 * Classe permettant de créer un objet de décoration en y appliquant son modèle 3d
 */
public class Arme extends modele.objet.Arme {
	
	private static final long serialVersionUID = 1L;
	
	public Arme(String nom) {
		super(nom);
		//this.lock();
	}
	
	public Arme(Monde monde, String type, String nom) {
		this(nom);
		ArmeLoader.loadModel(this, type);
		ArmeLoader.loadTexture(this, monde.getDisplay(), type);
		//monde.getRootNode().attachChild(this);
	}
	
	public Arme(modele.objet.Arme arme) {
		this(arme.getNom());
		this.id = arme.getId();
		this.setPosition(arme.getPosition());
		this.setDirection(arme.getDirection());
		this.setLocalRotation(arme.getLocalRotation());
		this.setLocalScale(arme.getLocalScale());
		this.setCaracteristiques(arme.getCaracteristiques());
	}
	
	public Arme(Monde monde, modele.objet.Arme arme) {
		this(arme);
		System.out.print("Création de l'arme : "+ getNom());
		String type = getNom();
		ArmeLoader.loadModel(this, type);
		ArmeLoader.loadTexture(this, monde.getDisplay(), type);
		System.out.println(" effectuée !");
		//monde.getRootNode().attachChild(this);
	}
	
}
