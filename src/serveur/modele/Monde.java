/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package serveur.modele;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import modele.action.Interaction;
import modele.monde.Carte;
import modele.objet.Batiment;
import modele.objet.ObjetDecoratif;
import modele.personne.Personnage;
import modele.quete.ObjectifCollecter;
import modele.quete.ObjectifDeplacement;
import modele.quete.ObjectifDetruire;
import modele.quete.ObjectifQuete;
import modele.quete.ObjectifRencontrer;
import modele.quete.Quete;
import serveur.Serveur;
import serveur.util.DummyTimer;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.util.Timer;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Monde {//extends modele.monde.Monde {
	
	/**
	 * Vitesse à laquelle la position d'un personnage est rafraichie
	 */
	public final static int MOVE_PERSO_UPDATE = 300;
	
	/**
	 * Vitesse à laquelle se fait la vérification des connexions clients
	 */
	public final static int REM_OLD_CLIENTS_UPDATE = 30000;
	
	private boolean finished;
	private Node rootNode = new Node ("Node principale");;
	private Timer timer;
	private float tpf;
	private Serveur serveur;
	
	// TODO: A mettre peut-être dans une surclasse
	private Collection personnages = new Vector();
	private Collection interactions = new Vector();
	private Vector objetsDeco = new Vector();
	private Vector batiments = new Vector();
	private Vector outils = new Vector();
	private Vector armes = new Vector();
	private Carte carte;
	
	
	public Monde(Serveur serveur) {
		this.serveur = serveur;
		initGame();
	}
	
	public Node getRootNode(){
		return rootNode;
	}
	
	public Carte getCarte() {
		return carte;
	}
	
	public void setCarte(Carte carte) {
		if (this.carte != null) rootNode.detachChild(this.carte);
		this.carte = carte;
		add (carte);
	}
	
	public Personnage getPersonnage(int id) {
		Personnage perso;
		for(Iterator it = personnages.iterator(); it.hasNext();) {
			perso = (Personnage) it.next();
			if(perso.getId() == id) {
				return perso;
			}
		}
		return null;
	}

	public Collection getPersonnages(){
		return personnages;
	}
	
	public void addPersonnage (Personnage personnage) {
		//System.out.println("Ajout du personnage "+personnage.getNom());
		personnages.add(personnage);
		add (personnage);
	}
	
	public void addDeco (ObjetDecoratif deco) {
		//System.out.println("Ajout de la déco "+deco.getNom());
		objetsDeco.add(deco);
		rootNode.attachChild(deco);
		add(deco);
	}
	
	public Vector getObjetsDeco() {
		return objetsDeco;
	}
	
	public void addBatiment(Batiment bati) {
		System.out.println("Ajout du batiment " + bati.getNom());
		batiments.add(bati);
		rootNode.attachChild(bati);
		add(bati);
	}
	
	public Batiment getBatiment(int id) {
		for(int i=0;i<batiments.size();i++) {
			Batiment bati = (Batiment)batiments.get(i);
			if(bati.getId()==id)
				return bati;
		}
		return null;
	}
	
	public Vector getBatiments() {
		return batiments;
	}
	
	public void addOutil(modele.objet.Outil outil) {
		System.out.println("Ajout de l'objet " + outil.getNom());
		outils.add(outil);
		rootNode.attachChild(outil);
		add(outil);
	}
	
	public Vector getOutils() {
		return outils;
	}
	
	public void addArme(modele.objet.Arme arme) {
		System.out.println("Ajout de l'objet " + arme.getNom());
		armes.add(arme);
		rootNode.attachChild(arme);
		add(arme);
	}
	
	public Vector getArmes() {
		return armes;
	}
	
	public void addInteraction (Interaction inter) {
		interactions.add(inter);
		rootNode.attachChild(inter);
		try {
			System.out.println ("Nouvelle inter attaché au monde");
			serveur.commServeur.initInter(inter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int delInteraction (Interaction inter) {
		interactions.remove(inter);
		return rootNode.detachChild(inter);
	}
	

	
	public void add (Node node) {
		rootNode.attachChild(node);
	}

	public int del (Node node) {
		return rootNode.detachChild(node);
	}
	
	protected void initGame() {
		setCarte(new Carte());
	}
	
	public void start () {
		timer = new DummyTimer ();
		rootNode = new Node ("Node principale");
		
		//initGame ();
		
		// Ceci est la boucle principale du serveur qui gère la mise à jour logique du jeu
		// ainsi que l'envoi et la réception de message
		
		// faire bouger un perso en permanence
		/*Personnage jim = getPersonnage(2);
		jim.setPositionX(30);
		jim.setPositionY(-5);
		boolean ar = true;
		long lastup = System.currentTimeMillis();
		*/
		while (!finished) {
			timer.update();
			tpf = timer.getTimePerFrame();
			update (tpf);
			
			//TODO: remplacer le sleep par le yield (on sera obliger d'y venir)
			Thread.yield();
			
			// soulager la machine un peu => 15% d'util de mon proc à 1,7Ghz avec 10ms
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// on fait bouger jim
			/*if(System.currentTimeMillis()-lastup>MOVE_PERSO_UPDATE) {
				//System.out.println(jim.getPosition());
				Vector3f pos = jim.getPosition();
				if(pos.x>80) {
					ar = false;
				}
				if(pos.x<20) {
					ar = true;
				}
				if(ar) {
					pos.x+=1;
			    	jim.setPosition(pos);
					//serveur.commServeur.notifierDeplacement(jim,pos);
				} else {
					pos.x-=1;
			    	jim.setPosition(pos);
					//serveur.commServeur.notifierDeplacement(jim,pos);
				}
				lastup = System.currentTimeMillis();
				
		    	for (Iterator it = interactions.iterator(); it.hasNext();) {
		    		Interaction inter = (Interaction) it.next();
		    		// Si l'interaction modifie l'iterator, on le rafraichit
		    		System.out.println ("Envoie d'une position" + inter.getChild(0).getLocalTranslation());
					serveur.commServeur.notifierDeplacement(inter,inter.getChild(0).getLocalTranslation());
		    	}
			}*/
			
			// supprimer les reférences clients inutilisées
			serveur.removeOldClient(REM_OLD_CLIENTS_UPDATE);
		}
		
	}
	
    protected void update(float tpf) {
        
    	rootNode.updateGeometricState(tpf, true);
    	
    	updateAction (tpf);
    	
//    	updateQuete(tpf);
//    	méthode qui vérifie que le déplacement des personnages réalise ou non un objectif
//    	TODO pour chaque personnage verifier qu'il y a des quetes avec des objectifs de deplacement en cours
// 		si il en existe alors verifier si le personnage est dans la zone ou pas
    	
    }
    
    protected void updateAction (float tpf) {
    	for (Iterator it = interactions.iterator(); it.hasNext();) {
    		Interaction inter = (Interaction) it.next();
    		// Si l'interaction modifie l'iterator, on le rafraichit
    		if (!inter.update(tpf))
    			it = interactions.iterator();
    	}
    }
    
    protected void updateQuete (float tpf) {
    	for (Iterator it = personnages.iterator(); it.hasNext();) {
    		Personnage perso = (Personnage) it.next();
    		for (Iterator it2 = perso.getQuetes().iterator(); it2.hasNext();) {
    			Quete quete = (Quete) it2.next();
    	    	if (!quete.isRealisee()){
    	    		for (Iterator it3 = quete.getObjectifs().iterator(); it3.hasNext();) {
    	    			ObjectifQuete objectif = (ObjectifQuete) it3.next();
    	    			if((!objectif.isRealise())&&(objectif.getNumero()==quete.getNextObjectif())){
    	    				if (objectif instanceof ObjectifDeplacement){
    	    					if (((ObjectifDeplacement)objectif).getZone().isInsideZoneObjectif(perso.getPosition())){
    	    						System.out.println(">>   	"+perso.getNom()+" a ete dans la zone voulue");
    	    						objectif.setReussi();
    	    						objectif.getInfos();
    	    						serveur.commServeur.envoyerObjectifRealise(perso.getId(),objectif);
    	    						quete.nextObjectif();
    	    					}
    	    	    		}else if (objectif instanceof ObjectifCollecter){
    	    	    			//TODO reconnaitre objet a collecter a partir de son type
    	    	    		}else if (objectif instanceof ObjectifRencontrer){
								if((serveur.getDataGenerator()).isPersoNearOne(perso,((ObjectifRencontrer)objectif).getIdPerso())){
									System.out.println(">>   	Perso Detecté ["+((ObjectifRencontrer)objectif).getIdPerso()+"] ");	
    	    						objectif.setReussi();
    	    						objectif.getInfos();
    	    						serveur.commServeur.envoyerObjectifRealise(perso.getId(),objectif);
    	    						quete.nextObjectif();
								}  	    					    	    			
    	    	    		}else if (objectif instanceof ObjectifDetruire){
    	    	    			//TODO reconnaitre batiment ou objet a detruire a aprtir de son type
    	    	    		}
    	    			}
    	    		}	
    	    	}
    		}
    	}
    }
    	

    public void finish() {
        finished = true;
    }
    
     
}
