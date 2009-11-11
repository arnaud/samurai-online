/*
 * Created on 14 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.modele;

import util.loader.OutilLoader;

/**
 * Classe permettant de créer un objet de décoration en y appliquant son modèle 3d
 */
public class Outil extends modele.objet.Outil {
	
	private static final long serialVersionUID = 1L;
	
	public Outil(String nom) {
		super(nom);
		//this.lock();
	}
	
	public Outil(Monde monde, String type, String nom) {
		this(nom);
		OutilLoader.loadModel(this, type);
		OutilLoader.loadTexture(this, monde.getDisplay(), type);
		//monde.getRootNode().attachChild(this);
	}
	
	public Outil(modele.objet.Outil outil) {
		this(outil.getNom());
		this.id = outil.getId();
		this.setPosition(outil.getPosition());
		this.setDirection(outil.getDirection());
		this.setLocalRotation(outil.getLocalRotation());
		this.setLocalScale(outil.getLocalScale());
		this.setCaracteristiques(outil.getCaracteristiques());
	}
	
	public Outil(Monde monde, modele.objet.Outil outil) {
		this(outil);
		System.out.print("Création de l'outil : "+ getNom());
		String type = getNom();
		OutilLoader.loadModel(this, type);
		OutilLoader.loadTexture(this, monde.getDisplay(), type);
		System.out.println(" effectuée !");
		//monde.getRootNode().attachChild(this);
	}
	
}
