package communication.actions;

import modele.objet.Outil;
import serveur.Serveur;
import client.Client;
import client.modele.Monde;

import com.jme.system.DisplaySystem;
import communication.CommClient;
import communication.CommServeur;

public class AutreOutilAction extends StandardAction {

	public AutreOutilAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try { 
			Outil outilRecu = (Outil)getObject(Outil.class, objet);
			DisplaySystem display = client.getMonde().getDisplay();
			client.modele.Outil outil = new client.modele.Outil(client.getMonde(), outilRecu);
			
		    System.out.println("[Test]Outil recue : " + 
		    		outil.getNom() + " " + 
		    		outil.getId() + " " + 
		    		outil.getPositionX() + " " +
		    		outil.getPositionY() + " "); 
				
		    Monde m = client.getMonde();
		    m.addAlentourOutil(outil);
			System.out.println("Autre Arme initialisée :" + m.alentoursOutils.size());
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("AutreBati : clientAction Failed");
		}
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
	}
	
}
