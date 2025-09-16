import java.io.IOException;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.DatagramPacket;

public class Sender {

    public static void main(String[] args){
	try (DatagramSocket socket = new DatagramSocket() ) {

	    // Mensaje a enviar 
	    String msj = "Hola desde el Sender"; 

	    // Definir el paquete donde se almacena 
	    // la información de entrada 
	    DatagramPacket packet = 
		new DatagramPacket(
			msj.getBytes(), // mensaje | data
			msj.length()	// tamaño del mensaje
			); 

	    // con quien nos estamos conectando 
	    socket.connect(InetAddress.getByName("192.168.131.23"), 5000);
	    
	    // enviamos la información 
	    socket.send(packet);



	}
	catch(SocketException e){
	    e.printStackTrace();
	}
	catch(IOException e){
	    e.printStackTrace();
	}

    }
}
