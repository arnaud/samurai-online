package client.controlleur;

import java.util.HashMap;

import client.modele.Carte;
import client.modele.Monde;
import client.modele.Personnage;

import com.jme.curve.BezierCurve;
import com.jme.curve.Curve;
import com.jme.curve.CurveController;
import com.jme.input.KeyInput;
import com.jme.input.ThirdPersonHandler;
import com.jme.intersection.CollisionResults;
import com.jme.intersection.TriangleCollisionResults;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import communication.CommClient;

/**
 * Classe de définition des contrôles du personnage joueur
 * @author Arnaud
 *
 */
public class HandlerPerso extends ThirdPersonHandler {
//	public static final String PROP_KEY_JUMP = "jump";
//	protected boolean jump;
	
	private Monde monde;
	private Personnage m_character;
	private Carte carte;
	private CommClient commClient;
	
	private CollisionResults results = new TriangleCollisionResults();
	private boolean isJumping = false;
	private CurveController controleur;
	
	/**
	 * Constructeur par défaut
	 * @param monde
	 * @param m_character
	 * @param carte
	 * @param comm
	 */
	public HandlerPerso(Monde monde, Personnage m_character, Carte carte, CommClient comm) {
		super(m_character, monde.getCam(), new DefaultConfig("controle-perso"));
        setActionSpeed(Personnage.WALK_SPEED);
        this.monde = monde;
        this.m_character = m_character;
        this.carte = carte;
        this.commClient = comm;
	}
	
	/**
	 * Gère l'avancement du personnage.
	 * Gère les collisions.
	 */
	public void update(float arg0) {
		KeyInput key = KeyInput.get();
		
		//TODO: améliorer la gestion des interactions
		if (key.isKeyDown(KeyInput.KEY_LCONTROL))
			commClient.creerInteraction (this.m_character);
		
		//TODO: gestion de la rotation pour les collisions
		prevRot.set (m_character.getLocalRotation());
		
		//TODO: Créer un handler spécifique au saut
		if (key.isKeyDown(KeyInput.KEY_SPACE) && !isJumping) {
			isJumping = true;
			Vector3f translation;
			if (walkingForward || walkingBackwards || nowTurning || nowStrafing) {
				translation = m_character.getLocalRotation().getRotationColumn(0);
				//TODO: valeur numérique : 100
				translation.normalizeLocal().multLocal(100 * speed * arg0);
			} else translation = new Vector3f (0,0,0);
			
			//TODO: valeur numérique : 1.5F
			Vector3f destFinal = m_character.getLocalTranslation().add(translation.mult(1.5F));
			destFinal.y = -1F;
			Vector3f[] v = {new Vector3f(m_character.getLocalTranslation()),
					//TODO: valeur numérique : 1.5F
					m_character.getLocalTranslation().add(translation.divide(2F).add(0,1.5F*speed,0)),
					m_character.getLocalTranslation().add(translation),
					destFinal};
			Curve curve = new BezierCurve ("courbe", v);
			controleur = new CurveController (curve, m_character);
		}

		Node rootNode = monde.getRootNode();
		
		if (!isJumping) {
			// Déplacement normal
			super.update(arg0);
	        
			// Attachement à la carte
			float characterMinHeight = carte.getHeight(m_character.getLocalTranslation());
	        if (!Float.isInfinite(characterMinHeight) && !Float.isNaN(characterMinHeight))
	        	m_character.getLocalTranslation().y = characterMinHeight;
	        
			// Gestion des collisions
			results.clear();
			this.m_character.getTriMesh().calculateCollisions (rootNode, results);
			if (results.getNumber() > 1)
				//if (!results.getCollisionData(0).getTargetMesh().getName().equals("blade"))
					m_character.getLocalTranslation().set(prevLoc);
			//TODO: petit rapport (pour que tu soit au courant) : le perso "marche",
			//les collisions avec les batiments ne sont plus gérés mais avec les autres persos si,
			//il y a un gros problème d'orientation (ce qui pourri le saut je pense).
		} else {
			// Saut
			prevLoc.set(m_character.getLocalTranslation());
			controleur.update(arg0);
			
			// Detection de la fin du saut par la carte
			float characterMinHeight = carte.getHeight(m_character.getLocalTranslation());
			if (m_character.getLocalTranslation().y < characterMinHeight)
				isJumping = false;
			
			// Detection de la fin du saut par une collision
			results.clear();
			this.m_character.calculateCollisions (rootNode, results);
			if (results.getNumber() > 0) {
				// Nouvelle courbe rebondissante (mais perte de vitesse
				Vector3f translation = m_character.getLocalRotation().getRotationColumn(0);
				//TODO: valeur numérique : 50
				translation.negateLocal().normalizeLocal().multLocal(50 * speed * arg0);
				Vector3f destFinal = m_character.getLocalTranslation().add(translation.mult(2F));
				destFinal.y = 0;
				Vector3f[] v = {new Vector3f(m_character.getLocalTranslation()),
						m_character.getLocalTranslation().add(translation),
						destFinal};
				Curve curve = new BezierCurve ("courbe", v);
				controleur = new CurveController (curve, m_character);
			}
		}
		
		// Mise à jour des positions sur le serveur
		if (!m_character.getLocalTranslation().equals(prevLoc)){
			commClient.deplacerPersonnage(this.m_character);
		}
		if (!m_character.getLocalRotation().equals(prevRot)){
			commClient.orienterPersonnage(this.m_character);
		}
	}
	
	/**
	 * Configuration par défaut du clavier et de la souris
	 * @author Arnaud
	 *
	 */
	private static class DefaultConfig extends HashMap {
		
		private static final long serialVersionUID = 1L;

		public DefaultConfig(String type){
			super();
			if(type.equals("controle-perso")){
		        put(HandlerPerso.PROP_DOGRADUAL, "true");
		        put(HandlerPerso.PROP_TURNSPEED, ""+(1.0f * FastMath.PI));
		        put(HandlerPerso.PROP_LOCKBACKWARDS, "false");
		        put(HandlerPerso.PROP_CAMERAALIGNEDMOVE, "true");
		        put(HandlerPerso.PROP_KEY_LEFT, ""+KeyInput.KEY_LEFT);
		        put(HandlerPerso.PROP_KEY_RIGHT, ""+KeyInput.KEY_RIGHT);
		        put(HandlerPerso.PROP_KEY_FORWARD, ""+KeyInput.KEY_UP);
		        put(HandlerPerso.PROP_KEY_BACKWARD, ""+KeyInput.KEY_DOWN);
//		        put(ConfigClavier.PROP_KEY_JUMP, ""+KeyInput.KEY_SPACE);
		        put(HandlerPerso.PROP_KEY_STRAFELEFT, ""+KeyInput.KEY_UNLABELED);
		        put(HandlerPerso.PROP_KEY_STRAFERIGHT, ""+KeyInput.KEY_UNLABELED);
			}else if(type.equals("controle-interface")){
		        put(HandlerPerso.PROP_KEY_LEFT, ""+KeyInput.KEY_UNLABELED);
		        put(HandlerPerso.PROP_KEY_RIGHT, ""+KeyInput.KEY_UNLABELED);
		        put(HandlerPerso.PROP_KEY_FORWARD, ""+KeyInput.KEY_UNLABELED);
		        put(HandlerPerso.PROP_KEY_BACKWARD, ""+KeyInput.KEY_UNLABELED);
		        put(HandlerPerso.PROP_KEY_STRAFELEFT, ""+KeyInput.KEY_UNLABELED);
		        put(HandlerPerso.PROP_KEY_STRAFERIGHT, ""+KeyInput.KEY_UNLABELED);
//		        put(ConfigClavier.PROP_KEY_JUMP, ""+KeyInput.KEY_UNLABELED);
		        put(HandlerPerso.PROP_CAMERAALIGNEDMOVE, "true");
			}
		}
	}
	
	public void setModeControlePersonnage(boolean isControlled){
		if(isControlled){
			//this.setActionSpeed(getWalkSpeed());
			//this.setTurning(true);
			//setEnabled(true);
			this.updateKeyBindings(new DefaultConfig("controle-perso"));
		}else{
			//this.setActionSpeed(0);
			//setEnabled(false);
			this.updateKeyBindings(new DefaultConfig("controle-interface"));
			//this.setTurning(false);
		}
	}
	
//    public void setJump(boolean b) {
//        jump = b;
//    }
//
//    public boolean isJump() {
//        return jump;
//    }
}
