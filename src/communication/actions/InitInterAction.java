package communication.actions;

import modele.action.Interaction;
import serveur.Serveur;
import client.Client;
import client.modele.Monde;

import communication.CommClient;
import communication.CommServeur;

public class InitInterAction extends StandardAction {

	public InitInterAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {

		try { 
			Interaction inter = (Interaction)getObject(Interaction.class, objet);
		    Monde m = client.getMonde();
		    System.out.println("Interaction créé en " + inter.getLocalTranslation());
		    m.addInteraction(inter);
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("InitInter : clientAction Failed");
		}
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
	}
	
}
