package communication.actions;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import serveur.Serveur;
import client.Client;

import com.jme.math.Vector3f;
import communication.CommClient;
import communication.CommServeur;
import communication.util.SetMove;

public class MoveAction extends StandardAction {

	public MoveAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
		//System.out.println("nouvelle MoveAction : " + objet.length);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try {
			SetMove move = (SetMove)getObject(SetMove.class, objet);
			
			try {
				int id = move.getId();
				client.modele.Personnage p = client.getPersonnage(move.getId());
				if(p!=null) {
				//	System.err.println("deplacement reçu");
					commClient.deplacerPersonnage(p, move.getVector3f());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("Move : clientAction Failed");
		}
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
		try {
			int id = serveur.getId(address, port);
			int idperso = serveur.getIdPersoFromIdClient(id); 	
			Vector3f pos = (Vector3f)getObject(Vector3f.class, objet);
			serveur.getPersonnage(idperso).setPosition(pos);
			commServeur.notifierDeplacement(serveur.getPersonnage(idperso), pos);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("Move : serveurAction Failed. Obj size : " + objet.length);
		}
	}
	
}
