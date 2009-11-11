package communication.actions;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import serveur.Serveur;
import client.Client;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import communication.CommClient;
import communication.CommServeur;
import communication.util.SetOrienter;

public class OrienterAction extends StandardAction {

	public OrienterAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
	// n'est pas encore géré
	/*	try {
			SetOrienter orient = (SetOrienter)getObject(SetOrienter.class, objet);
			int id = orient.getId();
			
			client.modele.Personnage p = client.getPersonnage(orient.getId());
			if(p!=null) {
				System.err.println("deplacement reçu");
				commClient.orienterPersonnage(p, orient.getQuaternion());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("Orienter : clientAction Failed");
		}*/
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
		try {
			int id = serveur.getId(address, port);
			int idperso = serveur.getIdPersoFromIdClient(id); 	
			commServeur.orienterPersonnage(serveur.getPersonnage(idperso), (Quaternion)getObject(Quaternion.class, objet));
		} catch (Exception ex) {
			ex.printStackTrace();	
			System.err.println("Orienter : serveurAction Failed");
		}
	}
	
}
