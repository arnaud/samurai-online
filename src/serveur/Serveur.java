/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package serveur;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import modele.action.Competence;
import modele.caracteristique.CaracteristiqueDynamique;
import modele.caracteristique.Mobilite;
import modele.caracteristique.Niveau;
import modele.caracteristique.Rang;
import modele.caracteristique.Vitalite;
import modele.objet.Arme;
import modele.objet.Batiment;
import modele.objet.ObjetAbstrait;
import modele.objet.ObjetDecoratif;
import modele.objet.Outil;
import modele.objet.Transportable;
import modele.personne.Depot;
import modele.personne.Groupe;
import modele.personne.Inventaire;
import modele.personne.PartieDuCorps;
import modele.personne.Personnage;
import modele.quete.Quete;
import serveur.ia.ControlleurIa;
import serveur.ia.Ia;
import serveur.modele.Monde;
import serveur.modele.TypeObjet;
import serveur.modele.TypePersonnage;
import serveur.util.BddConn;
import serveur.util.ExtendedPrintStream;

import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.util.LoggingSystem;
import communication.CommServeur;
import communication.ServeurGestReceived;

/**
 * @author jacquema
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Serveur {
	private Monde monde;
	public CommServeur commServeur;
	private BddConn bddConn;
	private ServeurGestReceived serveurGestReceived;
	private TypePersonnage typePersonnage;
	private TypeObjet typeObjet;
	private Vector clients;
	private Vector ias;
	private DataGenerator dataGenerator;
	private ControlleurIa controlleurIa;
	
	public Serveur(int serveurPort, int num_base) {
		// instanciations initiales
		System.out.println("Chargement du serveur ...");
		bddConn = new BddConn(num_base);
		commServeur = new CommServeur(this, serveurPort);
		serveurGestReceived = new ServeurGestReceived(this, commServeur);
		dataGenerator = new DataGenerator(this, commServeur);
		clients = new Vector();
		ias = new Vector();
		controlleurIa = new ControlleurIa(ias);
		
		// instanciation des objets du monde
		monde = new Monde(this);

		typePersonnage = new TypePersonnage(bddConn);
		typeObjet = new TypeObjet(bddConn);
		initWorld();
		//monde.getPersonnage(1).getInfos();
		System.out.println("***** SERVEUR INITIALISE *****");
		controlleurIa.start();
	}
	
	public void start () {
		// Afin de différencier l'initialisation des actions
		
		
		// on démarre l'analyse des paquets entrants
		serveurGestReceived.start();
		
		// commServeur.sendTo("127.0.0.1", 5010, "Salut, c'est le serveur");
		// actions dans le monde
		//perso.seDeplacer(1,1);
		//perso.seDeplacer(1,0);
		
		//		monde.setDialogBehaviour(2, // ALWAYS_SHOW_PROPS_DIALOG
		//				Serveur.class.getClassLoader()
		//				.getResource("data/images/splash.png"));
		
		//TODO: creer un SimpleGame (version client) si on veut debuguer
		monde.start();
	}
	
	public BddConn getBddConn() {
		return bddConn;
	}
	
	public Vector getClients() {
		return clients;
	}
	
	public ClientConnecte getClientConnecte(int id) {
		ClientConnecte cli = null;
		for(int i=0; i<clients.size(); i++) {
			if((cli = (ClientConnecte)(clients.get(i))).getId() == id) {
				return cli;
			}
		}
		return cli;
	}
	
	public ClientConnecte getClientFromIdPerso(int id) {
		ClientConnecte cli = null;
		for(int i=0; i<clients.size(); i++) {
			if((cli = (ClientConnecte)(clients.get(i))).getIdPerso() == id) {
				return cli;
			}
		}
		return cli;
	}
	
	public int addClient(String adresse, int port, String login, String pass) {
		ClientConnecte client = new ClientConnecte(adresse, port, login, pass);
		client.setLastConnectTime(System.currentTimeMillis());
		int id = client.getId(bddConn);
		if(id>0) {
			clients.add(client);
			System.err.println("> Nouveau client [id=" + id+"]");
			return id;
		} else {
			System.out.println("Le client " + login + " ne peut pas être authentifié !");
		}
		return -1;
	}
	
	public void removeClient(int id) {
		for(int i=0;i<clients.size();i++) {
			ClientConnecte client = (ClientConnecte)clients.get(0);
			if(client.getId()==id) {
				clients.remove(client);
				return;
			}
		}
	}
	
	public synchronized void updateLastTimeConnect(int client) {
		ClientConnecte cc = getClientConnecte(client);
		if(cc!=null) {
			cc.setLastConnectTime(System.currentTimeMillis());
		}
	}
	
	public void removeOldClient(long timeout) {
		long currTime = System.currentTimeMillis();
		ClientConnecte cli = null;
		for(int i=0; i<clients.size(); i++) {
			cli = (ClientConnecte)(clients.get(i));
			if(currTime - cli.getLastConnectTime() > timeout) {
				System.err.println("> Client supprimé [id=" + cli.getId()+"] ... timeout ...");
				clients.remove(cli);
			}
		}
	}
	
	public int getIdPersoFromIdClient(int id) {
		ClientConnecte client;
		for(int i=0; i<clients.size();i++) {
			if((client = (ClientConnecte)clients.get(i)).getId() == id) {
				return client.getIdPerso();
			}
		}
		return -1;
	}
	
	public Personnage getPersonnage(int id) {
		return monde.getPersonnage(id);
	}
	
	public int getId(String adresse, int port) {
		for(int i=0; i<clients.size(); i++) {
			ClientConnecte client = (ClientConnecte)clients.get(i);
			if(client.getAdresse().compareTo(adresse)==0 &&
					client.getPort() == port) {
				return client.getId();
			}
		}
		return 0;
	}
	
	public Quete getQueteFromIdQuete(int id) {
		if (id>-1){
			return typePersonnage.initialiserQuete(id,this);
		}else{
			return typePersonnage.queteAuHasard(this);
		}
	}
	
	public void initWorld() {
		
		Vector vecteurCompetence=new Vector();
		Competence competence = null;
		//On recupere les competences
		String query6 = "SELECT * FROM sam_compet";
		try {
			Statement stmt6 = BddConn.conn.createStatement();
			ResultSet rs6 = stmt6.executeQuery(query6);
			
			if(rs6.first()) {
				do {
					String effetInit=null,effetPerso = null,effetOutil=null,effetObjet=null,expressionObjet=null,expressionOutil=null,expressionPerso=null,expressionInit=null;
					String query7 = "SELECT * FROM sam_effet WHERE id='"+rs6.getInt("effetInit")+"'";
					try {
						Statement stmt7 = BddConn.conn.createStatement();
						ResultSet rs7 = stmt7.executeQuery(query7);
						if (rs7.first()){
							do{
								effetInit=rs7.getString("caract");
								expressionInit=rs7.getString("calcul");
							} while(rs7.next());
						}
						rs7.close();
						stmt7.close();
					} catch(Exception ex) {
						System.err.println("Exception lors de la création d'une competence '"+
						"dans initWorld()");
						ex.printStackTrace();
					}
					String query8 = "SELECT * FROM sam_effet WHERE id='"+rs6.getInt("effetObjet")+"'";
					try {
						Statement stmt8 = BddConn.conn.createStatement();
						ResultSet rs8 = stmt8.executeQuery(query8);
						if (rs8.first()){
							do{
								effetObjet=rs8.getString("caract");
								expressionObjet=rs8.getString("calcul");
							} while(rs8.next());
						}
						rs8.close();
						stmt8.close();
					} catch(Exception ex) {
						System.err.println("Exception lors de la création d'une competence '"+
						"dans initWorld()");
						ex.printStackTrace();
					}
					String query9 = "SELECT * FROM sam_effet WHERE id='"+rs6.getInt("effetPerso")+"'";
					try {
						Statement stmt9 = BddConn.conn.createStatement();
						ResultSet rs9 = stmt9.executeQuery(query9);
						if (rs9.first()){
							do{
								effetPerso=rs9.getString("caract");
								expressionPerso=rs9.getString("calcul");
							} while(rs9.next());
						}
						rs9.close();
						stmt9.close();
					} catch(Exception ex) {
						System.err.println("Exception lors de la création d'une competence '"+
						"dans initWorld()");
						ex.printStackTrace();
					}
					String query10 = "SELECT * FROM sam_effet WHERE id='"+rs6.getInt("effetOutil")+"'";
					try {
						Statement stmt10 = BddConn.conn.createStatement();
						ResultSet rs10 = stmt10.executeQuery(query10);
						if (rs10.first()){
							do{
								effetOutil=rs10.getString("caract");
								expressionOutil=rs10.getString("calcul");
							} while(rs10.next());
						}
						rs10.close();
						stmt10.close();
					} catch(Exception ex) {
						System.err.println("Exception lors de la création d'une competence '"+
						"dans initWorld()");
						ex.printStackTrace();
					}
					Collection caractNec = new Vector();
					String query11 = "SELECT * FROM sam_competcara WHERE compet='"+rs6.getInt("id")+"'";
					try {
						Statement stmt11 = BddConn.conn.createStatement();
						ResultSet rs11 = stmt11.executeQuery(query11);
						if (rs11.first()){
							do{
								String query12 = "SELECT pc.valeur, c.Nom, c.Min, c.Max, c.Vitalite FROM sam_competcara pc, sam_cara c WHERE pc.compet="+rs6.getInt("id")+" AND pc.carac=c.id";
								try {
									Statement stmt12 = BddConn.conn.createStatement();
									ResultSet rs12 = stmt12.executeQuery(query12);
									if(rs12.first()) {
										do {
											if (rs12.getString("Nom").equals("Mobilité"))
												caractNec.add(new Mobilite(rs12.getString("Nom"), rs12.getString("Min"),
														rs12.getInt("valeur"), rs12.getString("Max"),null));
											else if (rs12.getInt("Vitalite")==1)
												caractNec.add(new Vitalite(rs12.getString("Nom"), rs12.getString("Min"),
														rs12.getInt("valeur"), rs12.getString("Max"),null));
											else
												caractNec.add(new CaracteristiqueDynamique(rs12.getString("Nom"),
														rs12.getString("Min"), rs12.getInt("valeur"), rs12.getString("Max"), null));
										} while(rs12.next());
									}
									rs12.close();
									stmt12.close();
								} catch(Exception ex) {
									System.err.println("Exception lors de la création d'un personnage '"+
									"dans initWorld()");
									ex.printStackTrace();
								}
							} while(rs11.next());
						}
						rs11.close();
						stmt11.close();
					} catch(Exception ex) {
						System.err.println("Exception lors de la création d'une competence '"+
						"dans initWorld()");
						ex.printStackTrace();
					}
					competence = new Competence(rs6.getString("nom"),rs6.getString("xpMin")
							,rs6.getString("xpMax"),caractNec
							,rs6.getString("outil"),effetInit,effetPerso
							,effetOutil,effetObjet,expressionInit
							,expressionPerso,expressionOutil,expressionObjet
							,rs6.getFloat("taille"),rs6.getFloat("speed")
							,rs6.getFloat("duréeB"),rs6.getFloat("latence")
							,rs6.getFloat("total"),rs6.getBoolean("reversible")
							,rs6.getInt("nbEffet"),rs6.getInt("nbRepetition"));
					vecteurCompetence.add(competence);
					/*(String nom, String expressionXpMin, String expressionXpMax,
					 Collection caracteristiquesNecessaires, String outil,
					 String caracteristiquesModifieesPersoInit, String caracteristiquesModifieesPerso,
					 String caracteristiquesModifieesOutil, String caracteristiquesModifieesObjet,
					 String expressionPersoInit, String expressionPerso,
					 String expressionOutil, String expressionObjet,
					 float rayon, float vitesse, float dureeBloquage, float dureeLatence, float dureeTotal,
					 boolean reversible, int nbEffet, int nbRepetition)*/
				} while(rs6.next());
			}	
			rs6.close();
			stmt6.close();
		} catch(Exception ex) {
			System.err.println("Exception lors de la création d'une competence '"+
			"dans initWorld()");
			ex.printStackTrace();
		}
		
		// On initialise les batiments

		String query = "SELECT * FROM sam_typeobjet AS typeo, sam_objet AS o WHERE typeo.type='batiment' AND typeo.id=o.type";
		try {
			Statement stmt = BddConn.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.first()) {
				do {
					Batiment bati = (Batiment)typeObjet.get(rs.getString("typeo.nom"));
					bati.setId(rs.getInt("o.id"));
					bati.setPositionX(rs.getFloat("o.x"));
					bati.setPositionY(rs.getFloat("o.y"));
					// Pour tourner un batiment suivant l'axe z
					Quaternion z2= new Quaternion();
					z2.fromAngles(FastMath.DEG_TO_RAD*rs.getInt("o.angle_x"),
							FastMath.DEG_TO_RAD*rs.getInt("o.angle_y"),
							FastMath.DEG_TO_RAD*rs.getInt("o.angle_z"));
					bati.setLocalRotation(z2);
					bati.setLocalScale(rs.getFloat("scale"));
					
					// TODO : prendre en charge les caracteristiques des batiments
					
					monde.addBatiment(bati);
				} while(rs.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		// on recupere tout les persos
		Personnage personnage = null;
		
		query = "SELECT sp.*,stp.nom AS nomtype FROM sam_perso AS sp,sam_typeperso AS stp WHERE sp.type=stp.id";
		try {
			Statement stmt = BddConn.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.first()) {
				int nb_personnages = 0;
				do {
					nb_personnages++;
					//System.out.println(rs.getString("nom"));
					
					Inventaire inventaire = new Inventaire();
					Depot depot = new Depot();
					Rang rang = new Rang(rs.getString("nomtype"));
					
					personnage = new Personnage(rs.getString("nom"),rs.getString("sexe").charAt(0), rang, inventaire, depot);
					personnage.setLocalScale(rs.getFloat("scale"));
					//personnage.removeArme();
					
					if(rang.getNom().equals("commercant")) {
						personnage.setBatiment(monde.getBatiment(rs.getInt("sp.batiment")));
						ias.add(new Ia(commServeur, personnage));
					} else {
						//TODO : remove next line (only for test purpose)
						personnage.setGroupe(new Groupe("le chevalier du soleil levant", personnage));
					}
					
					// TODO : m'expliquer (mathieu) pourquoi il n'y a pas une table quetes, car un personnage peut avoir plusieurs quêtes!
					
					String q = rs.getString("quetes");
					if(q.compareTo("")!=0) {
						String[] listeQuetes = q.split(" ");
						for (int i= 0;i<listeQuetes.length;i++){
							Integer idQuete=new Integer(listeQuetes[i]);
							personnage.addQuete(typePersonnage.initialiserQuete(idQuete.intValue(),this));
						}
					}
					
					int id = rs.getInt("id");
					personnage.setId(id);
					personnage.setPositionX(rs.getFloat("x"));
					personnage.setPositionY(rs.getFloat("y"));
					
					personnage.addCaracteristique(new Niveau("Niveau",0,Integer.MAX_VALUE,personnage));
					
					//String classType = rs.getString("type");
					
					// Recupérer les caractéristiques
					
					// Caracteristiques du perso
					try {
						Statement stmt2 = BddConn.conn.createStatement();
						ResultSet rs2 = stmt2.executeQuery("SELECT pc.valeur, c.Nom, c.Min, c.Max, c.Vitalite FROM sam_persocara pc, sam_cara c WHERE pc.perso="+id+" AND pc.carac=c.id");
						if(rs2.first()) {
							do {
								if (rs2.getString("Nom").equals("Mobilité"))
									personnage.addCaracteristique(new Mobilite(rs2.getString("Nom"), rs2.getString("Min"),
											rs2.getInt("valeur"), rs2.getString("Max"), personnage));
								else if (rs2.getInt("Vitalite")==1)
									personnage.addCaracteristique(new Vitalite(rs2.getString("Nom"), rs2.getString("Min"),
											rs2.getInt("valeur"), rs2.getString("Max"), personnage));
								else
									personnage.addCaracteristique(new CaracteristiqueDynamique(rs2.getString("Nom"),
											rs2.getString("Min"), rs2.getInt("valeur"), rs2.getString("Max"), personnage));
							} while(rs2.next());
						}
						rs2.close();
						stmt2.close();
					} catch(Exception ex) {
						System.err.println("Exception lors de la création d'un personnage '"+
						"dans initWorld()");
						ex.printStackTrace();
					}
					
					// Recuperer les competences
					String query13 = "SELECT * FROM sam_persocompet WHERE perso='"+rs.getInt("id")+"'";
					try {
						Statement stmt13 = BddConn.conn.createStatement();
						ResultSet rs13 = stmt13.executeQuery(query13);
						if(rs13.first()) {
							do {
								personnage.addCompetence((Competence) vecteurCompetence.get((rs13.getInt("compet")-1)),rs13.getInt("xp"));
							} while(rs13.next());
						}
						rs13.close();
						stmt13.close();
					} catch(Exception ex) {
						System.err.println("Exception lors de l'insertion des competences dans un personnage '"+
						"dans initWorld()");
						ex.printStackTrace();
					}
					
					// 	Objets du perso
					
					String query3 = "SELECT * FROM sam_objetinventaire WHERE perso='"+id+"'";
					
					// Inventaire
					try {
						Statement stmt3 = BddConn.conn.createStatement();
						ResultSet rs3 = stmt3.executeQuery(query3);
						if(rs3.first()) {
							do {String query4 = "SELECT * FROM sam_objet WHERE id='"+rs3.getInt("objet")+"'";
							try {
								Statement stmt4 = BddConn.conn.createStatement();
								ResultSet rs4 = stmt4.executeQuery(query4);
								if(rs4.first()) {									
									do {
										ObjetAbstrait objet=typeObjet.get(rs4.getInt("type"));
										System.out.println("Objet ajouté : " + objet.getNom());
										if (rs3.getString("PartieDuCorps") == null)
											inventaire.addObjet((Transportable)objet);
										else {
											Iterator itPartie = personnage.getPartieDuCorps().iterator();
											while (itPartie.hasNext()) {
												PartieDuCorps partie = (PartieDuCorps)itPartie.next();
												if (partie.getNomPartieDuCorps().equals (rs3.getString("PartieDuCorps"))) {
													partie.setObjet((Outil)objet);
													break;
												}
											}
										}
									} while(rs4.next());
									
								}
								rs4.close();
								stmt4.close();
							} catch(Exception ex) {
								System.err.println("Exception lors de la création de l'inventaire '"+
								"dans initWorld()");
								ex.printStackTrace();
							}
							} while(rs3.next());
						}
						rs3.close();
						stmt3.close();
						
						
						String query5 = "SELECT * FROM sam_perso WHERE id='"+id+"'";
						Statement stmt5 = BddConn.conn.createStatement();
						ResultSet rs5 = stmt5.executeQuery(query5);
						if(rs5.first()){
							inventaire.setValeurRessources(rs5.getInt("or"),rs5.getInt("pierre"),rs5.getInt("nourriture"),rs5.getInt("fer"),rs5.getInt("bois"));
						}
						rs5.close();
						stmt5.close();
						
					} catch(Exception ex) {
						System.err.println("Exception lors de la création de l'inventaire '"+
						"dans initWorld()");
						ex.printStackTrace();
					}
					
					monde.addPersonnage (personnage);
				} while(rs.next());
				System.out.println(nb_personnages+" personnages ont été chargés.");
			}
			rs.close();
			stmt.close();
		} catch(Exception ex) {
			System.err.println("Exception lors de la création d'un personnage '"+
			"dans initWorld()");
			ex.printStackTrace();
		}
		
		// On initialise les objets de déco
		query = "SELECT * FROM sam_typeobjet AS typeo, sam_objet AS o WHERE typeo.type='deco' AND typeo.id=o.type";
		try {
			Statement stmt = BddConn.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.first()) {
				do {
					ObjetDecoratif deco = (ObjetDecoratif)typeObjet.get(rs.getString("typeo.nom"));
					deco.setPositionX(rs.getFloat("o.x"));
					deco.setPositionY(rs.getFloat("o.y"));
					Quaternion z1= new Quaternion();
					z1.fromAngles(FastMath.DEG_TO_RAD*rs.getInt("o.angle_x"),
							FastMath.DEG_TO_RAD*rs.getInt("o.angle_y"),
							FastMath.DEG_TO_RAD*rs.getInt("o.angle_z"));
					deco.setLocalRotation(z1);
					deco.setLocalScale(rs.getFloat("scale"));
					
					// TODO : prendre en charge les caracteristiques des objets
					
					monde.addDeco(deco);
				} while(rs.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		/*Random random = new Random();
		Quaternion z1= new Quaternion();
		z1.fromAngles(FastMath.DEG_TO_RAD*-90, 0, 0);
		for(int i=0;i<150;i++) {
			ObjetDecoratif deco = (ObjetDecoratif)typeObjet.get("tree");
			float x = random.nextFloat()*5000f-2500;
			float y = random.nextFloat()*5000f-2500;
			deco.setPositionX(x);
			deco.setPositionY(y);
			float mHeight = monde.getCarte().getHeight(deco.getPosition());
			if(mHeight>45f) {	
				deco.setLocalRotation(z1);
				deco.setLocalScale(0.6f);
			
				// TODO : prendre en charge les caracteristiques des objets
				monde.addDeco(deco);
				System.out.println("INSERT INTO sam_objet(type, x, y, angle_x, angle_y, angle_z, scale) "+
						"VALUES('tree','"+x+"','"+y+"','-90','0','0','0.6'");
			} else {
				i--;
			}
		}
		System.out.println("arbres finis de charger");
		*/

		
		// On initialise les armes

		query = "SELECT * FROM sam_typeobjet AS typeo, sam_objet AS o WHERE typeo.type='arme' AND typeo.id=o.type";
		try {
			Statement stmt = BddConn.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.first()) {
				do {
					Arme arme = (Arme)typeObjet.get(rs.getString("typeo.nom"));

					arme.setPositionX(rs.getFloat("o.x"));
					arme.setPositionY(rs.getFloat("o.y"));
					// Pour tourner un objet suivant l'axe z
					Quaternion z2= new Quaternion();
					z2.fromAngles(FastMath.DEG_TO_RAD*rs.getInt("o.angle_x"),
							FastMath.DEG_TO_RAD*rs.getInt("o.angle_y"),
							FastMath.DEG_TO_RAD*rs.getInt("o.angle_z"));
					arme.setLocalRotation(z2);
					arme.setLocalScale(rs.getFloat("scale"));
					
					// TODO : prendre en charge les caracteristiques des objets
					
					monde.addArme(arme);
				} while(rs.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// On initialise les outils

		query = "SELECT * FROM sam_typeobjet AS typeo, sam_objet AS o WHERE typeo.type='outil' AND typeo.id=o.type";
		try {
			Statement stmt = BddConn.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.first()) {
				do {
					Outil outil = (Outil)typeObjet.get(rs.getString("typeo.nom"));

					outil.setPositionX(rs.getFloat("o.x"));
					outil.setPositionY(rs.getFloat("o.y"));
					// Pour tourner un objet suivant l'axe z
					Quaternion z2= new Quaternion();
					z2.fromAngles(FastMath.DEG_TO_RAD*rs.getInt("o.angle_x"),
							FastMath.DEG_TO_RAD*rs.getInt("o.angle_y"),
							FastMath.DEG_TO_RAD*rs.getInt("o.angle_z"));
					outil.setLocalRotation(z2);
					outil.setLocalScale(rs.getFloat("scale"));
					
					// TODO : prendre en charge les caracteristiques des objets
					
					monde.addOutil(outil);
				} while(rs.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Monde getMonde() {
		return monde;
	}
	
	
	/**
	 * Sï¿½lectionne le flux utilisï¿½ pour la sortie standard ainsi que la sortie d'erreurs.
	 *
	 */
	private static void defineStreams(boolean log_fichiers){
		if(log_fichiers){
			try {
				System.setOut(new ExtendedPrintStream("server-output.log"));
				System.setErr(new ExtendedPrintStream("server-errors.log"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			System.setOut(new ExtendedPrintStream(System.out));
			System.setErr(new ExtendedPrintStream(System.err));
		}
	}
	
	public DataGenerator getDataGenerator() {
		return dataGenerator;
	}
	
	public void setDataGenerator(DataGenerator dataGenerator) {
		this.dataGenerator = dataGenerator;
	}
	
	public static void main(String[] args) {
		int num_base = 1;
		int serveurPort = 8010; // valeur par défaut
		if(args.length>0){
			if(args.length>0) serveurPort = Integer.parseInt(args[0]);
			else	System.err.println("Le port du serveur n'est pas spécifié : Utilisation du port "+serveurPort+" par défaut.");
			if(args.length>1) defineStreams(args[1].equals("log"));
			if(args.length>2) num_base = String.valueOf(args[2]).equals("local") ? 1 : String.valueOf(args[2]).equals("eseo") ? 2 : String.valueOf(args[2]).equals("sivit") ? 3 : 0;
			else	System.err.println("Ecriture des flux de sortie vers la console par défaut.");
			System.out.println("Démarrage du serveur sur le port "+serveurPort+"...");
		}else{
			System.err.println("Aucun argument n'a été entré !");
			System.err.println("Le serveur va démarrer avec les paramètres par défaut.");
			System.out.println("Commande manuelle du serveur : launch_serveur [port] [flux] [base]");
			System.out.println("> port : ~numéro~  : Entrer le numéro du port du serveur distant.");
			System.out.println("> flux : 'log'     : Redirige les flux de sortie et d'erreurs vers les fichiers de logs.");
			System.out.println("         'nolog'   : Conserve les flux de sortie par défaut (console).");
			System.out.println("> base : 'eseo'    : Utilise la base de données à l'eseo.");
			System.out.println(">        'sivit'   : Utilise la base de données distante.");
			System.out.println(">        defaut    : Utilise la base locale par défaut.");
			System.out.println();
			defineStreams(false); // par défaut
		}
		LoggingSystem.getLogger().setLevel(java.util.logging.Level.SEVERE);
		System.out.println("Bienvenue à toi, beta testeur du serveur");
		Serveur serveur = new Serveur(serveurPort, num_base);
		serveur.start();
	}
}
