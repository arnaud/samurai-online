package communication.actions;

import serveur.Serveur;
import client.Client;

import communication.CommClient;
import communication.CommServeur;

public class BonjourAction extends StandardAction {

	public BonjourAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try {
			int id = ((Integer)getObject(java.lang.Integer.class, objet)).intValue();
			if(id>0) {
				client.setId(id);
				commClient.authentifie = true;
				System.out.println("Le serveur m'a bien authentifié");
			} else {
				commClient.authentifie = false;
				System.out.println("L'authentification a échouée");
			}
		} catch(Exception ex) {
			System.err.println("Bonjour : clientAction Failed");
		}
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
		try {
			String msg = ((String)getObject(java.lang.String.class, objet));
			String[] split = msg.split("!");
			int id = serveur.addClient(address, port, split[0], split[1]);
			if(id>0) {
				commServeur.sendTo(StandardAction.BONJOUR, address, port, 
						getBytes(java.lang.Integer.class, new Integer(id)));
				commServeur.initClientPerso(id);
			} else {
				commServeur.sendTo(StandardAction.BONJOUR, address, port, 
						getBytes(java.lang.Integer.class, new Integer(-1)));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("Bonjour : serveurAction Failed");
		}
	}
	
}
