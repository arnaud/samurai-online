/*
 * Created on 14 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.modele;

import util.loader.DecoLoader;

/**
 * Classe permettant de créer un objet de décoration en y appliquant son modèle 3d
 */
public class ObjetDecoratif extends modele.objet.ObjetDecoratif {
	
	private static final long serialVersionUID = 1L;
	
	public ObjetDecoratif(String nom) {
		super(nom);
		//this.lock();
	}
	
	public ObjetDecoratif(Monde monde, String type, String nom) {
		this(nom);
		DecoLoader.loadModel(this, type);
		DecoLoader.loadTexture(this, monde.getDisplay(), type);
		//monde.getRootNode().attachChild(this);
	}
	
	public ObjetDecoratif(modele.objet.ObjetDecoratif objet) {
		this(objet.getNom());
		this.id = objet.getId();
		this.setPosition(objet.getPosition());
		this.setDirection(objet.getDirection());
		this.setLocalRotation(objet.getLocalRotation());
		this.setLocalScale(objet.getLocalScale());
		this.setCaracteristiques(objet.getCaracteristiques());
	}
	
	public ObjetDecoratif(Monde monde, modele.objet.ObjetDecoratif deco) {
		this(deco);
		System.out.print("Création de la deco : "+ getNom());
		String type = getNom();
		DecoLoader.loadModel(this, type);
		DecoLoader.loadTexture(this, monde.getDisplay(), type);
		System.out.println(" effectuée !");
		//monde.getRootNode().attachChild(this);
	}
	
}
