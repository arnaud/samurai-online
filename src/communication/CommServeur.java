/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package communication;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Vector;

import modele.action.Interaction;
import modele.objet.Arme;
import modele.objet.Batiment;
import modele.objet.ObjetDecoratif;
import modele.objet.Outil;
import modele.personne.Personnage;
import modele.quete.ObjectifQuete;
import serveur.ClientConnecte;
import serveur.Serveur;

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
import communication.util.SetMove;
import communication.util.SyncVector;

/**
 * @author jacquema
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommServeur {
	
	//private byte[] objet;
	public Serveur serveur;	
	private DatagramSocket socket = null;
	
	private SyncVector packetsRecus;	// permet de stocker les packets reçus et 
	private SyncVector packetsAEnvoyer;//d'y associer une correspondance
	private SyncVector recusNonLus;
	
	private ServeurSend serveurSend;	// Thread qui permet l'envoi de données
    private ServeurReceive serveurReceive;
	
	public CommServeur(Serveur serveur, int serveurPort) {
		this.serveur = serveur;
		try {
            socket = new DatagramSocket(serveurPort);
            // create a DatagramSocket with port of server
            
        } catch (Exception e) {
            System.err.println("Le port "+serveurPort+" ne peut être utilisé !");
            System.err.println("Veuillez vérifier qu'il n'est pas utilisé par un autre service.");
            System.err.println("Application terminée.");
            System.exit(-1);
        }

        packetsAEnvoyer = new SyncVector();
        packetsRecus = new SyncVector();
        recusNonLus = new SyncVector();
        serveurSend = new ServeurSend(this, packetsAEnvoyer);
        serveurReceive = new ServeurReceive(this, recusNonLus);
        serveurSend.start();
        serveurReceive.start();
   	}
	
    public DatagramSocket getSocket() {
    	return socket;
    }
    
    /*
    public void sendTo(int idClient, String msg) {
    	ClientConnecte cli = serveur.getClientConnecte(idClient);
    	sendTo(-1, cli.getAdresse(), cli.getPort(), msg);
    }
    
    public void sendTo(int idClient, String msg, byte[] bytes) {
    	ClientConnecte cli = serveur.getClientConnecte(idClient);
    	sendTo(-1, cli.getAdresse(), cli.getPort(), msg, bytes);
    }
    
    public void sendTo(int idpacket, int idClient, String msg) {
    	ClientConnecte cli = serveur.getClientConnecte(idClient);
    	sendTo(idpacket, cli.getAdresse(), cli.getPort(), msg);
    }
    
    public void sendTo(String client, int port_client, String msg) {
    	 sendTo(-1, client, port_client, msg);
    }
    
    public void sendTo(int idpacket, String client, int port_client, String msg) {
    	// Serveur send envoi les packets disponibles au format :
    	// idpacket!ip_hote!port_hote!type_message!message
    	String msg_send = idpacket + "!" + client + "!" + port_client + "!" + msg;
    	packetsAEnvoyer.add(new Paquet(msg_send));
    }
    
    public void sendTo(int idpacket, String client, int port_client, String msg, byte[] bytes) {
    	// Serveur send envoi les packets disponibles au format :
    	// idpacket!ip_hote!port_hote!type_message!message
    	String msg_send = idpacket + "!" + client + "!" + port_client + "!" + msg;
    	packetsAEnvoyer.add(new Paquet(msg_send, bytes));
    }
    */
    
   /* public void pong(String client, int port_client, long sendTime) {
    	System.out.println("pong envoyé à " + client + " " + port_client);
    	sendTo(client, port_client, "pong!" + sendTime);
    }*/
    	//byte type, byte priorite, int key, String address, int port, byte[] objet);
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

    // cette méthode permet d'envoyer les objets du monde qu'un client
    // a besoin de connaitre au début du jeu
    public void initClientPerso(int idClient) throws IOException {
    	// on envoie les dernieres caracteristiques du perso
    	int idperso = serveur.getIdPersoFromIdClient(idClient);
    	
    	Personnage perso = serveur.getPersonnage(idperso);
    	perso.getInfos();

    	byte[] objetToSend = StandardAction.getBytes(Personnage.class, perso);
    	System.out.println("taille du personnage envoye : " + objetToSend.length);
    	ClientConnecte cli = serveur.getClientConnecte(idClient);
    	sendTo(StandardAction.INITPERSO, cli.getAdresse(), cli.getPort(), objetToSend);

    	System.out.println("Client "+idClient+" | Initialisation du personnage "+perso.getNom());
    }
    
    public void initInter (Interaction inter) throws IOException {
       	byte[] objetToSend = StandardAction.getBytes(Interaction.class, inter);
    	
    	Vector clients = serveur.getClients();
    	for(int i=0;i<clients.size();i++) {
    		ClientConnecte client = (ClientConnecte)clients.get(i);
    	   	sendTo(StandardAction.INITINTER, client.getAdresse(), client.getPort(), objetToSend);

    	}
    	System.out.println("Client | Initialisation d'une interaction ");

    }
    
    public void initClientAutres(int idClient) throws IOException {
    	// on envoie les dernieres caracteristiques du perso
    	int idperso = serveur.getIdPersoFromIdClient(idClient);
    	Personnage perso = serveur.getPersonnage(idperso);
  	   	ClientConnecte cli = serveur.getClientConnecte(idClient);
    	
    	// serialisation des autres personnages
    	Vector personnages = serveur.getDataGenerator().persoNearThisOne(perso);
    	for(int i=0; i<personnages.size(); i++) {
    		Personnage perso2 = (Personnage)personnages.get(i);

          	byte[] objetToSend = StandardAction.getBytes(Personnage.class, perso2);

    	   	sendTo(StandardAction.AUTREPERSO, cli.getAdresse(), cli.getPort(), objetToSend);
    	   	tempo(50);
    		System.out.println("Serialisation du perso envoyee : " + perso2.getNom());
    	}
    	
    	System.out.println("Client "+idClient+" | Envoi de " + personnages.size()+" persos à proximité");
    	
    	// serialisation des objets deco
    	Vector objetsDeco = serveur.getDataGenerator().decoNearThisOne(perso);
    	Vector foret = new Vector();
    	System.out.println("nombres de deco à proxi : " + objetsDeco.size());
    	for(int i=0; i<objetsDeco.size(); i++) {
    		ObjetDecoratif deco = (ObjetDecoratif)objetsDeco.get(i);
    		if(deco.getNom().equals("tree")) {
    			foret.add(deco);
    		} else {
    			byte[] objetToSend = StandardAction.getBytes(ObjetDecoratif.class, deco);
    			sendTo(StandardAction.AUTREDECO, cli.getAdresse(), cli.getPort(), objetToSend);
    			tempo(50);
    			System.out.println("Serialisation de la deco envoyee : " + deco.getNom());
    		}
    	}
    	
    	if(foret.size()>0) {
    		byte[] objetToSend = StandardAction.getBytes(Vector.class, foret);
			sendTo(StandardAction.AUTREFORET, cli.getAdresse(), cli.getPort(), objetToSend);
			tempo(50);
			System.out.println("Serialisation de la foret envoyee : ");
    	}
    	
 
    	
    	// serialisation des batiments
    	Vector batiments = serveur.getDataGenerator().batimentsNearThisOne(perso);
    	System.out.println("nombres de batiments à proxi : " + batiments.size());
    	for(int i=0; i<batiments.size(); i++) {
    		Batiment bati = (Batiment)batiments.get(i);

          	byte[] objetToSend = StandardAction.getBytes(Batiment.class, bati);

    	   	sendTo(StandardAction.AUTREBATI, cli.getAdresse(), cli.getPort(), objetToSend);
    	   	tempo(50);
    		System.out.println("Serialisation du batiment envoyee : " + bati.getNom());
    	}
    	
    	// serialisation des armes
    	Vector objets = serveur.getDataGenerator().armesNearThisOne(perso);
    	System.out.println("nombres d'armes à proxi : " + objets.size());
    	for(int i=0; i<objets.size(); i++) {
    		Arme objet = (Arme)objets.get(i);

          	byte[] objetToSend = StandardAction.getBytes(Arme.class, objet);

    	   	sendTo(StandardAction.AUTREARME, cli.getAdresse(), cli.getPort(), objetToSend);
    	   	tempo(50);
    		System.out.println("Serialisation de l'objet envoyee : " + objet.getNom());
    	}
    	
    	// serialisation des outils
    	Vector outils = serveur.getDataGenerator().outilsNearThisOne(perso);
    	System.out.println("nombres d'outils à proxi : " + outils.size());
    	for(int i=0; i<outils.size(); i++) {
    		Outil objet = (Outil)outils.get(i);

          	byte[] objetToSend = StandardAction.getBytes(Outil.class, objet);

    	   	sendTo(StandardAction.AUTREOUTIL, cli.getAdresse(), cli.getPort(), objetToSend);
    	   	tempo(50);
    		System.out.println("Serialisation de l'objet envoyee : " + objet.getNom());
    	}

    	sendTo(StandardAction.INITFINISHED, cli.getAdresse(), cli.getPort(), null);
    }
    
	public void notifierDeplacement(Interaction inter, Vector3f position) {
    	Vector clients = serveur.getClients();
    	for(int i=0;i<clients.size();i++) {
    		ClientConnecte client = (ClientConnecte)clients.get(i);
			try{
				SetMove setMove = new SetMove(inter.getId(), position);
		       	byte[] objetToSend = StandardAction.getBytes(SetMove.class, setMove);
				System.out.println("Serialisation de l'interaction :" + objetToSend);

	    	   	sendTo(StandardAction.MOVEINTER, client.getAdresse(), client.getPort(), objetToSend);
	    	}
	    	catch(Exception e){
	        	e.printStackTrace();	
	    	}
    	}
    	System.out.println("Deplacement de l'inter envoyée");
	}
    
	public void notifierDeplacement(Personnage perso, Vector3f position) {
     	Vector clients = serveur.getClients();
    	for(int i=0;i<clients.size();i++) {
    		ClientConnecte client = (ClientConnecte)clients.get(i);
    		if(client.getIdPerso()!=perso.getId()) {
    			try{
    				// serialisation de la position
    				SetMove setMove = new SetMove(perso.getId(), position);
    				byte[] objetToSend = StandardAction.getBytes(SetMove.class, setMove);
    	    	   	sendTo(StandardAction.MOVE, client.getAdresse(), client.getPort(), objetToSend);
    	    	}
    	    	catch(Exception e){
    	        	e.printStackTrace();	
    	    	}
    		}
    	}
	}
	
	public void notifierMessage(byte type, int idperso, String texte) {
    	Vector clients = serveur.getClients();
    	for(int i=0;i<clients.size();i++) {
    		ClientConnecte client = (ClientConnecte)clients.get(i);
    		if(client.getIdPerso()!=idperso) {
    			try{
    				// serialisation du message
    				String msg = idperso + "!" + texte;
    				byte[] objetToSend = StandardAction.getBytes(String.class, msg);
    	    	   	sendTo(type, client.getAdresse(), client.getPort(), objetToSend);
    	    	}
    	    	catch(Exception e){
    	        	e.printStackTrace();	
    	    	}
    		}
    	}
	}
	
	public void notifierMessage(int idperso, String texte) {
		notifierMessage(StandardAction.SAY, idperso, texte);
	}
	
	public void notifierMessageClan(int idperso, String texte) {
		notifierMessage(StandardAction.SAYCLAN, idperso, texte);
	}
	
	public void notifierMessageEquipe(int idperso, String texte) {
		notifierMessage(StandardAction.SAYTEAM, idperso, texte);
	}
	
	public void orienterPersonnage(Personnage perso, Quaternion orientation) {
    	//int idperso = serveur.getIdPersoFromIdClient(id); 
    	//serveur.getPersonnage(idperso).
    	perso.getLocalRotation().set(orientation);
	}
	
	public void envoyerObjectifRealise(int idperso,ObjectifQuete objectifQuete) {
		ClientConnecte client = serveur.getClientFromIdPerso(idperso);
		byte[] objetToSend = StandardAction.getBytes(ObjectifQuete.class, objectifQuete);
   	   	sendTo(StandardAction.QUESTOBJECTIVE, client.getAdresse(), client.getPort(), objetToSend);
   	   	System.out.println("ObjectifQuete envoye");
	}
	
	private void tempo(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
