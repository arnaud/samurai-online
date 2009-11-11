package communication.actions;

import serveur.Serveur;
import client.Client;

import communication.CommClient;
import communication.CommServeur;

public class PingAction extends StandardAction {

	public PingAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try {
			long diffping = System.currentTimeMillis()-((Long)getObject(java.lang.Long.class, objet)).longValue();
			System.err.println("ping du serveur : " + diffping + "ms");
			commClient.serverAccessible = true;
		} catch (Exception ex) {
			System.err.println("Ping : clientAction Failed");
		}
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
		try {
			commServeur.sendTo(type, address, port, objet);
		} catch (Exception ex) {
			System.err.println("Ping : serveurAction Failed");
		}
	}

}
