
import java.io.IOException;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.DatagramPacket;

public class Reciver {

    public static void main(String[] args){
	//				  inicializo un puerto 
	//				  de escucha por UDP 
	//				  port = 5000
	try (DatagramSocket socket = new DatagramSocket(5000) ) {

	    // Crear un buffer para almacenar la información
	    // de entrada
	    byte[] buffer = new byte[1024]; 

	    // Definir el paquete donde se almacena 
	    // la información de entrada 
	    DatagramPacket packet = 
		new DatagramPacket(buffer, buffer.length); 

	    // Conectarse con la NIC -> guarda la información
	    // en el objeto packet 
	    socket.receive(packet);

	    // decodificar el mensaje 
	    String msj = new String(packet.getData()).trim(); 
	    System.out.println(packet.getAddress() + " " + msj);



	}
	catch(SocketException e){
	    e.printStackTrace();
	}
	catch(IOException e){
	    e.printStackTrace();
	}

    }
}
