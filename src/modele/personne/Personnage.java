/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.personne;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import modele.action.Action;
import modele.action.Competence;
import modele.action.Interaction;
import modele.caracteristique.Caracteristique;
import modele.caracteristique.Experience;
import modele.caracteristique.Mobilite;
import modele.caracteristique.Niveau;
import modele.caracteristique.Rang;
import modele.objet.Batiment;
import modele.objet.ObjetConcret;
import modele.objet.Outil;
import modele.quete.Quete;
import serveur.modele.Monde;

import com.jme.math.Vector3f;

/**
 * Modèle commun d'un personnage
 */
public class Personnage extends ObjetConcret implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** Declaration*/
	private Inventaire inventaire;
	private Depot depot;
	private int id;
//	private PartieDuCorps tete, tronc, brasGauche, brasDroit; //TODO: à mettre dans la colection qui suit
	private Collection partiesDuCorps;
	private Collection competences;
	private Collection quetes;
	//private Vector caracteristiques;
	// Caracteristique particulière :
	private Niveau niveau;
	private Mobilite mobilite;
	private Rang rang;
//	private Caracteristique force,dexterite,sagesse,charisme,intelligence,constitution;
//  caracteristiques suivantes : dependent des caracteristiques precedentes
//	private Caracteristique vie,experience,caraSpecifique; //caraSpecifique <> honneur/karma positif/karma negatif
	private Clan clan;
	private Groupe groupe;
	private Interaction interaction; //TODO: inutile pour l'instant
	private boolean interactionPossible = true;
	private Vector3f orientationInter;
	private Action action;
	private boolean possedeGroupe,possedeClan,possedeInventaire; //TODO: à supprimer (si clan == null alors il n'a pas de clan)
	private char sexe; //soit M pour masculin ou F pour feminin
	protected int etat;
	private Batiment batiment = null;

	// Vitesses de déplacement
	public static final float WALK_SPEED = 8F;
	public static final float RUN_SPEED = 14F;
	public static final float MAX_SPEED = 150;

	// Différents états du personnage durant le jeu
	protected static final int ETAT_FLANER = 0;
	protected static final int ETAT_MARCHER = 1;
	protected static final int ETAT_COURIR = 2;
	protected static final int ETAT_SAUTER = 3;
	protected static final int ETAT_MOURIR = 4;
	protected static final int ETAT_ATTAQUER = 5;

	/**
	 * Constructeur par défaut.
	 * Initialise toutes les variables avec leurs valeurs par défaut.
	 */
	public Personnage(){
		this.nom="unknownPerson";
		setName("Personnage");
		this.rang=null;
		this.depot=null;
		this.inventaire=null;
		this.clan=null;
		this.id=-1;
		this.sexe='M';
		possedeClan=false;
		possedeGroupe=false;
		caracteristiques=new Vector();
		competences = new Vector();
		quetes = new Vector();
		partiesDuCorps = new Vector ();
		this.isCollidable = true;
	}
	
	public Personnage(Personnage perso) {
		this();
		this.setId(perso.getId());
		this.setNom(perso.getNom());
		this.setSexe(perso.getSexe());
		this.setInteractionPossible(perso.isInteractionPossible());
		this.setPosition(perso.getPosition());
		this.setDirection(perso.getDirection());
		this.setCaracteristiques(perso.getCaracteristiques());
		this.setCompetences(perso.getCompetences());
		this.setQuetes(perso.getQuetes());
		this.setDepot(perso.getDepot());
		this.rang = perso.getRang();
		//this.setTete
		//this.setTronc
		//this.setBrasGauche
		//this.setBrasDroit
		this.setClan(perso.getClan());
		this.setGroupe(perso.getGroupe());
	}

	
	public Personnage(String nom, char sexe){
		this();
		this.nom=nom;
		this.sexe=sexe;
	}
	
	public Personnage(String nom,char sexe, Rang rang,Inventaire inventaire,Depot depot){
		this (nom, sexe);
		this.rang=rang;
		this.depot=depot;
		this.inventaire=inventaire;
		partiesDuCorps = new Vector ();
		partiesDuCorps.add (new PartieDuCorps("Tete"));
		partiesDuCorps.add (new PartieDuCorps("Tronc"));
		partiesDuCorps.add (new PartieDuCorps("Bras gauche"));
		partiesDuCorps.add (new PartieDuCorps("Bras droit"));
	}
	
	public Personnage(String nom,char sexe, Rang rang,Inventaire inventaire,Depot depot,Vector caracteristiques){
		this (nom,sexe,rang,inventaire,depot);
		this.caracteristiques=caracteristiques;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return nom;
	}
	
	public char getSexe() {
		return sexe;
	}
	
	public void setSexe(char sexe) {
		this.sexe = sexe;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id= id;
	}
	
	public boolean isVivant(){
		Caracteristique carac_vie = getCaracteristique("Vie");
		if(carac_vie == null)
			return false;
		return carac_vie.getValeur() >= carac_vie.getValeurMin();
	}
	
	public void augmenterVie(int valeur){
		Caracteristique carac_vie = getCaracteristique("Vie");
		carac_vie.setValeur(carac_vie.getValeur() + valeur);
	}
	
	public void décrémenterVie(int valeur){
		Caracteristique carac_vie = getCaracteristique("Vie");
		try{
			carac_vie.setValeur(carac_vie.getValeur() - valeur);
		}catch(NullPointerException e){
			//TODO POURQUOI ... ?
		}
	}
	
	public Inventaire getInventaire() {
		return inventaire;
	}

	public void setInventaire(Inventaire inventaire) {
		this.inventaire = inventaire;
	}
	
	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

// methodes sur la collection des caractéristiques du personnage	
	
	public void addCaracteristique (Caracteristique cara) {
		if (caracteristiques == null) caracteristiques = new Vector ();
		caracteristiques.add(cara);
		if (cara instanceof Mobilite) this.mobilite = (Mobilite)cara;
		if (cara instanceof Niveau) this.niveau = (Niveau)cara;
		if (cara instanceof Rang) this.rang = (Rang)cara;
		System.out.println(cara);
	}
	
//	public Collection getCaracteristiques() {
//		return caracteristiques;
//	}
	

// methodes sur les caracteristiques speciales : Rang, Mobilité, Niveau
	
	public Mobilite getMobilite() {
		return mobilite;
	}
	
	public Niveau getNiveau() {
		return niveau;
	}
	
	public Rang getRang() {
		return rang;
	}
	
	public boolean augmenterRang(Rang choixRang) {
		Collection rangsSuivants = rang.getRangSuivant();
		if (rangsSuivants != null && rangsSuivants.contains(choixRang)
				&& choixRang.getNiveauMinimum() <= this.niveau.getValeur()) {
			addCaracteristique (choixRang);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean diminuerRang(){
		Rang rangPrecedent;
		rangPrecedent=rang.getRangPrecedent();	
		if (rangPrecedent != null) {
			addCaracteristique (rangPrecedent);
			return true;
		}else{
			return false;
		}
	}
	
	/*public int getExperience(){
		return experience.getValeur();
	}
	
	public void setExperience(int valeur){
		experience.setValeur(valeur);
	}
	
	public int getVie(){
		return vie.getValeur();
	}
	
	public void setVie(int valeur){
		vie.setValeur(valeur);
	}
	
	public int getCaraSpecifique(){
		return caraSpecifique.getValeur();
	}
	
	public void setCaraSpecifique(int valeur){
		vie.setValeur(valeur);
	}*/
	
	// methodes sur la collection des competences du personnage
	
	public void setCompetences(Collection liste){
		competences = liste;
	}
	
	public Collection getCompetences(){
		return competences;
	}
	
	public boolean addCompetence(Competence competence) {
		return addCompetence (competence, 0);
	}
	
	public boolean addCompetence(Competence competence, int xp) {
		if(!competences.contains(competence)) {
//				&& this.rang.getCompetences() != null
//				&& this.rang.getCompetences().contains(competence)) {
			this.addCaracteristique(new Experience(xp,this,competence));
			return competences.add(competence);
		} else return false;
	}
	
	public void removeCompetence(Competence competence) {		
		competences.remove(competence);
	}
	
	public int nbCompetences() {
		return competences.size();
	}
	
	public boolean hasCompetence(Competence competence) {
		return competences.contains(competence);
	}
	
	public Competence getCompetence(String nom){
		for (Iterator it = competences.iterator(); it.hasNext();) {
			Competence comp = (Competence) it.next();
			if (comp.getNom().equals(nom))
				return comp;
		}
		return null;
	}
	
	// methodes sur la collection des quetes du personnage
	
	public void setQuetes(Collection quetes){
		this.quetes = quetes;
	}
	
	public Collection getQuetes(){
		return quetes;
	}
	
	public Quete getQuete(int idQuete){
		
		for (Iterator it = quetes.iterator(); it.hasNext();) {
			Quete quete= (Quete) it.next();
			if (quete.getId() == idQuete)
				return quete;
		}
		return null;
	}
	
	public void getInfosQuetes() {
		for(Iterator it = quetes.iterator(); it.hasNext();) {
			Quete quete = (Quete) it.next();
			quete.getInfos();
		}
	}
	
	public boolean addQuete(Quete quete) {
		return quetes.add(quete);
	}
		
	public boolean removeQuete(Quete quete) {		
		return quetes.remove(quete);
	}
	
	public boolean hasQuete(Quete quete) {
		return quetes.contains(quete);		
	}
	
	public boolean hasQuete(int idQuete) {
		for(Iterator it = quetes.iterator(); it.hasNext();) {
			Quete quete = (Quete) it.next();
			if (quete.getId()==idQuete){			
				return true;
			}	
		}
		return false;		
	}
	
	public boolean hasQueteEnCours() {		
		for(Iterator it = quetes.iterator(); it.hasNext();) {
			Quete quete = (Quete) it.next();
			if (!quete.isRealisee()){			
				return true;
			}	
		}
		return false;
	}
	

	
	public boolean hasUtilisable(String outilType) {
		for (Iterator it = partiesDuCorps.iterator(); it.hasNext();) {
			PartieDuCorps partie = (PartieDuCorps) it.next();
			if (partie.hasObjet (outilType))
				return true;
		}
		return false;
	}
	
	public Outil getOutil (String objetType) {
		for (Iterator it = partiesDuCorps.iterator(); it.hasNext();) {
			PartieDuCorps partie = (PartieDuCorps) it.next();
			if (partie.hasObjet (objetType))
				return partie.getObjet();
		}
		return null;
	}
	
//methode s'appliquant sur le clan du personnage
	
	public Clan getClan(){
		return clan;
	}
	
	public void setClan(Clan clan){
		this.clan=clan;
		possedeClan=true;
	}
	
//methode s'appliquant sur le groupe du personnage
	
	public Groupe getGroupe(){
		return groupe;
	}
	
	public void setGroupe(Groupe groupe){
		 this.groupe=groupe;
	     possedeGroupe=true;
	}
	
	public void creerGroupe(String nomGroupe){
		try{
			groupe  = new Groupe(nomGroupe,this);
			
		}catch(Exception e){
			System.out.println("Exception :" + e);	
		}
		clan.addGroupe(groupe);
		possedeGroupe=true;
	}
	
	public void integrerDansGroupe(Personnage personnage){
		Personnage responsable;
		
		responsable=groupe.getResponsable();
		if(this.equals(responsable)){
			groupe.addPersonnage(personnage);
			personnage.setGroupe(groupe);
		}
	}
	
	public void modifierNomGroupe(String nomGroupe){
		Personnage responsable;
		
		responsable=groupe.getResponsable();
		if(this.equals(responsable)){
			groupe.setNomGroupe(nomGroupe);
		}
	}
	
	public void quitterGroupe(){
		Personnage responsable;
		
		responsable=groupe.getResponsable();
		if(this.equals(responsable)){
			//destruction du groupe si le responsable quite le groupe
			clan.removeGroupe(groupe);
			groupe.supprimerGroupe();
		}else{
			groupe.removePersonnage(this);
			groupe=null;
		}
		possedeGroupe=false;
	}
	
	public int tailleGroupe(){
		return groupe.personnagesSize();
	}

	public Collection getPartieDuCorps () {
		return partiesDuCorps;
	}
	
	public void getInfos() {
		System.out.println("Infos personnage :");
		System.out.println("	Nom :" + getNom());
		if(possedeClan){
			System.out.println("	Clan :" + clan.getNomClan());
		}
		if(possedeGroupe){
			System.out.println("	Appartient au groupe : " + groupe.getNomGroupe());	
		}
		//System.out.println("	Vie :" + getVie());
		//System.out.println("	Experience :" + getExperience());
		//System.out.println("	" +caraSpecifique.getNom()+ " :" + getCaraSpecifique());
		System.out.println("   Caracteristiques ("+caracteristiques.size()+ "):");
		for(Iterator it = caracteristiques.iterator(); it.hasNext();) {
			Caracteristique cara = (Caracteristique) it.next();
			System.out.println("	" +
					cara.getNom() + " :" +
					cara.getValeur());
		}
		if (inventaire!=null){
			System.out.println("   Inventaire de : " + nom + ", taille :" + inventaire.sizeObjets());
			for(int i=0;i<inventaire.sizeObjets();i++) {
				System.out.println("	"+
					((ObjetConcret)inventaire.getObjet(i)).getNom());
			}
			System.out.println("   Ressources :\n       "+inventaire.getRessourcesToString());
		}
		System.out.println("	Position (x,y,z) :" + getPosition());


	/*	System.out.println("	Position (x,y,z) :" + position.getX() + "," + 
				position.getY() + ","+ position.getZ() );*/
	}

	
//	 methodes sur les actions du personnage	
	
	public boolean setAction (){
		if (this.action == null)
			return false;
		else {
			this.action = new Action();
			return true;
		}
	}
	
	public void libererAction (){
		this.action = null;
	}
	
//	 methodes sur les interactions du personnage		
	
	//TODO: associer un perso à un monde si cela est utile
	public void setInteraction (Competence competence, Monde monde, Vector3f orientation) {
		this.orientationInter = orientation;
		if (this.isInteractionPossible () && competence.isUtilisable (this)) {
			Interaction inter = new Interaction (this, competence, monde);
			inter.firstStart();
			this.setInteractionPossible (false);
		}
	}
	
	public Vector3f getOrientationInter () {
		if (orientationInter != null)
			return orientationInter;
		else return this.getLocalRotation().getRotationColumn(0);
	}
	
	public boolean isInteractionPossible () {
		return this.interactionPossible;
	}
	
	public void setInteractionPossible (boolean v) {
		this.interactionPossible = v;
	}
	
	public void seDeplacer(Vector3f positionFinale) {
		//TODO: IA : pathfinding
	}
	
	public boolean seDeplacer(float dx, float dy, float dz) {
		//TODO: gestion du mouvement Ã  voir avec le moteur (le monde)
		// + gestion de la hauteur sur la carte
		System.out.println("La nouvelle position de "+ getLocalTranslation().addLocal(dx,dy,dz));
		return false;
	}
	
	public void setBatiment(Batiment bati) {
		batiment = bati;
	}
	
	public Batiment getBatiment() {
		return batiment;
	}
}
