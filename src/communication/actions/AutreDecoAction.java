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

public class AutreDecoAction extends StandardAction {

	public AutreDecoAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try { 
			//modele.personne.Personnage persoRecu = (modele.personne.Personnage)getObject(modele.personne.Personnage.class, objet);
			ObjetDecoratif decoRecu = (ObjetDecoratif)getObject(ObjetDecoratif.class, objet);
			DisplaySystem display = client.getMonde().getDisplay();
			client.modele.ObjetDecoratif deco = new client.modele.ObjetDecoratif(client.getMonde(), decoRecu);
			//client.modele.ObjetDecoratif deco = new client.modele.ObjetDecoratif(client.getMonde(), "pont", "Pont");
			
		//	client.modele.Personnage perso = new client.modele.Personnage(client.getMonde(), persoRecu);
			
		    System.out.println("[Test]Deco recue : " + 
		    		deco.getNom() + " " + 
		    		deco.getId() + " " + 
		    		deco.getPositionX() + " " +
		    		deco.getPositionY() + " "); 
				
		    Monde m = client.getMonde();
		    m.addAlentourDeco(deco);
			System.out.println("Autre Deco initialisée :" + m.alentoursDeco.size());
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("AutreDeco : clientAction Failed");
		}
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
	}
	
}
