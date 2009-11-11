package serveur.modele;

import java.sql.ResultSet;
import java.sql.Statement;

import modele.caracteristique.Caracteristique;
import modele.objet.Arme;
import modele.objet.Batiment;
import modele.objet.ObjetAbstrait;
import modele.objet.ObjetDecoratif;
import modele.objet.Outil;
import serveur.util.BddConn;

public class TypeObjet {
	private BddConn bddConn;
	
	public TypeObjet(BddConn bddConn) {
		this.bddConn = bddConn;
	}
	
	public ObjetAbstrait get(String nom) {
		ObjetAbstrait objet = null;
		String query = "SELECT * FROM sam_typeobjet WHERE nom='"+nom+"'";
		try {
			Statement stmt = BddConn.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.first()) {
				return get(rs.getInt("id"));
			}	
						
			rs.close();
			stmt.close();
		} catch(Exception ex) {
			System.err.println("Exception lors de la création de l'objet '"+
					nom+"' dans TypeObjet.get()");
		}
		return objet;
	}
	
	public ObjetAbstrait get(int id) {
		ObjetAbstrait objet = null;
		
		String query = "SELECT * FROM sam_typeobjet WHERE id='"+id+"'";
		try {
			Statement stmt = BddConn.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.first()) {
				String classType = rs.getString("type");
				if(classType.compareTo("deco")==0) {
					objet = new ObjetDecoratif(rs.getString("nom")); 
				} else if(classType.compareTo("outil")==0) {
					objet = new Outil(rs.getString("nom"));
				} else if(classType.compareTo("batiment")==0) {
					objet = new Batiment(rs.getString("nom"));
				} else if(classType.compareTo("arme")==0) {
					objet = new Arme(rs.getString("nom"));
				} else {
					return null;
				}
		
				// Recupérer les caractéristiques
				String query2 = "SELECT * FROM sam_typeobjetcara WHERE typeobjet='"+id+"'";
				try {
					Statement stmt2 = BddConn.conn.createStatement();
					ResultSet rs2 = stmt2.executeQuery(query2);
					if(rs2.first()) {
						do {
							objet.addCaracteristique(new Caracteristique(rs2.getString("nom"), rs2.getInt("valeurmin"),
								rs2.getInt("valeur"), rs2.getInt("valeurmax"), objet));
						} while(rs2.next());
					}
					rs2.close();
					stmt2.close();
				} catch(Exception ex) {
					System.err.println("Exception lors de la création des caractéristiques de l'objet '"+
						classType+"' dans TypeObjet.get()");
					 ex.printStackTrace();
				}
			}	
						
			rs.close();
			stmt.close();
		} catch(Exception ex) {
			System.err.println("Exception lors de la création de l'objet '"+
					id+"' dans TypeObjet.get()");
		}
		
		return objet;
	}
	
}
