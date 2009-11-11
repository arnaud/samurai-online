package communication;

import java.net.DatagramPacket;
import java.net.InetAddress;

import communication.actions.AutrePersoAction;
import communication.actions.BonjourAction;
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


public class ServeurSend extends Thread{
	// Serveur send envoi les packets disponibles au format :
	// idpacket!ip_hote!port_hote!message
	private CommServeur commServeur;
	private SyncVector packets;
	
	public ServeurSend(CommServeur commServeur, SyncVector packets) {
		this.commServeur = commServeur;
		this.packets = packets;
		this.setPriority(Thread.NORM_PRIORITY);
	}
	
    private byte[] concat(byte[] a, byte[] b){
    	byte[] cat=new byte[a.length+b.length];

    	try {
    		for (int i=0;i<a.length;i++)
    			cat[i]=a[i];
    		for (int i=0;i<b.length;i++)
    			cat[a.length+i]=b[i]; 
    		}catch (Exception e){
    			System.err.println("Exception :" + e);   		
    		}
    	return cat;
    }

   public void run() {
	    while(true) {
	    	System.out.println("En attente de nouveaux packets à envoyer...");
	    	synchronized(packets) {
	    		while(packets.isEmpty());
	    	}
	    	
	    	while(!packets.isEmpty()) {
	    		StandardAction action = (StandardAction)packets.get(0);
	    		packets.remove(0);
	    		
	    		byte[] entete = new byte[2];
	    		entete[0] = action.getType();
	    		entete[1] = 0; // CRC Non encore utilisé
	    		byte[] data;
	    		if(action.getObjet()!=null) {
	    			data = concat(entete, action.getObjet());
	    		} else {
	    			data = entete;
	    		}
				//System.out.println("taille du flux sortant : " + data.length);

	    		try {
	    			InetAddress server_inet = InetAddress.getByName(action.getAddress());
	    			// create an object InetAddress (IP)
	    			
	    			DatagramPacket datagram =
	    				new DatagramPacket(data, data.length, server_inet, action.getPort());
	    			// create a datagram packet
	    			commServeur.getSocket().send(datagram);
	    			// send the datagram packet to server
	    		} catch (Exception e) {
	    			return;
	    		}
	    		yield();
	    	}
	    }
    }
}
