package communication.actions;

import modele.objet.Arme;
import serveur.Serveur;
import client.Client;
import client.modele.Monde;

import com.jme.system.DisplaySystem;
import communication.CommClient;
import communication.CommServeur;

public class AutreArmeAction extends StandardAction {

	public AutreArmeAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try { 
			Arme armeRecu = (Arme)getObject(Arme.class, objet);
			client.modele.Arme arme = new client.modele.Arme(client.getMonde(), armeRecu);
			
		    System.out.println("[Test]Arme recue : " + 
		    		arme.getNom() + " " + 
		    		arme.getId() + " " + 
		    		arme.getPositionX() + " " +
		    		arme.getPositionY() + " "); 
				
		    Monde m = client.getMonde();
		    m.addAlentourArme(arme);
			System.out.println("Autre Arme initialisée :" + m.alentoursArmes.size());
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("AutreBati : clientAction Failed");
		}
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
	}
	
}
