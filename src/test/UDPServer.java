package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

	 class UDPServer
	 {
	   public static void main(String[] args)
	   {
         byte [] buffer = new byte[1024];
		 String myMessage ;

		 try
		 {
			// Create a Datagram Socket
			DatagramSocket socket = new DatagramSocket(Integer.parseInt(args[0]));

			// Create an empty Datagram Packet 
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length );

			// receive request from client and get client info
			socket.receive(packet);
			InetAddress client = packet.getAddress();
			int client_port = packet.getPort();
			System.out.println(" Received "+new String(buffer)+" from "+client);

			// send some data to the client
			myMessage = "Here is your stuff";
		    buffer = myMessage.getBytes("US-ASCII") ;
			packet = new DatagramPacket(buffer, buffer.length, client, client_port);
			socket.send(packet);
		 }
		 catch(UnknownHostException e){ System.out.println(e); }
		 catch(IOException e){ System.out.println(e); }
	   }
	 }



