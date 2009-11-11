package communication.actions;

import java.util.Vector;

import modele.objet.ObjetDecoratif;
import serveur.Serveur;
import client.Client;
import client.modele.Monde;

import com.jme.system.DisplaySystem;
import communication.CommClient;
import communication.CommServeur;

public class AutreForetAction extends StandardAction {

	public AutreForetAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try { 
			//modele.personne.Personnage persoRecu = (modele.personne.Personnage)getObject(modele.personne.Personnage.class, objet);
			Vector foretRecu = (Vector)getObject(Vector.class, objet);
			//client.modele.ObjetDecoratif deco = new client.modele.ObjetDecoratif(client.getMonde(), decoRecu);
			
			//client.modele.ObjetDecoratif deco = new client.modele.ObjetDecoratif(client.getMonde(), "pont", "Pont");
			
		//	client.modele.Personnage perso = new client.modele.Personnage(client.getMonde(), persoRecu);
			
		    /*System.out.println("[Test]Deco recue : " + 
		    		deco.getNom() + " " + 
		    		deco.getId() + " " + 
		    		deco.getPositionX() + " " +
		    		deco.getPositionY() + " "); 
				*/
		    Monde m = client.getMonde();
		    for(int i=0; i<foretRecu.size();i++) {
		    	modele.objet.ObjetDecoratif arbreRecu = (modele.objet.ObjetDecoratif)foretRecu.get(i);
		    	client.modele.ObjetDecoratif arbre = new client.modele.ObjetDecoratif(client.getMonde(), arbreRecu);
		    	m.addAlentourDeco(arbre);
		    }
			//System.out.println("Autre Deco initialisée :" + m.alentoursDeco.size());
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("AutreDeco : clientAction Failed");
		}
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
	}
	
}
