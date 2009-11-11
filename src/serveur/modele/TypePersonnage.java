package serveur.modele;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import com.jme.math.Vector3f;
import com.jme.scene.Node;

import modele.caracteristique.Caracteristique;
import modele.monde.Zone;
import modele.personne.Personnage;
import modele.quete.ObjectifCollecter;
import modele.quete.ObjectifDeplacement;
import modele.quete.ObjectifQuete;
import modele.quete.ObjectifRencontrer;
import modele.quete.Quete;
import serveur.Serveur;
import serveur.util.BddConn;

public class TypePersonnage {
	private BddConn bddConn;
	
	public TypePersonnage(BddConn bddConn) {
		this.bddConn = bddConn;
	}
	
	public Personnage getPersonnage(String type, String nom) {
		Personnage personnage = getPersonnage(type);
		personnage.setNom(nom);
		return personnage;
	}
	
	/**
	 * Fonction d'initialisation d'un personnage a partir d' un type de personnage defini dans la base de donnees
	 * @param type
	 * @return
	 */
	public Personnage getPersonnage(String type) {
		Personnage personnage = null;
		
		String query = "SELECT * FROM sam_typeperso WHERE nom='"+type+"'";
		try {
			Statement stmt = BddConn.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.first()) {
				personnage = new Personnage(null, 'N');
				int id = rs.getInt("id");
				do {
					//String classType = rs.getString("type");
			
					// Recupérer les caractéristiques
					String query2 = "SELECT * FROM sam_typepersocara WHERE typeperso='"+id+"'";
					
					// Caracteristiques du perso
					try {
						Statement stmt2 = BddConn.conn.createStatement();
						ResultSet rs2 = stmt2.executeQuery(query2);
						if(rs2.first()) {
							do {
								personnage.addCaracteristique(new Caracteristique(rs2.getString("nom"), rs2.getInt("valeurmin"),
									rs2.getInt("valeur"), rs2.getInt("valeurmax"), personnage));
							} while(rs2.next());
						}
						rs2.close();
						stmt2.close();
					} catch(Exception ex) {
						System.err.println("Exception lors de la création du personnage '"+
							type+"' dans TypePersonnage.get()");
						ex.printStackTrace();
					}
					
					// 	Objets du perso
					
					
				} while(rs.next());
			}
			rs.close();
			stmt.close();
		} catch(Exception ex) {
			System.err.println("Exception lors de la création du personnage '"+
					type+"' dans TypePersonnage.get()");
			ex.printStackTrace();
		}
		
		return personnage;
	}
	
	/**
	 * Fonction d'initialisation d'une quete prise au hasard dans la base de donnees
	 * @param serveur
	 * @return Quete
	 */
	   
	public Quete queteAuHasard(Serveur serveur){
		Quete quete = null;
		int idQuete = -1;
		try {
			Statement stmt2 = BddConn.conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT id_quete FROM sam_quete ORDER BY RAND() LIMIT 1");
			if(rs2.first())
				idQuete =rs2.getInt("id_quete");
			rs2.close();
			stmt2.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println ("valeur de id_quete ="+idQuete);
		if(idQuete!=-1){
			quete=this.initialiserQuete(idQuete,serveur);
		}
		return quete;
	}
	
	/**
	 * Fonction d'initialisation d'une quête a partir de son idQuete
	 * @param idQuete
	 * @param serveur
	 * @return Quete
	 */
	
	public Quete initialiserQuete(int idQuete,Serveur serveur){
		Quete quete = null;
		String nameObjectif;
		String[] valeurObjectif;
		ObjectifQuete objectif=null;
		try {
			Statement stmt2 = BddConn.conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT nom,description FROM sam_quete WHERE id_quete="+idQuete);
			if(rs2.first()){
				quete = new Quete(idQuete,rs2.getString("nom"),rs2.getString("description"));
				rs2.close();
				rs2 = stmt2.executeQuery("SELECT num_objectif,description,type_action,valeur_action FROM sam_queteobjectif WHERE id_quete="+idQuete);
				if(rs2.first()){
					do {
						nameObjectif=(rs2.getString("type_action")).trim();
						valeurObjectif=(rs2.getString("valeur_action")).split(" ");
						int numero = rs2.getInt("num_objectif");
						if (nameObjectif.compareTo("deplacement")==0){
							Float x=new Float(valeurObjectif[0]);
							Float y=new Float(valeurObjectif[1]);
							Float rayon=new Float(valeurObjectif[2]);
							Zone zone= new Zone (new Vector3f(x.floatValue(),y.floatValue(),0),rayon.floatValue());
							objectif = new ObjectifDeplacement(numero,rs2.getString("description"),zone);
							objectif.setIdQuete(idQuete);
						}else if(nameObjectif.compareTo("detruire")==0){
							Integer idType=new Integer(valeurObjectif[0]);
							Integer idObjet=new Integer(valeurObjectif[1]);
							//TODO: methode pour recupere objets du monde avec id
							//objectif = new ObjectifDetruire(numero,rs2.getString("description"),);
							//objectif.setIdQuete(idQuete);			
						}else if(nameObjectif.compareTo("rencontrer")==0){
							Integer idPerso=new Integer(valeurObjectif[0]);	
							objectif = new ObjectifRencontrer (numero,rs2.getString("description"),idPerso.intValue());
							objectif.setIdQuete(idQuete);
						}else if(nameObjectif.compareTo("collecter")==0){
							//TODO: Collecter un objet deja instancie dans le monde et non plus un objet de type
							Integer idTypeObjet=new Integer(valeurObjectif[0]);	
							objectif = new ObjectifCollecter(numero,rs2.getString("description"),idTypeObjet.intValue());
							objectif.setIdQuete(idQuete);
						} else {
							System.err.println("Erreur non reconnaissance du type_action de l'objectifQuete :"+nameObjectif);
						}
						quete.addObjectif(objectif);
					} while(rs2.next());
				}			
			}	
			rs2.close();
			stmt2.close();
		} catch(Exception ex) {
			System.err.println("Exception lors de la création d'une quete");
			ex.printStackTrace();
		}
		quete.nextObjectif();
		return quete;
	}

}
