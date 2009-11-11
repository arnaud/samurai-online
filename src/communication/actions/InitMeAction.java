package communication.actions;

import serveur.Serveur;
import client.Client;

import communication.CommClient;
import communication.CommServeur;

public class InitMeAction extends StandardAction {

	public InitMeAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
		try {
			int id = serveur.getId(address, port);
			if(id>0) {
				commServeur.initClientAutres(id);
			} 
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("InitMe : serveurAction Failed");
		}
	}
	
}
