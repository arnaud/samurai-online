/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.action;

import java.util.Collection;
import java.util.Vector;

import modele.objet.ObjetConcret;
import modele.objet.Outil;
import modele.personne.Personnage;
import serveur.modele.Monde;

import com.jme.bounding.BoundingBox;
import com.jme.curve.BezierCurve;
import com.jme.curve.Curve;
import com.jme.curve.CurveController;
import com.jme.intersection.CollisionData;
import com.jme.intersection.TriangleCollisionResults;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Geometry;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Interaction extends Node {
	public static int ID_CURRENT=0;
	public int id;
	
	transient private Competence competence;
	transient private Personnage personnage;
	transient private Outil outil;
	transient private Monde monde;
	
	//TODO: conteneur ou trimesh ?
	private Curve curve;
	private CurveController controleur;
	
	transient private TriangleCollisionResults results;
	transient private Collection objetsUtilises;
	
	private float dateCourante;
	//TODO: supprimer les lignes suivantes si les perf sont OK (étudier les performance du get)
	private float dateFinBlocage;
	transient private boolean isFree;
	private float dateFinLatence;
	private float dateFinTotal;
	
	transient private int nbEffet = 0;
	transient private int nbRepetition;

	public Interaction (Personnage personnage, Competence competence, Monde monde) {
		this (personnage, competence, monde, null, 0);
	}
	
	public Interaction (Personnage personnage, Competence competence, Monde monde, Curve curve, int nbRepetition) {
		super ("node interaction");
		this.personnage = personnage;
		this.competence = competence;
		this.outil = personnage.getOutil(competence.getOutilType());
		this.monde = monde;
		this.curve = curve;
		this.nbRepetition = nbRepetition;
		
		this.results = new TriangleCollisionResults ();
		this.objetsUtilises = new Vector ();
		this.isFree = (nbRepetition != 0);
		
		id = ID_CURRENT++;
	}
	
	public void init () {
		// Récupération des temps
		dateCourante = 0F;
		dateFinBlocage = competence.getDureeBlocage();
		dateFinLatence = competence.getDureeLatence();
		dateFinTotal = competence.getDureeTotal();
		
		// Création de la zone d'action (en l'occurence un cube)
		//TODO: texturiser la boite
		float dimenssion = competence.getRayon();
		//TriMesh zoneActive = new Sphere ("sphere interaction", 20, 20, dimenssion);
		TriMesh zoneActive = new Box ("box interaction",
				new Vector3f (-dimenssion,-dimenssion,-dimenssion),
				new Vector3f (dimenssion,dimenssion,dimenssion));
		zoneActive.setModelBound(new BoundingBox());
		zoneActive.updateModelBound();
		this.attachChild(zoneActive);
		this.setIsCollidable(false);
		//TODO: setColor pour le node;
		zoneActive.setDefaultColor(ColorRGBA.red);
		
		// Si le personnage n'existe pas, cela signifie que l'on a déjà la courbe
		if (curve == null) {
			// Création du controleur déplaçant la zone d'action
			Vector3f translation = personnage.getOrientationInter();
			//TODO: rajouter la hauteur du perso divisée par deux
			Vector3f[] v = {new Vector3f(personnage.getLocalTranslation()).add(new Vector3f(0F,3F,0F)),
					//TODO: valeur numérique : 1000
					//TODO: tester avec un simple mult
					personnage.getLocalTranslation().add (translation.multLocal(1000))};
			curve = new BezierCurve ("courbe", v);
			System.out.println ("Courbe : " + v[0] + " " + v[1]);
		}
		
		// Création du controleur du mouvement le long de la courbe
		controleur = new CurveController (curve, zoneActive,
				competence.getDureeLatence()*competence.getVitesse(),
				competence.getDureeTotal()*competence.getVitesse());
		controleur.setSpeed(competence.getVitesse());
	}
	
	public void firstStart () {
		start ();
		// L'interaction ne peut toucher le personnage l'ayant créée
		objetsUtilises.add(this.personnage);
		// Mais l'effet initial de la compétence à lieu
		this.competence.calculInit(this.personnage, this.outil);
	}
	
	public void start () {
		// Some initialisation
		this.init ();
		// Liaison au monde
		monde.addInteraction (this);
	}
	
	public boolean update (float fps) {
		dateCourante += fps;
		if (!isFree && dateCourante > dateFinBlocage) {
			isFree = true;
			personnage.setInteractionPossible(true);
		}
		if (dateCourante > dateFinTotal) {
			monde.delInteraction (this);
			return false;
		} else {
			controleur.update(fps);
			if (dateCourante > dateFinLatence) {
				// Gestion des objets en collision
				this.gererCollision ();
				
				// Gestion de la fin de l'interaction
				//TODO: étudier les performance du get
				if (nbEffet >= competence.getNbEffet()) {
					// Suppression de l'interaction dans le monde
					monde.delInteraction (this);
					nbRepetition++;
					// Si le nombre de répétition est fixé à 0, l'interaction se répète sans cesse
					if (competence.getNbRepetition() == 0 || nbRepetition < competence.getNbRepetition()) {
						Interaction inter = new Interaction (personnage, competence, monde, curve, nbRepetition);
						inter.start ();
					}
				}
			}
			return true;
		}
	}
	
	public void gererCollision () {
		results.clear();
		//TODO: Si cette ligne n'était pas nécessaire, cela économiserait la sursuivante
		this.setIsCollidable(true);
		this.calculateCollisions(monde.getRootNode(), results);
		this.setIsCollidable(false);
		for (int i=0; i < results.getNumber() && nbEffet < competence.getNbEffet(); i++) {
			CollisionData collisionCourante = results.getCollisionData(i);
			Geometry target = collisionCourante.getTargetMesh();
			//TODO: vérifier le get (isInstanceOf)
			ObjetConcret objet = (ObjetConcret) target.getParent();
			if (!objetsUtilises.contains(objet)) {
				objetsUtilises.add(objet);
				if (this.competence.calcul (this.personnage, this.outil, objet))
					nbEffet++;
			}
		}
	}
	
	public int getId () {
		return id;
	}
}
