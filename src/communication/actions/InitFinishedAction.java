package communication.actions;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import modele.action.Interaction;
import modele.caracteristique.Caracteristique;
import modele.objet.ObjetDecoratif;
import modele.personne.Personnage;

import com.jme.math.Vector3f;
import com.jme.system.DisplaySystem;

import serveur.Serveur;
import client.Client;
import client.modele.Monde;

import communication.CommClient;
import communication.CommServeur;
import communication.util.SetMove;

public class InitFinishedAction extends StandardAction {

	public InitFinishedAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		commClient.init_finished = true;
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
	}
	
}
