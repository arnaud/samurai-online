/*
 * Created on 9 avr. 2006
 *
 */
package test;

import modele.caracteristique.Caracteristique;
import modele.caracteristique.Rang;
import modele.objet.Outil;
import modele.objet.Transportable;
import modele.personne.Clan;
import modele.personne.Depot;
import modele.personne.Inventaire;
import modele.personne.Personnage;

import com.jme.math.Vector3f;
/**
 * @author JF
 *Test de la classe personnage (Desole pour les noms utilises !)
 */
public class TestPersonnage {

	public static void main(String[] args) {
		
		Personnage persoM;
		Inventaire inventaireM;
		Depot depotM;
		Rang premierRang;
		Vector3f positionM;
		Clan javaClan;
		
		premierRang = new Rang("Base");
		inventaireM = new Inventaire();
		depotM = new Depot();
		positionM = new Vector3f(100,100,100);
		
		persoM = new Personnage("Maurice",'M',premierRang,inventaireM,depotM);
		
		javaClan = new Clan("Clan java",persoM);
		persoM.setClan(javaClan);
		Transportable couteau =new Outil("Couteau");
		inventaireM.addObjet(couteau);
		persoM.creerGroupe("Java team");
		persoM.setId(4);
		persoM.addCaracteristique( new Caracteristique("force",10,20,100,persoM));
		persoM.setPosition(positionM);
		inventaireM.setValeurRessources(50,50,50,50,50);
		persoM.getInfos();
		try{ 
			persoM.quitterGroupe();
		}catch(Exception e){
			System.out.println("Exception :" + e);
		}		
	}
}
