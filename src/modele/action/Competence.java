/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.action;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import modele.caracteristique.Caracteristique;
import modele.objet.ObjetConcret;
import modele.objet.Outil;
import modele.personne.Personnage;

import org.nfunk.jep.JEP;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Competence implements Serializable {
	private String nom;
	
	// Expression pour les bornes de l'expérience
	private String expressionXpMin;
	private String expressionXpMax;
	
	// Gestion des caractéristiques
	private Collection caracteristiquesNecessaires; //caractéristique ayant une valeur min
	private String outil; // Outil modifié et requis
	// Effet initial
	private String caracteristiquesModifieesPersoInit; // String représentant la caractéristique à modifier
	private String expressionPersoInit;	// une expression par caractéristique modifiée
	// Effets lors de l'interaction
	private String caracteristiquesModifieesPerso;
	private String expressionPerso;
	private String caracteristiquesModifieesOutil;
	private String expressionOutil;
	private String caracteristiquesModifieesObjet;
	private String expressionObjet;
	
	//TODO: A compléter plus tard
//	private Competence contreCompetence;
//	private Personnage possesseurCompetence;
//	private ObjetConcret objetFabrique;
	
	private float rayon; //Pour des zones sphériques (qui sont en fait cubiques)
	private float vitesse; //Pour le déplacement de la zone

	private float dureeBloquage; //en seconde
	private float dureeLatence;
	private float dureeTotal;
	private boolean reversible; //TODO: gérer le concept
	private int nbEffet; //Nombre d'effet au cour d'un cycle total
	private int nbRepetition; //Nombre de cycles totaux possible

	public Competence () {
		this ("?");
	}
	
	public Competence (String nom) {
		this (nom, null, null, null, null, null, null, null, null, null, null);
	}
	
	public Competence (String nom, Collection caracteristiquesNecessaires, String outil,
			String caracteristiquesModifieesPersoInit, String caracteristiquesModifieesPerso,
			String caracteristiquesModifieesOutil, String caracteristiquesModifieesObjet,
			String expressionPersoInit, String expressionPerso,
			String expressionOutil, String expressionObjet) {
		this (nom, "0", String.valueOf(Float.MAX_VALUE),
				caracteristiquesNecessaires, outil,
				caracteristiquesModifieesPersoInit, caracteristiquesModifieesPerso,
				caracteristiquesModifieesOutil, caracteristiquesModifieesObjet,
				expressionPersoInit, expressionPerso, expressionOutil, expressionObjet,
				5F, 0.1F, 0.2F, 0.5F, 10F, false, 1, 1);
	}
	
	public Competence (String nom, String expressionXpMin, String expressionXpMax,
			Collection caracteristiquesNecessaires, String outil,
			String caracteristiquesModifieesPersoInit, String caracteristiquesModifieesPerso,
			String caracteristiquesModifieesOutil, String caracteristiquesModifieesObjet,
			String expressionPersoInit, String expressionPerso,
			String expressionOutil, String expressionObjet,
			float rayon, float vitesse, float dureeBloquage, float dureeLatence, float dureeTotal,
			boolean reversible, int nbEffet, int nbRepetition) {
		this.nom = nom;
		this.expressionXpMin = expressionXpMin;
		this.expressionXpMax = expressionXpMax;
		this.caracteristiquesNecessaires = caracteristiquesNecessaires;
		this.outil = outil;
		this.caracteristiquesModifieesPersoInit = caracteristiquesModifieesPersoInit;
		this.caracteristiquesModifieesPerso = caracteristiquesModifieesPerso;
		this.caracteristiquesModifieesOutil = caracteristiquesModifieesOutil;
		this.caracteristiquesModifieesObjet = caracteristiquesModifieesObjet;
		this.expressionPersoInit = expressionPersoInit;
		this.expressionPerso = expressionPerso;
		this.expressionOutil = expressionOutil;
		this.expressionObjet = expressionObjet;
		this.rayon = rayon;
		this.vitesse = vitesse;
		this.dureeBloquage = dureeBloquage;
		this.dureeLatence = dureeLatence;
		this.dureeTotal = dureeTotal;
		this.reversible = reversible;
		this.nbEffet = nbEffet;
		this.nbRepetition = nbRepetition;
	}
	
	public boolean isUtilisable (Personnage personnage) {
		if (!personnage.hasCompetence(this))
			return false;
		// Teste si le perso possède l'outil requis
		if (outil != null && !personnage.hasUtilisable (outil))
			return false;
		// Teste si le perso à les caractéristiques nécessaires (rang, niveau, force, ...)
		big_loop: for (Iterator it1 = caracteristiquesNecessaires.iterator(); it1.hasNext();) {
			Caracteristique caraCourante = (Caracteristique) it1.next ();
			for (Iterator it2 = personnage.getCaracteristiques().iterator(); it2.hasNext();) {
				Caracteristique caraPerso = (Caracteristique) it2.next ();
				if (caraCourante.isComparable(caraPerso) && caraCourante.isInferior(caraPerso))
					continue big_loop;
			}
			return false;
		}
		return true;
	}
	
	public void calculInit (Personnage personnage, Outil outil) {
		JEP myParser = new JEP ();
		myParser.setImplicitMul(true);
		myParser.addStandardConstants();
		myParser.addStandardFunctions();
		
		preparer (myParser, personnage, "Perso");
		preparer (myParser, outil, "Outil");
		
		Caracteristique caract = personnage.getCaracteristique(caracteristiquesModifieesPersoInit);
		myParser.parseExpression(expressionPersoInit);
		caract.setValeur((int)myParser.getValue());
	}
	
	public boolean calcul (Personnage personnage, Outil outil, ObjetConcret objet) {
		Caracteristique caract = objet.getCaracteristique(caracteristiquesModifieesObjet);
		if (caract != null) {
			// Préparation de l'expression
			JEP myParser = new JEP ();
			myParser.setImplicitMul(true);
			myParser.addStandardConstants();
			myParser.addStandardFunctions();
			
			preparer (myParser, personnage, "Perso");
			preparer (myParser, outil, "Outil");
			preparer (myParser, objet, "Objet");
			
			// Évaluation des expressions
			myParser.parseExpression(expressionObjet);
			caract.setValeur((int)myParser.getValue());
			caract = outil.getCaracteristique(caracteristiquesModifieesOutil);
			myParser.parseExpression(expressionOutil);
			caract.setValeur((int)myParser.getValue());
			caract = personnage.getCaracteristique(caracteristiquesModifieesPerso);
			myParser.parseExpression(expressionPerso);
			caract.setValeur((int)myParser.getValue());
			
			return true;
		} else return false;
	}
	
	private void preparer (JEP myParser, ObjetConcret objet, String id) {
		for (Iterator itCaractPerso = objet.getCaracteristiques().iterator(); itCaractPerso.hasNext ();) {
			Caracteristique caractCourante = (Caracteristique) itCaractPerso.next();
			myParser.addVariable(caractCourante.getNom()+id, caractCourante.getValeur());
			myParser.addVariable(caractCourante.getNom()+"Min"+id, caractCourante.getValeurMin());
			myParser.addVariable(caractCourante.getNom()+"Max"+id, caractCourante.getValeurMax());
		}
	}
	
	public String getNom() {
		if(nom==null)
			return "?";
		else
			return nom;
	}
	
	public String getExpressionXpMin () {
		return this.expressionXpMin;
	}
	
	public String getExpressionXpMax () {
		return this.expressionXpMax;
	}
	
	public String getOutilType () {
		return this.outil;
	}
	
	public int getNbRepetition () {
		return this.nbRepetition;
	}
	
	public int getNbEffet () {
		return this.nbEffet;
	}
	
	public float getDureeBlocage () {
		return this.dureeBloquage;
	}
	
	public float getDureeLatence () {
		return this.dureeLatence;
	}
	
	public float getDureeTotal () {
		return this.dureeTotal;
	}
	
	public float getRayon () {
		return this.rayon;
	}
	
	public float getVitesse () {
		return this.vitesse;
	}
}