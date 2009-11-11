package communication.actions;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import serveur.Serveur;
import client.Client;
import client.gui.fenetre.contenu.ContChatBox;

import communication.CommClient;
import communication.CommServeur;

public class SayClanAction extends StandardAction {

	public SayClanAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try { 
			String msg = ((String)getObject(java.lang.String.class, objet));
			String[] split = msg.split("!");
	
			client.modele.Personnage p = client.getPersonnage(new Integer(split[0]).intValue());
			ContChatBox ccb = commClient.getContChatBox();
			ccb.addText(ccb.textChatClan, p.getNom(), split[1]);

		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("Say : clientAction Failed");
		}
	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
		try {
			int id = serveur.getId(address, port);
			int idperso = serveur.getIdPersoFromIdClient(id); 	
			String text = (String)getObject(java.lang.String.class, objet);
			commServeur.notifierMessageClan(idperso, text);
		} catch (Exception ex) {
			ex.printStackTrace();	
			System.err.println("Say : serveurAction Failed");
		}
	}
	
}
