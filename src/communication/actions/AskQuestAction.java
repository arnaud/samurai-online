package communication.actions;

import modele.quete.Quete;
import serveur.Serveur;
import client.Client;

import communication.CommClient;
import communication.CommServeur;
/**
 * @author gautieje
 * Module de demande 
 */
public class AskQuestAction extends StandardAction  {

	public AskQuestAction (byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	/**
	 * Methode de demande de quete de la part du client
	 */
	public void clientAction(Client client, CommClient commClient) {
		try {
			// objet Integer -1 si demande d'une quete au hasard sinon idQuete
			commClient.sendTo(type, address, port, objet);
		} catch (Exception ex) {
			System.err.println("SendQuest: serveurAction Failed");
		}

	}
	
	/**
	 * Methode de renvoi d'une quete a un client a trvaer l'action de comunication SendQuestAction
	 * et enregistrement de cette quete dans le perso du client sur le serveur
	 */
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
		try {
			Integer idQuete = (Integer)getObject(Integer.class, objet);
			if(idQuete!=null) {
				Quete quete = serveur.getQueteFromIdQuete(idQuete.intValue());
				int id = serveur.getId(address, port);
				int idperso = serveur.getIdPersoFromIdClient(id); 	
				if (!(serveur.getPersonnage(idperso)).hasQuete(quete.getId())){
					System.out.println("[Quete a renvoyee "+quete.getId()+"]");
					byte[] objetToSend = StandardAction.getBytes(Quete.class, quete);
					commServeur.sendTo(StandardAction.SENDQUEST,address, port,objetToSend);
					System.out.println("[Quete renvoyee]");
					(serveur.getPersonnage(idperso)).addQuete(quete);
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("MoveInter : clientAction Failed");
		}
	}

}