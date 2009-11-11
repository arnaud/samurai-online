package serveur;

import java.sql.ResultSet;
import java.sql.Statement;

import serveur.util.BddConn;

public class ClientConnecte {
	private String adresse;
	private int port;
	private int id;
	private String login;
	private String pass;
	private int idperso;
	private long lastconnecttime = 0;
	
	public ClientConnecte() {
		
	}
	
	public ClientConnecte(String adresse, int port, String login, String pass) {
		this.adresse = adresse;
		this.port = port;
		this.login = login;
		this.pass = pass;
		this.id = -1;
		this.idperso = -1;
	}
	
	public String getAdresse() {
		return adresse;
	}
	
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getId() {
		return id;
	}
	
	public int getId(BddConn bddConn) {
		if(id == -1) {
			String query = "SELECT * FROM sam_joueurs WHERE login='"+login+
							"' AND pass='"+pass+"'";
			try {
				Statement stmt = BddConn.conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				if(rs.first()) {
					id = rs.getInt("id");
					idperso = rs.getInt("perso");
				} else {
					id = -1;
				}
			} catch(Exception ex) {
				System.out.println("Exception lors de l'appel getID()");
			}
		}
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdPerso() {
		return idperso;
	}
	
	public long getLastConnectTime() {
		return lastconnecttime;
	}
	
	public void setLastConnectTime(long time) {
		lastconnecttime = time;
	}
}
