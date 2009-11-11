package communication.actions;

import serveur.Serveur;
import client.Client;

import communication.CommClient;
import communication.CommServeur;

public interface NetworkAction {
	public void clientAction(Client client, CommClient commClient);
	public void serveurAction(Serveur serveur, CommServeur commServeur);
}
