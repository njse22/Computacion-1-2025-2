package ui; 
import java.net.DatagramSocket; 
import java.net.DatagramPacket; 
import java.net.InetAddress; 

public class Main{
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()){

	    String msj = "Hola"; 

	    DatagramPacket packet = 
		new DatagramPacket(
			msj.getBytes(), msj.length()); 

	    socket.connect( 
		    InetAddress.getByName("192.168.131.200"), 5000);
	    socket.send(packet); 
        	
        } catch(Exception e){
        	e.printStackTrace();
        }
    }
}
