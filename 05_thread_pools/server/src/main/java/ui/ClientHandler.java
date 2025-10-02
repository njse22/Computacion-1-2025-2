package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket; 

    public ClientHandler(Socket socket){
	this.socket = socket; 
    }

    @Override
    public void run(){
	try {
	    BufferedReader reader = new BufferedReader(
		    new InputStreamReader(socket.getInputStream())
		    ); 

	    String msj =  reader.readLine();
	    System.out.println("El cliente: " + socket.getInetAddress() 
		    + " por el puerto: " + socket.getPort() + " " + msj);
	} catch (Exception e) {
		// TODO: handle exception
		
	}

    }
	
}
