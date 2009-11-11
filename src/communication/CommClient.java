/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package communication;

import java.net.BindException;
import java.net.DatagramSocket;
import java.net.SocketException;

import client.Client;
import client.dialogue.Dialogue;
import client.gui.fenetre.contenu.ContChatBox;
import client.modele.Personnage;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import communication.actions.AskQuestAction;
import communication.actions.AutreArmeAction;
import communication.actions.AutreBatiAction;
import communication.actions.AutreDecoAction;
import communication.actions.AutreForetAction;
import communication.actions.AutreOutilAction;
import communication.actions.AutrePersoAction;
import communication.actions.BonjourAction;
import communication.actions.InitFinishedAction;
import communication.actions.InitInterAction;
import communication.actions.InitMeAction;
import communication.actions.InitPersoAction;
import communication.actions.InteractionAction;
import communication.actions.MoveAction;
import communication.actions.MoveInterAction;
import communication.actions.OrienterAction;
import communication.actions.PingAction;
import communication.actions.QuestObjectiveAction;
import communication.actions.SayAction;
import communication.actions.SayClanAction;
import communication.actions.SayTeamAction;
import communication.actions.SendQuestAction;
import communication.actions.StandardAction;
import communication.util.SyncVector;

/**
 * @author jacquema
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommClient {
    private DatagramSocket socket = null;
    public String server_address;
    private String login, pass;
    private ClientSend clientSend;	// Thread qui permet l'envoi de données
    private ClientReceive clientReceive;
    private Pinger pinger;
    
    private SyncVector packetsRecus;	// permet de stocker les packets reçus et 
    private SyncVector packetsAEnvoyer;//d'y associer une correspondance
	private SyncVector recusNonLus;
    public int numeroCourant = 500;		// permet ainsi d'avoir un numero unique 
    								//par requête au serveur
    public boolean isStarted;
    public boolean serverAccessible = false;
    public boolean authentifie = false;
    public boolean init_finished = false;

    public int server_port;
    public int client_port;
    public Client client;
    
    private ContChatBox contChatBox = null;
    
    //TODO: à supprimer le plus rapidement
    private long tempsPrecedent;
	private final long TEMPS_MAJ = 2000; // 2 secondes
    
    public CommClient(String adr, int server_port, int client_port, Client client) {
    	this.server_address = adr;
    	this.server_port = server_port;
    	this.client_port = client_port;
    	this.client = client;
    	
    	try {
    		System.out.print("Vérification du port du client : "+client_port+" ...");
            socket = new DatagramSocket(client_port);
            System.out.println("OK !");
            // create a DatagramSocket with port of client
        } catch (BindException e) {
            System.out.println("ERREUR !");
            e.printStackTrace();
    		System.err.println("Le port "+client_port+" doit déjà être utilisé !");
			String nom = "Erreur de port client !";
			String message = "Le port client spécifié ("+client_port+") est invalide.<br>Il est possible qu'il soit déjà utilisé par une autre application.<br>Peut-être avez-vous déjà une instance du jeu en exécution ?";
			new Dialogue(nom, message);
    		return;
        } catch (SocketException e) {
            System.out.println("ERREUR !");
			e.printStackTrace();
    		System.err.println("La socket n'a pas pu s'initialiser !");
			String nom = "Erreur de socket !";
			String message = "Une erreur est survenue lors de la communication par socket !";
			new Dialogue(nom, message);
   		return;
		}
        
        isStarted = true;
        
        packetsAEnvoyer = new SyncVector();
        clientSend = new ClientSend(this, packetsAEnvoyer);
        packetsRecus = new SyncVector();
        recusNonLus = new SyncVector();
        clientReceive = new ClientReceive(this, recusNonLus);
        pinger = new Pinger(this);
        clientSend.start();
        clientReceive.start();
        pinger.start();
    }
    
    public DatagramSocket getSocket() {
    	return socket;
    }
    
    public String getLogin() {
    	return login;
    }
    
    public String getPass() {
    	return pass;
    }
    
    public int deplacerPersonnage(Personnage perso){
    	int numero;
    	numero = numeroCourant;
    	numeroCourant++;
    	
    	// 	TODO: à supprimer le plus rapidement
    	if( (System.currentTimeMillis() - tempsPrecedent) > TEMPS_MAJ){
    		tempsPrecedent = System.currentTimeMillis();
    		try{
    			// serialisation de la position
    			byte[] objetToSend = StandardAction.getBytes(Vector3f.class, perso.getLocalTranslation());
    			sendTo(StandardAction.MOVE, server_address, server_port, objetToSend);
    		}
    		catch(Exception e){
    			e.printStackTrace();	
    		}
    		return numero;
    	// TODO: à supprimer le plus rapidement
    	} else return 0;
    }
    
	public void deplacerPersonnage(Personnage perso, Vector3f position) {
    	perso.setPosition(position);
	}
    
    public int orienterPersonnage(Personnage perso){
    	int numero;
    	numero = numeroCourant;
    	numeroCourant++;

//    	TODO: à supprimer le plus rapidement
    	if( (System.currentTimeMillis() - tempsPrecedent) > TEMPS_MAJ){
    		tempsPrecedent = System.currentTimeMillis();
		
    		try{
    			// serialisation de la position
    			byte[] objetToSend = StandardAction.getBytes(Quaternion.class, perso.getLocalRotation());
    			sendTo(StandardAction.ORIENTER, server_address, server_port, objetToSend);
    		} catch(Exception e){
    			e.printStackTrace();	
    		}
    		return numero;
    		//  TODO: à supprimer le plus rapidement
    	}else return 0;
    }
    
    public void authentifier(String login, String pass) {
    	String message = login + "!" + pass;
		byte[] objetToSend = StandardAction.getBytes(String.class, message);
		sendTo(StandardAction.BONJOUR, server_address, server_port, objetToSend);
    }
    
    public void creerCompte(String nom, String pass, String email, boolean sexeHomme){
    	String message = nom + "!" + pass + "!" + email + "!" + sexeHomme;
		byte[] objetToSend = StandardAction.getBytes(String.class, message);
		//sendTo(StandardAction.CREERCOMPTE, server_address, server_port, objetToSend);
    	//packetsAEnvoyer.add(new Paquet(message));// On pose le message dans la liste d'envoi
    }
    
  /*  public void sendTo(String client, int port_client, String msg) {
   	 sendTo(-1, client, port_client, msg);
   }
   
   public void sendTo(int idpacket, String client, int port_client, String msg) {
   	// Serveur send envoi les packets disponibles au format :
   	// idpacket!ip_hote!port_hote!message
   	String msg_send = idpacket + "!" + client + "!" + port_client + "!" + msg;
   	packetsAEnvoyer.add(new Paquet(msg_send));
   }
   
   public void sendTo(int idpacket, String msg, byte[] bytes) {
	// Serveur send envoi les packets disponibles au format :
	// idpacket!ip_hote!port_hote!type_message!message
	String msg_send = idpacket + "!" + msg;
	packetsAEnvoyer.add(new Paquet(msg_send, bytes));
}*/
    
    public void sendTo(byte type, String address, int port, byte[] objet) {
    	sendTo(type, -1, address, port, objet);
    }
    
    public void sendTo(byte type, int key, String address, int port, byte[] objet) {
    	byte stdPrio = 5;
        if(type == StandardAction.PING) {
        	PingAction action = new PingAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.MOVE) {
        	MoveAction action = new MoveAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.ORIENTER) {
        	OrienterAction action = new OrienterAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.BONJOUR) {
        	BonjourAction action = new BonjourAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.INITME) {
        	InitMeAction action = new InitMeAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.INTERACTION) {
        	InteractionAction action = new InteractionAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.SAY) {
        	SayAction action = new SayAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.SAYCLAN) {
        	SayClanAction action = new SayClanAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.SAYTEAM) {
        	SayTeamAction action = new SayTeamAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.MOVEINTER) {
        	MoveInterAction action = new MoveInterAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.INITPERSO) {
        	InitPersoAction action = new InitPersoAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.INITINTER) {
        	InitInterAction action = new InitInterAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.SENDQUEST) {
        	SendQuestAction action = new SendQuestAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.QUESTOBJECTIVE) {
        	QuestObjectiveAction action = new QuestObjectiveAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.AUTREPERSO) {
        	AutrePersoAction action = new AutrePersoAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.AUTREDECO) {
        	AutreDecoAction action = new AutreDecoAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.INITFINISHED) {
        	InitFinishedAction action = new InitFinishedAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.ASKQUEST) {
        	AskQuestAction action = new AskQuestAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.AUTREBATI) {
        	AutreBatiAction action = new AutreBatiAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.AUTREOUTIL) {
        	AutreOutilAction action = new AutreOutilAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.AUTREARME) {
        	AutreArmeAction action = new AutreArmeAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        } else if(type == StandardAction.AUTREFORET) {
        	AutreForetAction action = new AutreForetAction(type, stdPrio, key, address, port, objet);
        	packetsAEnvoyer.add(action);
        }
    }
   
   public SyncVector getPacketsRecus() { 
   	return packetsRecus;
   }
   
   public SyncVector getRecusNonLus() { 
   	return recusNonLus;
   }
   
   public void ping() {
   		byte[] objetToSend = StandardAction.getBytes(Long.class, new Long(System.currentTimeMillis()));
   		sendTo(StandardAction.PING, server_address, server_port, objetToSend);
   }
   
   public boolean estConnecte(){
	   ping();
	   long startTime = System.currentTimeMillis();
	   // TimeOut de 10s - serverAccessible est mis à vrai lors de la reception d'un ping
	   while((System.currentTimeMillis() < startTime + 10000) && !serverAccessible) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   	
	   //	Thread.yield();
	   }
	   return serverAccessible;
   }
   
   public boolean estAuthentifie(){
	   long startTime = System.currentTimeMillis();
	   
	   // TimeOut de 10s - serverAccessible est mis à vrai lors de la reception d'un ping
	   while((System.currentTimeMillis() < startTime + 10000) && !authentifie) {
	   	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   	
	   }
	   return authentifie;
   }
   
   /**
    * Referme la connexion au serveur ainsi que les processus de gestion de paquets.
    *
    */
   public void close() {
	   clientReceive.stopThread();
	   clientSend.stopThread();
	   //clientReceive.destroy();
	   recusNonLus = null;
	   packetsRecus = null;
	   //clientSend.destroy();
	   packetsAEnvoyer = null;
	   socket.close();
   }
   
   public void creerInteraction (Personnage perso) {
   		Vector3f id = new Vector3f (client.getMonde().getOrientation());
		byte[] objetToSend = StandardAction.getBytes(Vector3f.class, id);
		sendTo(StandardAction.INTERACTION, server_address, server_port, objetToSend);
   }   
   

   public void askInit() {
   		sendTo(StandardAction.INITME, server_address, server_port, null);
   }
   
   public void askQuest(int idQuete) {
		Integer id =new Integer(idQuete);
		byte[] objetToSend = StandardAction.getBytes(Integer.class, id);
		sendTo(StandardAction.ASKQUEST, server_address, server_port, objetToSend);
   }
   
   public void askQuestAuHasard() {
   		askQuest(-1);
   }
   
   public void sendText(String text) {
   		try{
   	    	// serialisation du texte
   			String msg = client.personnage.getId() + "!" + text;
			byte[] objetToSend = StandardAction.getBytes(String.class, msg);
    	   	sendTo(StandardAction.SAY, server_address, server_port, objetToSend);
   		} catch(Exception ex) {
   			ex.printStackTrace();
   		}
   }
   
   public void sendTextClan(String text) {
  		try{
   	    	// serialisation du texte
   			String msg = client.personnage.getId() + "!" + text;
			byte[] objetToSend = StandardAction.getBytes(String.class, msg);
    	   	sendTo(StandardAction.SAYCLAN, server_address, server_port, objetToSend);
   		} catch(Exception ex) {
   			ex.printStackTrace();
   		}
   }
   
   public void sendTextEquipe(String text) {
  		try{
   	    	// serialisation du texte
   			String msg = client.personnage.getId() + "!" + text;
			byte[] objetToSend = StandardAction.getBytes(String.class, msg);
    	   	sendTo(StandardAction.SAYTEAM, server_address, server_port, objetToSend);
   		} catch(Exception ex) {
   			ex.printStackTrace();
   		}
   }

   public void setContChatBox(ContChatBox contChatBox) {
   		this.contChatBox=contChatBox;
   }
   
   public ContChatBox getContChatBox() {
		return contChatBox;
}
    
}
