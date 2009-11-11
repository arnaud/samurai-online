/*
 * Created on 14 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;

import modele.caracteristique.Caracteristique;
import modele.personne.Personnage;
import serveur.modele.Monde;
import serveur.util.BddConn;


/**
 * @author camuspa
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SauvegardeMonde {
	private Monde monde;
	private BddConn bddConn;
	
	public SauvegardeMonde(Monde monde){
		this.monde=monde;
		bddConn = new BddConn(0);
	}
	
	public void updatePerso(){
		Collection personnages = monde.getPersonnages();
		Personnage perso;
		for(Iterator it = personnages.iterator(); it.hasNext();) {
			perso = (Personnage) it.next();
			/*UPDATE personnes
			 SET nom = 'DUPONT', adresse = '12b rue du Commerce'
			 WHERE nom = 'DUPOND' AND prenom = 'Albert'*/
			
			//Update de la table sam_perso
			String update = "UPDATE sam_perso SET x='"+perso.getPositionX() +"'" +
					", y='"+perso.getPositionY()+"'"+
					", bois='"+perso.getInventaire().getBois()+"'" +
					", nourriture='"+perso.getInventaire().getNourriture()+"'" +
					", pierre='"+perso.getInventaire().getPierre()+"'" +
					", fer='"+perso.getInventaire().getFer()+"'" +
					",or='"+perso.getInventaire().getOr()+"'" +
					"WHERE nom='"+perso.getNom()+"'";
			try {
				Statement stmt = BddConn.conn.createStatement();
				int rs = stmt.executeUpdate(update);
				stmt.close();
			} catch(Exception ex) {
				System.err.println("Exception lors de la sauvegarde d'un personnage '"+
				"dans initWorld()");
				ex.printStackTrace();
			}
			
			//Update de la table sam_persocara
			Collection caracteristiques=perso.getCaracteristiques();
			Caracteristique caracteristique;
			for(Iterator it1 = personnages.iterator(); it1.hasNext();) {
				caracteristique = (Caracteristique) it1.next();
				String update1 = "UPDATE sam_persocara SET valeur='"+caracteristique.getValeur() +"'" +
						"WHERE perso='"+perso.getId()+"' " +
						"and nom='"+caracteristique.getNom()+"'";
				try {
					Statement stmt1 = BddConn.conn.createStatement();
					int rs1 = stmt1.executeUpdate(update1);
					stmt1.close();
				} catch(Exception ex) {
					System.err.println("Exception lors de la sauvegarde d'un personnage '"+
					"dans initWorld()");
					ex.printStackTrace();
				}
			}
		}
		
	}
	
}

