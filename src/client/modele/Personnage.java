package client.modele;

import modele.objet.Arme;
import test.SoundNode;
import util.loader.ArmeLoader;
import util.loader.PersoLoader;

import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jmex.model.animation.JointController;
import com.jmex.model.animation.KeyframeController;

/**
 * Classe permettant de créer un personnage en y appliquant son modèle 3d
 */
public class Personnage extends modele.personne.Personnage {
	
	private static final long serialVersionUID = 1L;
	
	private JointController kc;
	
//	private SoundNode soundNode; 
	
	/**
	 * Constructeur du personnage
	 * @param monde Monde dans lequel le peronnage va s'attacher
	 * @param perso Eléments communs au serveur et au client, définis par les données reçues du serveur
	 */
	public Personnage(Monde monde, modele.personne.Personnage perso) {
		super(perso);
		System.out.print("Création du personnage : "+ getNom() + " de type :" + getRang().getNom());
		String type = getRang().getNom();
		PersoLoader.loadModel(this, type);
		//TODO Texture statique. A définir dynamiquement
		PersoLoader.loadTexture(this, monde.getDisplay(), type);
		this.setLocalScale(perso.getLocalScale());
		//applyFrameController(type);
		System.out.println(" effectuée !");
		monde.getRootNode().attachChild(this);
		getArmeMesh().setIsCollidable(false);
		
		

        
//        soundNode.addController( new Controller() {
//        	
//			private static final long serialVersionUID = 1L;
//			Vector3f axis = new Vector3f( 0, 1, 0 );
//            Quaternion rot = new Quaternion();
//
//            public void update( float time ) {
//                rot.fromAngleNormalAxis( time/2, axis );
//                rot.multLocal( soundNode.getLocalTranslation() );
//            }
//        } );
	}
	
	public Personnage(boolean anim, Monde monde, modele.personne.Personnage perso) {
		this(monde, perso);
		if(anim) {
			String type = getRang().getNom();
			applyFrameController(type);
		}
	}
	
	
	public Personnage(Monde monde, modele.personne.Personnage perso, boolean principal) {
		this(monde, perso);
		if(principal) {
			String type = getRang().getNom();
			applyFrameController(type);
//			soundNode = SoundNode.create( "sound" );
//			soundNode.setSampleAddress( "data/sound/FootstepsMono.wav" );
//			soundNode.setLoop( true );
//        //	soundNode.play();
//			monde.getRootNode().attachChild( soundNode );
//			soundNode.attachChild(this);
		}
	}
	
	public Personnage(Monde monde, String type, String nom) {
		super();
		PersoLoader.loadModel(this, type);
		PersoLoader.loadTexture(this, monde.getDisplay(), type);
		if(type.substring(0,4).equals("nain")) {
			applyFrameController(type);
		}
		//monde.getRootNode().attachChild(this);
		try{
			getArmeMesh().setIsCollidable(false);
		}catch(NullPointerException e){}
	}
	
	/**
	 * Définit les séquences d'animation assignées aux actions
	 * @param type
	 */
	private void applyFrameController(String type){
		
		try{
			kc = (JointController) ((Node)this.getChild(0)).getController(0);
			//kc = (JointController) ((Node)this).getController(0);
	        kc.setSpeed(1);
	        kc.setTimes(206,250);
		}catch(IndexOutOfBoundsException e){
        	System.err.println(getNom()+"."+getName()+" : Problème !");
        	e.printStackTrace();
        }

        KeyBindingManager.getKeyBindingManager().set("marcher1", KeyInput.KEY_UP);
        KeyBindingManager.getKeyBindingManager().set("marcher2", KeyInput.KEY_LEFT);
        KeyBindingManager.getKeyBindingManager().set("marcher3", KeyInput.KEY_RIGHT);
        KeyBindingManager.getKeyBindingManager().set("marcher4", KeyInput.KEY_DOWN);
        int[] keyCodes1 = {KeyInput.KEY_UP, KeyInput.KEY_LSHIFT};
        KeyBindingManager.getKeyBindingManager().set("courir1", keyCodes1);
        int[] keyCodes2 = {KeyInput.KEY_LEFT, KeyInput.KEY_LSHIFT};
        KeyBindingManager.getKeyBindingManager().set("courir1", keyCodes2);
        int[] keyCodes3 = {KeyInput.KEY_RIGHT, KeyInput.KEY_LSHIFT};
        KeyBindingManager.getKeyBindingManager().set("courir1", keyCodes3);
        int[] keyCodes4 = {KeyInput.KEY_DOWN, KeyInput.KEY_LSHIFT};
        KeyBindingManager.getKeyBindingManager().set("courir1", keyCodes4);
        KeyBindingManager.getKeyBindingManager().set("mourir", KeyInput.KEY_DELETE);
        KeyBindingManager.getKeyBindingManager().set("sauter", KeyInput.KEY_SPACE);
        KeyBindingManager.getKeyBindingManager().set("attaquer", KeyInput.KEY_X);
	}
	
	/**
	 * Récupère le TriMesh correspondant au modèle.
	 * @return
	 */
	public TriMesh getTriMesh(){
		Node node = getNodeBeforeMeshes();
		if(node!=null)
			return (TriMesh)node.getChild(0);
		else
			return null;
	}
	
	/**
	 * Renvoit le node directement parents des TriMeshes qui composent le personnage
	 * @return
	 */
	public Node getNodeBeforeMeshes(){
		Spatial spatial = this;
		while(((Node)spatial).getChildren().size()>0  &&  !TriMesh.class.isInstance(((Node)spatial).getChild(0))){
			spatial = ((Node)spatial).getChild(0);
		}
		if(((Node)spatial).getChildren().size()>0 && TriMesh.class.isInstance(((Node)spatial).getChild(0))){
			return (Node)spatial;
		}else{
			System.exit(-1);
			return null;
		}
	}
	
	/**
	 * Ressuscite le personnage uniquement s'il est mort
	 *
	 */
	public void ressusciter(){
		if(!isVivant()){
			augmenterVie(20);
			// Position du vivant
			for(int i=0; i<getNodeBeforeMeshes().getChildren().size(); i++)
				getNodeBeforeMeshes().rotateUpTo(new Vector3f(+1,0,0));
		}// Sinon déjà vivant donc pas besoin
	}
	
	/**
	 * Tue le personnage uniquement s'il est vivant
	 *
	 */
	public void tuer(){
		if(isVivant()){ // Si vivant, on le tue
			décrémenterVie(1);
			//kc.setRepeatType(KeyframeController.RT_CLAMP);
            //kc.setTimes(184,205);
			// Position du mort
			if(!isVivant())
				getNodeBeforeMeshes().rotateUpTo(new Vector3f(-1,0,0));
		}// Sinon déjà mort, donc pas besoin
	}
	
	private boolean animated = false;
	
	/**
	 * Met à jour l'animation du personnage suivant l'action en cours
	 *
	 */
	public void updateAnimations(){
		
		//System.out.println("type : " + this.getRang().getNom().substring(0,4));
		if(!animated && this.getRang().getNom().substring(0,4).equals("nain")){
			animated = true;
            kc.setRepeatType(KeyframeController.RT_CYCLE);
            kc.setSpeed(0.8f);
            kc.setTimes(327,360);
        	etat = ETAT_FLANER;
		}else if(!this.getRang().getNom().substring(0,4).equals("nain")){ // Personnage principal
			KeyBindingManager key = KeyBindingManager.getKeyBindingManager();
	        if (key.isValidCommand("marcher1", false)
	        		|| key.isValidCommand("marcher2", false)
	        		|| key.isValidCommand("marcher3", false)
	        		|| key.isValidCommand("marcher4", false)) {
	            kc.setRepeatType(KeyframeController.RT_CYCLE);
	            kc.setSpeed(1);
	        	kc.setTimes(15,30);
	        	System.out.println(">> Action marcher");
	        	etat = ETAT_MARCHER;
//	        	this.soundNode.play();
	        }
	        if (key.isValidCommand("marcher1", true)
	        		|| key.isValidCommand("marcher2", true)
	        		|| key.isValidCommand("marcher3", true)
	        		|| key.isValidCommand("marcher4", true)) {
	        	etat = ETAT_MARCHER;
	        }
	        if (key.isValidCommand("courir1", false)
	        		|| key.isValidCommand("courir2", false)
	        		|| key.isValidCommand("courir3", false)
	        		|| key.isValidCommand("courir4", false)) {
	            kc.setRepeatType(KeyframeController.RT_CYCLE);
	            kc.setSpeed(2);
	            kc.setTimes(1,14);
	            System.out.println(">> Action courir");
	        	etat = ETAT_COURIR;
//	        	this.soundNode.play();
	        }
	        if (key.isValidCommand("courir1", true)
	        		|| key.isValidCommand("courir2", true)
	        		|| key.isValidCommand("courir3", true)
	        		|| key.isValidCommand("courir4", true)) {
	        	etat = ETAT_COURIR;
	        }
	        // Gestion de l'arrêt du son des pas == Passage à l'état de flâneur
	        if(etat!=ETAT_MARCHER && etat!=ETAT_COURIR){// && soundNode.isPlaying()){
//	        	soundNode.stop();
	            kc.setSpeed(1);
	            double val = Math.random();
	            if(val>0.66)
	            	kc.setTimes(184,205);
	            else if(val>0.33)
	            	kc.setTimes(206,250);
	            else
	            	kc.setTimes(251,300);
	        }
	        if((etat==ETAT_MARCHER || etat==ETAT_COURIR) && !
	        		(key.isValidCommand("courir1", false)
	        		|| key.isValidCommand("courir2", false)
	        		|| key.isValidCommand("courir3", false)
	        		|| key.isValidCommand("courir4", false)
	        		|| key.isValidCommand("marcher1", false)
	        		|| key.isValidCommand("marcher2", false)
	        		|| key.isValidCommand("marcher3", false)
	        		|| key.isValidCommand("marcher4", false) )) {
	        	etat = ETAT_FLANER;
	        }
	        if (key.isValidCommand("sauter", false)) {
	            kc.setRepeatType(KeyframeController.RT_CYCLE);
	            kc.setTimes(103,111);
	            System.out.println(">> Action sauter");
	        	etat = ETAT_SAUTER;
	        }
	        if (key.isValidCommand("mourir", false)) {
	            kc.setRepeatType(KeyframeController.RT_WRAP);
	            kc.setTimes(184,205);
	            tuer();
	            System.out.println(">> Action mourir");
	        	etat = ETAT_MOURIR;
	        }
	        if (key.isValidCommand("attaquer", false)) {
	            kc.setRepeatType(KeyframeController.RT_CYCLE);
	            kc.setTimes(134,145);
	            System.out.println(">> Action attaquer");
	        	etat = ETAT_ATTAQUER;
	        }
		}
	}
	
	/**
	 * Donne l'arme correpondante à la main du personnage
	 * @param arme
	 */
	public void setArme(Arme arme){
		// On enlève l'arme précédente si il y en avait une
		removeArme();
		// On ajoute la nouvelle
		String nom = arme.getNom();
		Node arme_node = new Node(nom);
		ArmeLoader.loadModel(arme_node, nom);
		getNodeBeforeMeshes().attachChild(arme_node);
	}
	
	/**
	 * @return L'arme portée par le personnage
	 */
	public Arme getArme(){
		return null;
	}
	
	/**
	 * Enlève l'arme portée par le personnage
	 *
	 */
	public void removeArme(){
		TriMesh armeMesh = getArmeMesh();
		if(armeMesh != null)
			armeMesh.removeFromParent();
	}
	
	/**
	 * @return TriMesh de l'arme portée par le personnage
	 */
	private TriMesh getArmeMesh(){
		Node node = getNodeBeforeMeshes();
		if(node!=null)
			try{
				return (TriMesh)node.getChild(1);
			}catch(IndexOutOfBoundsException e){}
		return null;
	}
	
}
