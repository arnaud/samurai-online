package client.controlleur;

import client.modele.Monde;

import com.jme.input.ChaseCamera;

/**
 * Caméra de suivi (3ème personne) attachée au personnage joueur
 * @author Arnaud
 *
 */
public class CameraSuivi extends ChaseCamera {
	
	private boolean isAttached;

	/**
	 * Constructeur de la caméra, attachement réalisé au personnage dans le monde
	 * @param monde
	 */
	public CameraSuivi(Monde monde) {
        super(monde.getCam(), monde.getPersonnage());
        setAttachment(true);
	}
	
	/**
	 * Définit si la caméra doit rester attachée au personnage
	 * @param attach
	 */
	public void setAttachment(boolean attach){
		isAttached = attach;
		if(isAttached){
			this.setEnableSpring(true);
			this.setEnabledOfAttachedHandlers(true);
			this.setEnabled(true);
		} else {
			this.setEnableSpring(false);
			this.setEnabledOfAttachedHandlers(false);
			this.setEnabled(false);
		}
	}
}
