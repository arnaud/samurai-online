package communication.actions;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import modele.action.Interaction;
import modele.caracteristique.Caracteristique;
import modele.personne.Personnage;

import com.jme.math.Vector3f;

import serveur.Serveur;
import client.Client;
import client.modele.Monde;

import communication.CommClient;
import communication.CommServeur;
import communication.util.SetMove;

public class InitPersoAction extends StandardAction {

	public InitPersoAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try { 
			while(!client.monde_init) {};

			modele.personne.Personnage persoRecu = (modele.personne.Personnage)getObject(modele.personne.Personnage.class, objet);
			
			//client.modele.Personnage perso = new client.modele.Personnage(client.getMonde(), persoRecu);
			
		    System.out.println("[Test]Personnage recu : " + 
		    		persoRecu.getNom() + " " + 
		    		persoRecu.getId() + " " + 
		    		persoRecu.getRang().getNom() + " " + 
		    		persoRecu.getSexe() + " " +
		    		persoRecu.getPositionX() + " " +
		    		persoRecu.getPositionY() + " " + 
		    		((Caracteristique)(persoRecu.getCaracteristiques().iterator().next())).getNom() + 
		    		" groupe : " + persoRecu.getGroupe().getNomGroupe());
		    //Monde m = client.getMonde();

		    client.perso = persoRecu;
			System.out.println("Personnage initialisé");
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("InitPerso : clientAction Failed");
		}
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
	}
	
}
