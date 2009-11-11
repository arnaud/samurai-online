/*
 * Created on 14 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.modele;

import util.loader.BatiLoader;

/**
 * Classe permettant de créer un bâtiment en y appliquant son modèle 3d
 */
public class Batiment extends modele.objet.Batiment {
	
	private static final long serialVersionUID = 1L;
	
	public Batiment(String nom) {
		super(nom);
		//this.lock();
	}
	
	public Batiment(Monde monde, String type, String nom) {
		this(nom);
		BatiLoader.loadModel(this, type);
		BatiLoader.loadTexture(this, monde.getDisplay(), type);
		//monde.getRootNode().attachChild(this);
	}
	
	public Batiment(modele.objet.Batiment bati) {
		this(bati.getNom());
		this.id = bati.getId();
		this.setPosition(bati.getPosition());
		this.setDirection(bati.getDirection());
		this.setLocalRotation(bati.getLocalRotation());
		this.setLocalScale(bati.getLocalScale());
		this.setCaracteristiques(bati.getCaracteristiques());
	}
	
	public Batiment(Monde monde, modele.objet.Batiment bati) {
		this(bati);
		System.out.print("Création du batiment : "+ getNom());
		String type = getNom();
		BatiLoader.loadModel(this, type);
		
		BatiLoader.loadTexture(this, monde.getDisplay(), type);
		System.out.println(" effectuée !");
		//monde.getRootNode().attachChild(this);
	}
	

	
}
