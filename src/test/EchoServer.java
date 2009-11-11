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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class EchoServer
{
// Initialize Port number and Packet Size
static final int serverPort = 1026;
static final int packetSize = 1024;
public static void main(String args[]) throws ClassNotFoundException, IOException{
	
DatagramPacket packet;
DatagramSocket socket;

byte[] data,dataSend; // For data to be Sent in packets
int clientPort,packetSendSize;
InetAddress address;
String str;

socket = new DatagramSocket(serverPort);







for(;;){
data = new byte[packetSize];
// Create packets to recieve the message


packet = new DatagramPacket(data,packetSize);
System.out.println("Waiting to recieve the packets");
try{
// wait infinetely for arrive of the packet
socket.receive(packet);
}catch(IOException ie){
System.out.println(" Could not Recieve:"+ie.getMessage());
System.exit(0);
}
// get data about client in order to echo data back
address = packet.getAddress();
clientPort = packet.getPort();

// print string that was recieved on server's console
str = new String(data,0,0,packet.getLength());
System.out.println("Message :"+ str.trim());
System.out.println("From :"+address);
// echo data back to the client
// Create packets to send to the client

//serialisation
ByteArrayOutputStream fluxSortie = new ByteArrayOutputStream() ;
//on cree un ByteArrayOuputSream 
ObjectOutput s = new ObjectOutputStream(fluxSortie);
//on associe à ce ByteArrayOutputStream un flux de l'objet
UneDate [] dd = {new UneDate(25, 12, 98), new UneDate(15, 9, 57)};
//on cree un tableau des elements ke l'on veut envoyer
s.writeObject(dd);
//et on utilise writeObject() pour ecrire dans ce flux d'objets les objets ke l'on veut envoyer
s.flush();

System.out.println("Paquet transforme :" + fluxSortie.toString());
dataSend=fluxSortie.toByteArray();
packetSendSize=dataSend.length;
System.out.println("Paquet envoye :" + dataSend);
packet = new DatagramPacket(dataSend,packetSendSize,address,clientPort);

try{
// sends packet
socket.send(packet);
System.out.println("Message Envoye : " + data.toString());
}catch(IOException ex){
System.out.println("Could not Send"+ex.getMessage());
System.exit(0);
}
} // for loop
} // main
}
