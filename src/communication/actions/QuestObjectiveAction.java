package communication.actions;

import modele.quete.ObjectifQuete;
import serveur.Serveur;
import client.Client;

import communication.CommClient;
import communication.CommServeur;
/**
 * @author gautieje
 *
 */
public class QuestObjectiveAction extends StandardAction  {

	public QuestObjectiveAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		super(type, priorite, key, address, port, objet);
	}
	
	public void clientAction(Client client, CommClient commClient) {
		try {
			ObjectifQuete objectif = (ObjectifQuete)getObject(ObjectifQuete.class, objet);
			if(objectif!=null) {
				System.out.println("[ObjectifQuete reçu]"+ objet.toString());
				ObjectifQuete objectifClient = client.perso.getQuete(objectif.getIdQuete()).getObjectif(objectif.getNumero());
				if ((objectif!=null)&&(objectif.isRealise())){
					objectifClient.setReussi();
					System.out.println("[ObjectifQuete reussi]"+ objectifClient.isRealise());
					System.out.println ("[Quete realisee ] :"+client.perso.getQuete(objectifClient.getIdQuete()).verifierQuete());
					//Prochaine ligne pas indispensable => Non verification du prochain objectif dans client
					//client.perso.getQuete(objectifClient.getIdQuete()).nextObjectif();
				}
			}else{
				System.err.println("[ObjectifQuete reçu mais non reconnu]"+objet.toString());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("QuestObjective : clientAction Failed");
		}

	}
	
	public void serveurAction(Serveur serveur, CommServeur commServeur) {
		try {
			commServeur.sendTo(type, address, port, objet);
		} catch (Exception ex) {
			System.err.println("QuestObjective: serveurAction Failed");
		}
	}

}