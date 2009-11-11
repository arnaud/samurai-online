package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

 class UDPClient
 {
   public static void main(String[] args)
   {
     byte [] buffer = new byte[1024];
	 String myMessage = "Send me some stuff";

	 try
	 {
		// get inetaddress of server
		InetAddress server = InetAddress.getByName(args[0]);

		// Create a Datagram Socket
		DatagramSocket socket = new DatagramSocket();

		// Create a Datagram Packet with server information
		// and fill it with the request mesage
		buffer = myMessage.getBytes("US-ASCII") ;
		DatagramPacket packet = 
		  new DatagramPacket(buffer, buffer.length, server, Integer.parseInt(args[1]));

		// send data to server
		socket.send(packet);

		// reset the buffer, reset the packet and get some data from server
		buffer = new byte[1024];
		packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);
		System.out.println(" Received "+new String(buffer)+" from "+server);

	 }
	 catch(UnknownHostException e){ System.out.println(e); }
	 catch(IOException e){ System.out.println(e); }
   }
 }



