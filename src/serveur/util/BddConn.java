package serveur.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Cette classe permet la connexion avec la base de données. Elle est utilisée uniquement par
 * le serveur qui est le seul à dialoguer avec la base.
 *
 */
public class BddConn {
	public static Connection conn;
	
	// paramètres de connexion
	private final static String SERVEUR = "vds92.sivit.org";
	private final static int PORT = 3306;
	private final static String BASE = "samurai";
	private final static String UTILISATEUR = "samurai";
	private final static String PASS = "bushibushi";
	
	public BddConn(int num_base) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			System.err.println("Impossible d'utiliser le Driver");
		}
		
		try {
			Properties prop = new Properties();
			prop.put("user",UTILISATEUR);
			prop.put("password",PASS);
			String ACCES = "jdbc:mysql://"+SERVEUR+":"+PORT+"/"+BASE;
			
			switch(num_base){
				case 1 :
					conn = DriverManager.getConnection("jdbc:mysql://localhost/samurai", "samurai", "bushibushi");
					break;
				case 2 :
					conn = DriverManager.getConnection("jdbc:mysql://172.20.0.11/mmorpg", "md", "grosbill");
					break;
				case 3 :
					conn = DriverManager.getConnection(ACCES, prop);
					break;
				default :
					conn = DriverManager.getConnection("jdbc:mysql://localhost/samurai", "samurai", "bushibushi");
					break;
			}
				
			System.out.println("Connecté à la base de données");
		} catch (Exception ex) {
			System.err.println("Impossible de se connecter à la base");
			System.err.println("Veuillez vérifier les arguments entrés !");
			System.exit(-1);
			//ex.printStackTrace();
		}

	}
	
	public static void main(String[] args){
		System.out.println("Tentative de connexion à la base de données...");
		System.out.println(">Serveur : "+SERVEUR);
		System.out.println(">Port : "+PORT);
		System.out.println(">Base : "+BASE);
		System.out.println(">Login : "+UTILISATEUR);
		System.out.println(">Pass : "+PASS);
		new BddConn(0);
	}
}
