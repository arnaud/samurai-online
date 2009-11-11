package communication.actions;

import modele.quete.Quete;
import serveur.Serveur;
import client.Client;

import communication.CommClient;
import communication.CommServeur;
/**
 * @author gautieje
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SendQuestAction extends StandardAction  {

	public SendQuestAction (byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try {
			Quete quete = (Quete)getObject(Quete.class, objet);
			if(quete!=null) {
				System.out.println("[Quete reçue]");
				//quete.getInfos();
				client.perso.addQuete(quete);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("MoveInter : clientAction Failed");
		}

	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
		try {
			commServeur.sendTo(type, address, port, objet);
		} catch (Exception ex) {
			System.err.println("SendQuest: serveurAction Failed");
		}
	}

}