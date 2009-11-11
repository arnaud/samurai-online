package communication.actions;

import com.jme.math.Vector3f;

import modele.action.Competence;
import modele.personne.Personnage;
import serveur.Serveur;
import client.Client;

import communication.CommClient;
import communication.CommServeur;

public class InteractionAction extends StandardAction {

	public InteractionAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
		try {
			Vector3f orientation = (Vector3f)getObject(Vector3f.class, objet);
			int id = serveur.getId(address, port);
			if(id>0) {
				Personnage perso = serveur.getPersonnage(serveur.getIdPersoFromIdClient(id));
				Competence comp = (Competence)perso.getCompetences().iterator().next();
				perso.setInteraction(comp, serveur.getMonde(), orientation);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("Interaction : serveurAction Failed");
		}
	}
	
}
