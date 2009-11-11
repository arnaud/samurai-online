package communication;

import client.Client;

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
import communication.actions.MoveAction;
import communication.actions.MoveInterAction;
import communication.actions.OrienterAction;
import communication.actions.PingAction;
import communication.actions.QuestObjectiveAction;
import communication.actions.SayAction;
import communication.actions.SayClanAction;
import communication.actions.SayTeamAction;
import communication.actions.SendQuestAction;
import communication.actions.StandardAction;
import communication.util.SyncVector;

public class ClientGestReceived extends Thread {
	private Client client;
	private SyncVector packetsRecus;
	private SyncVector recusNonLus;
	private CommClient commClient;
	//public modele.personne.Personnage personnage;
	private boolean authentifie = false;
	public boolean initialise = false;
		
	public ClientGestReceived(Client client, CommClient commClient) {
		this.client = client;
		this.commClient = commClient;
		this.packetsRecus = commClient.getPacketsRecus();
		this.recusNonLus = commClient.getRecusNonLus();
		this.setPriority(Thread.NORM_PRIORITY+1);
	}
	
	public void run() {
		while(true) {
			synchronized(recusNonLus) {
				if(!recusNonLus.isEmpty()) {
					StandardAction action = (StandardAction)recusNonLus.get(0);
					recusNonLus.remove(action);
					    		    		
					if(action.getKey()<0) {
						if(action.getType()==StandardAction.PING) {
							((PingAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.BONJOUR) {
							((BonjourAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.INITME) {
							((InitMeAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.SAY) {
							((SayAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.SAYCLAN) {
							((SayClanAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.SAYTEAM) {
							((SayTeamAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.MOVE && commClient.init_finished) {
							((MoveAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.ORIENTER && commClient.init_finished) {
							((OrienterAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.MOVEINTER) {
							((MoveInterAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.INITPERSO) {
							((InitPersoAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.INITINTER) {
							((InitInterAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.INITINTER) {
							((InitInterAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.SENDQUEST) {
							((SendQuestAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.QUESTOBJECTIVE) {
							((QuestObjectiveAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.AUTREPERSO) {
							((AutrePersoAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.AUTREDECO) {
							((AutreDecoAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.INITFINISHED) {
							((InitFinishedAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.AUTREBATI) {
							((AutreBatiAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.AUTREOUTIL) {
							((AutreOutilAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.AUTREARME) {
							((AutreArmeAction)action).clientAction(client, commClient);
						} else if(action.getType()==StandardAction.AUTREFORET) {
							((AutreForetAction)action).clientAction(client, commClient);
						} else {
							packetsRecus.add(action);
						}
					} 
				}
			}
		}
	}
	
	

	
	
}
