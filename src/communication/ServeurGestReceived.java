package communication;

import serveur.Serveur;

import communication.actions.AskQuestAction;
import communication.actions.AutreArmeAction;
import communication.actions.AutreBatiAction;
import communication.actions.AutreDecoAction;
import communication.actions.AutreForetAction;
import communication.actions.AutreOutilAction;
import communication.actions.AutrePersoAction;
import communication.actions.BonjourAction;
import communication.actions.InitFinishedAction;
import communication.actions.InitInterAction;
import communication.actions.InitMeAction;
import communication.actions.InitPersoAction;
import communication.actions.InteractionAction;
import communication.actions.MoveAction;
import communication.actions.MoveInterAction;
import communication.actions.OrienterAction;
import communication.actions.PingAction;
import communication.actions.SayAction;
import communication.actions.SayClanAction;
import communication.actions.SayTeamAction;
import communication.actions.StandardAction;
import communication.util.SyncVector;

public class ServeurGestReceived extends Thread {
	private Serveur serveur;
	private SyncVector packetsRecus;
	private SyncVector recusNonLus;
	private CommServeur commServeur;
		
	public ServeurGestReceived(Serveur serveur, CommServeur commServeur) {
		this.serveur = serveur;
		this.commServeur = commServeur;
		this.packetsRecus = commServeur.getPacketsRecus();
		this.recusNonLus = commServeur.getRecusNonLus();
		this.setPriority(Thread.NORM_PRIORITY);
	}
	
	public void run() {
		while(true) {
			synchronized(recusNonLus) {
				if(!recusNonLus.isEmpty()) {
					StandardAction action = (StandardAction)recusNonLus.get(0);
					recusNonLus.remove(action);
					
				// 	on met à jour le last time connect du client
					synchronized(serveur.getClients()) {
						int idClient = serveur.getId(action.getAddress(),action.getPort());
						serveur.updateLastTimeConnect(idClient);
					}
					
					if(action.getKey()<0) {
						if(action.getType()==StandardAction.PING) {
							((PingAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.BONJOUR) {
							((BonjourAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.INITME) {
							((InitMeAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.SAY) {
							((SayAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.SAYCLAN) {
							((SayClanAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.SAYTEAM) {
							((SayTeamAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.MOVE) {
							((MoveAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.ORIENTER) {
							((OrienterAction)action).serveurAction(serveur, commServeur);
				        } else if(action.getType()==StandardAction.INTERACTION) {
				        	((InteractionAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.MOVEINTER) {
							((MoveInterAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.INITPERSO) {
							((InitPersoAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.INITINTER) {
							((InitInterAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.AUTREPERSO) {
							((AutrePersoAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.AUTREDECO) {
							((AutreDecoAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.INITFINISHED) {
							((InitFinishedAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.ASKQUEST) {
							((AskQuestAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.AUTREBATI) {
							((AutreBatiAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.AUTREOUTIL) {
							((AutreOutilAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.AUTREARME) {
							((AutreArmeAction)action).serveurAction(serveur, commServeur);
						} else if(action.getType()==StandardAction.AUTREFORET) {
							((AutreForetAction)action).serveurAction(serveur, commServeur);
						} else {
							packetsRecus.add(action);
						}
					} 
				}
			}
		}
	}
}
