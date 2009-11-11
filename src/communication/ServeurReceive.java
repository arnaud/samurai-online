package communication;

import java.net.DatagramPacket;

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

public class ServeurReceive extends Thread{
		private CommServeur commServeur;
		private SyncVector packets;
		
		public ServeurReceive(CommServeur commServeur, SyncVector packets) {
			this.commServeur = commServeur;
			this.packets = packets;
		}
		
		public byte[] getBytes(byte[] bytes, int deb, int fin) {
			byte[] nbytes = null;
			if(deb!=fin) {
				nbytes = new byte[fin-deb+1];
				for(int i=0; i<nbytes.length; i++) {
					nbytes[i] = bytes[i+deb];
				}
			}
			return nbytes;
		}
	
	   public void run() {
		   byte[] buffer = new byte[4096*4096];
	        // create a buffer to receive the message
		   
	        while(true) {
	        	try {
	                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
	                // create a datagram packet
	                commServeur.getSocket().receive(datagram);
	                // read from the datagramsocket (UDP) - it blocks this thread
	                byte[] bytes = datagram.getData();
	                
	                //System.out.println("Message reçu de :" + datagram.getAddress().getHostAddress());
	                
	                // bytes[0] contains the type
	                // bytes[1] contains the CRC
	                // bytes[2..n] contains a serialized Object
	                byte stdPrio = 5;
	                	                
	                if(bytes[0] == StandardAction.PING) {
	                	PingAction action = new PingAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.MOVE) {
	                	MoveAction action = new MoveAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.ORIENTER) {
	                	OrienterAction action = new OrienterAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.BONJOUR) {
	                	BonjourAction action = new BonjourAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.INITME) {
	                	InitMeAction action = new InitMeAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.INTERACTION) {
	                	InteractionAction action = new InteractionAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.SAY) {
	                	SayAction action = new SayAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.SAYCLAN) {
	                	SayClanAction action = new SayClanAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.SAYTEAM) {
	                	SayTeamAction action = new SayTeamAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.MOVEINTER) {
	                	MoveInterAction action = new MoveInterAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.INITPERSO) {
	                	InitPersoAction action = new InitPersoAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.INITINTER) {
	                	InitInterAction action = new InitInterAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.AUTREPERSO) {
	                	AutrePersoAction action = new AutrePersoAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.AUTREDECO) {
	                	AutreDecoAction action = new AutreDecoAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.INITFINISHED) {
	                	InitFinishedAction action = new InitFinishedAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.ASKQUEST) {
	                	AskQuestAction action = new AskQuestAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.AUTREBATI) {
	                	AutreBatiAction action = new AutreBatiAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.AUTREARME) {
	                	AutreArmeAction action = new AutreArmeAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.AUTREOUTIL) {
	                	AutreOutilAction action = new AutreOutilAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                } else if(bytes[0] == StandardAction.AUTREFORET) {
	                	AutreForetAction action = new AutreForetAction(bytes[0], stdPrio, -1, datagram.getAddress().getHostAddress(), 
	                			datagram.getPort(), getBytes(bytes, 2,datagram.getLength()));
	                	packets.add(action);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	                return;
	            }
	        }
	    }
}
