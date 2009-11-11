package communication.actions;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import modele.action.Interaction;

import com.jme.math.Vector3f;

import serveur.Serveur;
import client.Client;

import communication.CommClient;
import communication.CommServeur;
import communication.util.SetMove;

public class MoveInterAction extends StandardAction {

	public MoveInterAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try {
			SetMove move = (SetMove)getObject(SetMove.class, objet);
			
			Interaction p = client.getInter(move.getId());
			if(p!=null) {
				System.err.println("deplacement reçu");
				System.out.println ("Nouvel pos : " + move.getVector3f());
				p.getChild(0).setLocalTranslation(move.getVector3f());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("MoveInter : clientAction Failed");
		}
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
	}
	
}
