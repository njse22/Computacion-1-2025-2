package ui; 

import java.net.DatagramSocket;
import java.net.DatagramPacket;

public class Main{
    public static void main(String[] args) {
        
	try (DatagramSocket socket = new DatagramSocket(5000)){
	    byte[] buffer = new byte[1024]; 

	    DatagramPacket packet = new DatagramPacket(
		    buffer, buffer.length
		    ); 

	    socket.receive(packet); 

	    String msj = new String(packet.getData()).trim(); 

	    System.out.println(
		    packet.getAddress() + " " + msj);

		
	} catch(Exception e) {
	}
    }
}
