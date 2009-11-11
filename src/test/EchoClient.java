/*
 * Created on 16 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

/**
 * @author toto
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class EchoClient{
	
static final int serverPort = 1026;
static final int packetSize = 1024;
public static void main(String args[]) throws ClassNotFoundException, IOException{
DatagramSocket socket; // How we send packets

DatagramPacket packet; // what we send it in
InetAddress address; // Where to send
String messageSend; // Message to be send
String messageReturn; // What we get back from the Server


byte[] data;
// Checks for the arguments that sent to the java interpreter
// Make sure command line parameters correctr
if(args.length != 2){
System.out.println("Usage Error :Java EchoClient < Servername> < Message>");
System.exit(0);
}
// Gets the IP address of the Server
address = InetAddress.getByName(args[0]);
socket = new DatagramSocket();
data = new byte[packetSize];


messageSend = new String(args[1]);
messageSend.getBytes(0,messageSend.length(),data,0);
// remember datagrams hold bytes
packet = new
DatagramPacket(data,data.length,address,serverPort);
System.out.println(" Trying to Send the packet ");
try{
// sends the packet

socket.send(packet);
}catch(IOException ie){
System.out.println("Could not Send :"+ie.getMessage());
System.exit(0);
}
//packet is reinitialzed to use it for recieving
packet = new DatagramPacket(data,data.length);
try{
// Recieves the packet from the server
socket.receive(packet);
}catch(IOException iee){
System.out.println("Could not recieve :"+iee.getMessage() );
System.exit(0);
}
// display message recieved


//deserialisation
System.out.println("Paquet recu :" +packet.getData());
ByteArrayInputStream  fluxEntree = new ByteArrayInputStream(packet.getData());
System.out.println("Paquet transforme :" + fluxEntree.toString());
ObjectInput t = new ObjectInputStream(fluxEntree);

UneDate date[] = (UneDate[])t.readObject();

for (int i=0; i<date.length ; i++)
   System.out.println(date[i].jour + " " + date[i].mois + " " + date[i].annee);

} // main
} // Class EchoClient