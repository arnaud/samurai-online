package communication.actions;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import modele.action.Interaction;
import modele.caracteristique.Caracteristique;
import modele.personne.Personnage;

import com.jme.math.Vector3f;
import com.jme.system.DisplaySystem;

import serveur.Serveur;
import client.Client;
import client.modele.Monde;

import communication.CommClient;
import communication.CommServeur;
import communication.util.SetMove;

public class AutrePersoAction extends StandardAction {

	public AutrePersoAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try { 
			modele.personne.Personnage persoRecu = (modele.personne.Personnage)getObject(modele.personne.Personnage.class, objet);
			DisplaySystem display = client.getMonde().getDisplay();
			System.out.println("display width: " + display.getWidth());
			
			client.modele.Personnage perso;
			if(persoRecu.getRang().getNom().equals("chevre")) {
				perso = new client.modele.Personnage(client.getMonde(), persoRecu);
			} else {
				perso = new client.modele.Personnage(true, client.getMonde(), persoRecu);
			}
			
		    System.out.println("[Test]Personnage recu : " + 
		    		perso.getNom() + " " + 
		    		perso.getId() + " " + 
		    		perso.getRang().getNom() + " " + 
		    		perso.getSexe() + " " +
		    		perso.getPositionX() + " " +
		    		perso.getPositionY() + " "); 
				
		    Monde m = client.getMonde();
		    m.addAlentourPerso(perso);
		    client.clientGestReceived.initialise = true;
			System.out.println("Autre Personnage initialisé");
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("AutrePerso : clientAction Failed");
		}
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
	}
	
}
