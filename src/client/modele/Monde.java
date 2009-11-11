package client.modele;

import java.util.Vector;

import modele.action.Interaction;
import modele.objet.Arme;
import modele.objet.Outil;
import client.Client;
import client.controlleur.CameraSuivi;
import client.controlleur.HandlerPerso;
import client.controlleur.MousePick;
import client.gui.GuiPrincipal;
import client.gui.fenetre.Fenetre;
import client.gui.fenetre.contenu.AbsContent;
import client.gui.fenetre.contenu.ContConsole;
import client.gui.fenetre.contenu.ContDebug;
import client.gui.fenetre.contenu.carte.ContMiniMap;

import com.jme.app.AbstractGame;
import com.jme.app.SimpleGame;
import com.jme.input.InputHandler;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Geometry;
import com.jme.scene.Node;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;
import communication.CommClient;

public class Monde extends SimpleGame {
	
	public static AbstractGame instance;
	private final String GAME_TITLE = "Samurai Online - Application cliente";
	private CommClient commClient;

    private Personnage personnage;
    private Vector alentoursPersos;
    public Vector alentoursBatiments;
    public Vector alentoursDeco;
    public Vector alentoursArmes;
    public Vector alentoursOutils;
    private CameraSuivi chaser;
    private Carte carte;
    private Mer mer;
    private Ciel ciel;
    private GuiPrincipal gui;
    private HandlerPerso cfg_clavier;
    private Geometry objetSelec;
    
    private Client client;
    
    private Vector interaction = new Vector();

    public Monde(Client client, CommClient comm){
    	super();
    	this.client = client;
    	this.commClient = comm;
    }
    
    protected void simpleInitGame() {
    	
    	instance = this;
        display.setTitle(GAME_TITLE);

        // Initialisations statiques
        initCarte();
        initPersonnage();
        initAlentoursPersos();
        initAlentoursBatiments();
        initAlentoursDeco();
        initAlentoursArmes();
        initAlentoursOutils();

        // Demande d'initialisation
        commClient.askInit();
    	long start = System.currentTimeMillis();
    	System.out.println("start : " + start);
    	while(System.currentTimeMillis()<start+2000L && !commClient.init_finished) {
    		System.out.println("current:"+System.currentTimeMillis());
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	System.out.println("NB DECO : " + alentoursDeco.size());
        initCiel();
        initMer();
        initCamera();
        initConfigClavier();
        initBrouillard();
        initGUI();
        initKeys();
        //initSound();
		input.addAction(new MousePick(rootNode, this));
		
        rootNode.detachChildNamed("fpsNode");
     

        // Nécessaire > initialise l'affichage du gui
    	gui.refresh();
    }
    
    /**
     * Initialise le personnage joueur et réalise l'attachement à la scène
     *
     */
    private void initPersonnage(){
    	personnage = new Personnage(this, client.perso, true);
    	// Placement du personnage sur la carte
        personnage.getLocalTranslation().y = carte.getHeight(personnage.getLocalTranslation());
        // Orientation du personnage dans la bonne direction
		Quaternion quat = new Quaternion();
		quat.fromAngleNormalAxis(FastMath.DEG_TO_RAD*90, new Vector3f(0,-1,0));
		personnage.getNodeBeforeMeshes().setLocalRotation(quat);
		//personnage.removeArme();
    }
    
    /**
     * Initialise les personnages alentours du personnage joueur et réalise les attachements au rootNode
     *
     */
    private void initAlentoursPersos(){
    	alentoursPersos = new Vector();
    	new Personnage(this, "commercant", "Commercant");
    	new Personnage(this, "monstre", "Monstre");
    	new Personnage(this, "nain1", "Nain");
    	new Personnage(this, "nain2", "Nain");
    	new Personnage(this, "chevre", "Chevre");
    }

    /**
     * Initialise les bâtiments alentours du personnage joueur et réalise les attachements au rootNode
     *
     */
    private void initAlentoursBatiments(){
    	alentoursBatiments = new Vector();
    	
    	// TODO : bug à corriger (chargement à vide nécessaire)
    	new Batiment(this, "seirou", "Tour de garde");
    	new Batiment(this, "store", "Store de commerce");
    	new Batiment(this, "minka", "Minka");
    	new Batiment(this, "kura", "Kura");
    }
    
    /**
     * Initialise les objets de deco alentours du personnage joueur et réalise les attachements au rootNode
     *
     */
    private void initAlentoursDeco(){
    	alentoursDeco = new Vector();
    	
    	// TODO : bug à corriger (chargement à vide nécessaire)
    	new ObjetDecoratif(this, "pont", "Pont");
    	new ObjetDecoratif(this, "porte", "porte");
    	new ObjetDecoratif(this, "lanterne", "lanterne");
    	new ObjetDecoratif(this, "tree", "Arbre");
    }
    
    /**
     * Initialise les objets de deco alentours du personnage joueur et réalise les attachements au rootNode
     *
     */
    private void initAlentoursArmes(){
    	alentoursArmes = new Vector();
    	
    	// TODO : bug à corriger (chargement à vide nécessaire)
    	new client.modele.Arme(this, "katana", "Katana");
    	new client.modele.Arme(this, "kabuto", "Casque");
    }
    
    /**
     * Initialise les objets de deco alentours du personnage joueur et réalise les attachements au rootNode
     *
     */
    private void initAlentoursOutils(){
    	alentoursOutils = new Vector();
    	
    	// TODO : bug à corriger (chargement à vide nécessaire)
    	new client.modele.Outil(this, "charette", "Charette");
    }


    /**
     * Initialise le ciel et réalise l'attachement au rootNode
     *
     */
    private void initCiel(){
    	ciel = new Ciel(this);
    }

    /**
     * Initialise la carte du terrain et réalise l'attachement au rootNode
     *
     */
    private void initCarte(){
    	carte = new Carte(this);
    }

    /**
     * Initialise la mer et réalise l'attachement au rootNode
     *
     */
    private void initMer(){
    	mer = new Mer(this);
    	mer.setScale(1200);
    	mer.setHauteur(45);
    	// trop tôt
    	//mer.makeVagues();
    }

    /**
     * Initialise la caméra de poursuite et réalise l'attachements au rootNode
     *
     */
    private void initCamera(){
    	chaser = new CameraSuivi(this);
    }

    /**
     * Initialise la configuration du clavier
     *
     */
    private void initConfigClavier(){
    	cfg_clavier = new HandlerPerso(this, personnage, carte, commClient);
    	input = cfg_clavier;
    }

    /**
     * Initialise le brouillard et réalise l'attachement au rootNode
     *
     */
    private void initBrouillard(){
    	new Brouillard(this);
    }
    
    private void initGUI(){
    	gui = new GuiPrincipal(this, commClient);
    }
    
    private void initKeys(){
    	
    	/*** On enlève toute la floppée d'assignations de touches faites pas le BaseSimpleGame ***/
    	
    	KeyBindingManager keyBind = KeyBindingManager.getKeyBindingManager();
    	keyBind.remove("toggle_pause");
    	//keyBind.remove("toggle_wire");
    	keyBind.remove("toggle_lights");
    	//keyBind.remove("toggle_bounds");
    	keyBind.remove("toggle_depth");
    	keyBind.remove("toggle_normals");
    	//keyBind.remove("camera_out");
    	keyBind.remove("screen_shot");
    	//keyBind.remove("parallel_projection");
    	keyBind.remove("exit");
    	
    	/*** On définit nos propres assignations ***/
    	
    	// CTRL+C = quitter
        //int[] keyCodes = {KeyInput.KEY_LCONTROL, KeyInput.KEY_C};
    	
    	// X = Goutte d'eau
        //KeyBindingManager.getKeyBindingManager().set("goutte-d-eau", KeyInput.KEY_X);
        
        // ESCAPE = commutation (contrôle de perso / contrôle de l'interface)
        KeyBindingManager.getKeyBindingManager().set("switch_gui", KeyInput.KEY_ESCAPE);
        KeyBindingManager.getKeyBindingManager().set("switch_inventaire", KeyInput.KEY_I);
        KeyBindingManager.getKeyBindingManager().set("switch_quetes", KeyInput.KEY_Q);
        KeyBindingManager.getKeyBindingManager().set("collecter_objets_alentours", KeyInput.KEY_C);
        KeyBindingManager.getKeyBindingManager().set("rafraichir_gui", KeyInput.KEY_R);
    }
    
 /*   private void initSound() {
    	URL rootUrl = getClass().getClassLoader().getResource("data/sound/test.ogg");
    	if (rootUrl == null) System.out.println(">>>>>>>>>NULL<<<<<<<<<<");
    	int background = SoundSystem.createStream(rootUrl.getFile(), true);
    	if(SoundSystem.isStreamOpened(background)) {
//    		int lgth = (int) SoundSystem.getStreamLength(background);
    		SoundSystem.playStream(background);
//            SoundSystem.setStreamVolume(background, 0);
    	} else
    		System.out.println ("Failed to open stream!");
    }*/
    
    private boolean modeCtrlPerso = true;
    private boolean vaguesDone = false;
    
    protected void simpleUpdate() {
    	
    	// Lecture du caractère de la touche enfoncée
        //KeyInput key = KeyInput.get();
        // Lecture des actions associées aux touches
        KeyBindingManager keyBind = KeyBindingManager.getKeyBindingManager();

        // Commutation entre mode contrôle de personnage et mode contrôle de l'interface utilisateur
        if (keyBind.isValidCommand("switch_gui", false)) {
        	modeCtrlPerso = !modeCtrlPerso;
        	((ContConsole)gui.getGuiDesktop().getFenetre("console").getContenu()).println(modeCtrlPerso?"Mode contrôle du personnage":"Mode contrôle de l'interface");
            this.chaser.setAttachment(modeCtrlPerso);
//            this.gui.getCurseur().setVisible(!modeCtrlPerso);
            cfg_clavier.setModeControlePersonnage(modeCtrlPerso);
        }
        if(keyBind.isValidCommand("switch_inventaire", false)) {
        	gui.switchInventaire();
        }
        if(keyBind.isValidCommand("switch_quetes", false)) {
        	gui.switchQuetes();
        }
        if(keyBind.isValidCommand("collecter_objets_alentours", false)) {
        	//TODO Gérer la collecte des objets alentours
        }
    	if (keyBind.isValidCommand("goutte-d-eau", false)) {
    		mer.makeGoutte(personnage.getPositionX(), personnage.getPositionY());
    	}
    	if (keyBind.isValidCommand("rafraichir_gui", false)) {
    		gui.refresh();
    	}
        // Gestion de la déconnexion rapide
        if (keyBind.isValidCommand("exit")) {
        	exit();
        }
        
        // Update du personnage principal (animations)
        for(int i=0; i<alentoursPersos.size(); i++) {
        	Personnage perso = (Personnage)alentoursPersos.get(i);
        	if(perso.getRang().getNom().substring(0,4).equals("nain")) {
    		  	perso.updateAnimations();
        	}
        }
        personnage.updateAnimations();
        
        // Gestion de la mise à jour du mode de jeu courant

        if(modeCtrlPerso) {
        	updateModeControlePersonnage();
        } else {
        	updateModeControleInterface();
        }
        
        
        if(System.currentTimeMillis() % 5 == 0){
        	// Mise à jour du gui
        	//gui.refresh();
        	// Coordonnées actuelles du personnage sur la carte
			Vector3f vectPos = personnage.getLocalTranslation();
			// Information des coordonnées dans la fenêtre Debug
			Fenetre debug = gui.getGuiDesktop().getFenetre("debug");
			if(debug!=null){
				AbsContent contenu = debug.getContenu();
				((ContDebug)contenu).setPositionX(vectPos.x);
				((ContDebug)contenu).setPositionY(vectPos.z);
				((ContDebug)contenu).setPositionZ(vectPos.y);
			}
	        // Mise à jour de la mini-carte
	        ContMiniMap.updatePosition(vectPos.x, vectPos.z, vectPos.y);
        }
        // Affichage du gui en premier plan
        display.getRenderer().draw( gui );
		if(!vaguesDone && timer.getTime()>20000){
			mer.makeVagues();
			vaguesDone = true;
		}
    }
    
    private void updateModeControlePersonnage(){
    	
    	// Lecture du caractère de la touche enfoncée
        KeyInput key = KeyInput.get();
        
        // Gestion de la rapidité de déplacement du personnage
        if(key.isKeyDown(KeyInput.KEY_LSHIFT) || key.isKeyDown(KeyInput.KEY_RSHIFT))
        	input.setActionSpeed(Personnage.RUN_SPEED);
        else if(key.isKeyDown(KeyInput.KEY_CAPITAL))
            input.setActionSpeed(Personnage.MAX_SPEED);
        else if(key.isKeyDown(KeyInput.KEY_TAB))
            input.setActionSpeed(10*Personnage.MAX_SPEED);
        else
        	input.setActionSpeed(Personnage.WALK_SPEED);
        //TODO Remplacer par la mobilité
        /*
        if(key.isKeyDown(KeyInput.KEY_LSHIFT) || key.isKeyDown(KeyInput.KEY_RSHIFT)) // courir
        	input.setActionSpeed(personnage.getMobilite().getValeur() * 1.5f);
        else if(key.isKeyDown(KeyInput.KEY_CAPITAL)) // courir très vite
            input.setActionSpeed(personnage.getMobilite().getValeur() * 4.0f);
        else if(key.isKeyDown(KeyInput.KEY_TAB)) // course tarée
            input.setActionSpeed(personnage.getMobilite().getValeurMax());
        else // marcher
        	input.setActionSpeed(personnage.getMobilite().getValeur());*/
    	
    	// Caméra
        chaser.update(tpf);
        float camMinHeight = carte.getHeight(cam.getLocation())+4;
        if (!Float.isInfinite(camMinHeight) && !Float.isNaN(camMinHeight)
                && cam.getLocation().y <= camMinHeight) {
            cam.getLocation().y = camMinHeight;
            cam.update();
        }
        //TODO: gérer la caméra qui ne va pas sous l'eau

        // Autres personnages
    	for(int i=0; i<alentoursPersos.size(); i++){
    		Personnage courant = (Personnage)alentoursPersos.get(i);
        
	        float characterMHeight = carte.getHeight(courant.getPosition());
	        if (!Float.isInfinite(characterMHeight) && !Float.isNaN(characterMHeight)) {
	        		courant.setHauteur(characterMHeight);
	        }
    	}
    	
    	// Bâtiments
    	for(int i=0; i<alentoursBatiments.size(); i++){
    		Batiment courant = (Batiment)alentoursBatiments.get(i);
        
	        float characterMHeight = carte.getHeight(courant.getPosition());
	        if (!Float.isInfinite(characterMHeight) && !Float.isNaN(characterMHeight)) {
	        	courant.setHauteur(characterMHeight);
	        }
    	}
    	
    	// Deco
    	for(int i=0; i<alentoursDeco.size(); i++){
    		ObjetDecoratif deco = (ObjetDecoratif)alentoursDeco.get(i);
        
	        float characterMHeight = carte.getHeight(deco.getPosition());
	        if (!Float.isInfinite(characterMHeight) && !Float.isNaN(characterMHeight)) {
	        	deco.setHauteur(characterMHeight);
	        }
    	}
    	
    	// Armes
    	for(int i=0; i<alentoursArmes.size(); i++){
    		Arme arme = (Arme)alentoursArmes.get(i);
        
	        float characterMHeight = carte.getHeight(arme.getPosition());
	        if (!Float.isInfinite(characterMHeight) && !Float.isNaN(characterMHeight)) {
	        	arme.setHauteur(characterMHeight);
	        }
    	}
    	
    	// Outils
    	for(int i=0; i<alentoursOutils.size(); i++){
    		Outil outil = (Outil)alentoursOutils.get(i);
        
	        float characterMHeight = carte.getHeight(outil.getPosition());
	        if (!Float.isInfinite(characterMHeight) && !Float.isNaN(characterMHeight)) {
	        	outil.setHauteur(characterMHeight);
	        }
    	}
        
        // Ciel toujours fixe par rapport à la caméra
    	ciel.setLocalTranslation(cam.getLocation());
        
        // Gestion de la mort !!!
        if (personnage.getLocalTranslation().y + 10F < mer.getHauteur()
        		//&& personnage.getLocalTranslation().y!=0f
        		)
        	personnage.tuer();
    }
    
    //boolean pwet = false;
    
    private void updateModeControleInterface(){
    	// Mise à jour du gui
        //if(System.currentTimeMillis() % 100 == 0)
        //	gui.refresh();
    	GuiPrincipal.inventaire.refresh();
    }
    
	public static void exit() {
		instance.finish();
	}
	
	//--- Début des ACCESSEURS ---
	
	public DisplaySystem getDisplay(){
		return display;
	}
	
	public Node getRootNode(){
		return rootNode;
	}
	
	public Camera getCam(){
		return cam;
	}
	
	public InputHandler getInput(){
		return input;
	}
	
	public Node getFpsNode(){
		return fpsNode;
	}
	
	public LightState getLightState(){
		return lightState;
	}
	
	public Personnage getPersonnage(){
		return personnage;
	}
	
	public void setPersonnage(Personnage personnage){
		this.personnage = personnage;
		if(!rootNode.hasChild(personnage))
			rootNode.attachChild(personnage);
	}
	
	public Node getCarte(){
		return carte;
	}
	
	public void addAlentourPerso(Personnage perso) {
		System.err.println("on attache un nouveau perso au monde : " + perso.getNom());
		perso.setDirection(new Vector3f(-1, 1, 3));
		System.err.println("Position :" + perso.getPosition());
		perso.setPositionX(perso.getPositionX());
		perso.setPositionY(perso.getPositionY());
		alentoursPersos.add(perso);
		rootNode.attachChild(perso);
		rootNode.updateRenderState();
	}
	
	public void addInteraction (Interaction inter) {
		System.err.println("on attache une nouvelle inter au monde : " + inter.getId());
		rootNode.attachChild(inter);
		interaction.add(inter);
	}
	
	public void addAlentourDeco(ObjetDecoratif deco) {
		System.err.println("on attache une nouvelle deco au monde : " + deco.getNom());
		//perso.setDirection(new Vector3f(-1, 1, 3));
		//System.err.println("Position :" + perso.getPosition());
		deco.setPositionX(deco.getPositionX());
		deco.setPositionY(deco.getPositionY());
		alentoursDeco.add(deco);
		rootNode.attachChild(deco);
		rootNode.updateRenderState();
	}
	
	public void addAlentourBati(Batiment bati) {
		System.err.println("on attache un nouveau batiment au monde : " + bati.getNom());
		//perso.setDirection(new Vector3f(-1, 1, 3));
		//System.err.println("Position :" + perso.getPosition());
		bati.setPositionX(bati.getPositionX());
		bati.setPositionY(bati.getPositionY());
		alentoursBatiments.add(bati);
		rootNode.attachChild(bati);
		rootNode.updateRenderState();
	}
	
	public void addAlentourArme(Arme arme) {
		System.err.println("on attache un nouveau batiment au monde : " + arme.getNom());
		//perso.setDirection(new Vector3f(-1, 1, 3));
		//System.err.println("Position :" + perso.getPosition());
		arme.setPositionX(arme.getPositionX());
		arme.setPositionY(arme.getPositionY());
		alentoursArmes.add(arme);
		rootNode.attachChild(arme);
		rootNode.updateRenderState();
	}
	
	public void addAlentourOutil(Outil outil) {
		System.err.println("on attache un nouveau batiment au monde : " + outil.getNom());
		//perso.setDirection(new Vector3f(-1, 1, 3));
		//System.err.println("Position :" + perso.getPosition());
		outil.setPositionX(outil.getPositionX());
		outil.setPositionY(outil.getPositionY());
		alentoursOutils.add(outil);
		rootNode.attachChild(outil);
		rootNode.updateRenderState();
	}
	
	public Personnage getPersonnage(int id) {
		if(alentoursPersos==null){
			//System.err.println("Dans Monde : alentoursPersos non instancié !");
			return null;
		}
		for(int i=0; i<alentoursPersos.size();i++) {
			Personnage p = (Personnage)alentoursPersos.get(i);
			if(p.getId()==id) {
				return p;
			}
		}
		return null;
	}
	
	public Interaction getInter(int id) {
		for(int i=0; i<interaction.size();i++) {
			Interaction p = (Interaction)interaction.get(i);
			if(p.getId()==id) {
				return p;
			}
		}
		return null;
	}
	
	public void setObjetSelec (Geometry objet) {
		this.objetSelec = objet;
	}
	
	public Vector3f getOrientation () {
		if (this.objetSelec == null) return personnage.getLocalRotation().getRotationColumn(0);
		return this.objetSelec.getLocalTranslation().subtract(this.personnage.getLocalTranslation());
	}

	/**
	 * @return
	 */
	public CommClient getCommClient() {
		
		return commClient;
	}
	
	public GuiPrincipal getGUI(){
		return gui;
	}
	
	//--- Fin des ACCESSEURS ---
}