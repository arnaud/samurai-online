package communication.actions;

import modele.objet.Batiment;
import modele.objet.ObjetDecoratif;
import serveur.Serveur;
import client.Client;
import client.modele.Monde;

import com.jme.system.DisplaySystem;
import communication.CommClient;
import communication.CommServeur;

public class AutreBatiAction extends StandardAction {

	public AutreBatiAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try { 
			//modele.personne.Personnage persoRecu = (modele.personne.Personnage)getObject(modele.personne.Personnage.class, objet);
			Batiment batiRecu = (Batiment)getObject(Batiment.class, objet);
			client.modele.Batiment bati = new client.modele.Batiment(client.getMonde(), batiRecu);
			//client.modele.ObjetDecoratif deco = new client.modele.ObjetDecoratif(client.getMonde(), "pont", "Pont");
			
		//	client.modele.Personnage perso = new client.modele.Personnage(client.getMonde(), persoRecu);
			
		    System.out.println("[Test]Bati recue : " + 
		    		bati.getNom() + " " + 
		    		bati.getId() + " " + 
		    		bati.getPositionX() + " " +
		    		bati.getPositionY() + " "); 
				
		    Monde m = client.getMonde();
		    m.addAlentourBati(bati);
			System.out.println("Autre Bati initialisée :" + m.alentoursBatiments.size());
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("AutreBati : clientAction Failed");
		}
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
	}
	
}
