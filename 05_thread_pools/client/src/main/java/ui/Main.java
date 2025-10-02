package ui;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
	try {
	    //while (true) {
	    	
	    Socket socket = new Socket("127.0.0.1", 5000);
	    BufferedWriter writer = new BufferedWriter(
		    new OutputStreamWriter(socket.getOutputStream())
		    ); 

	    writer.write("Hola desde el cliente: " + socket.getLocalAddress());

	    Thread.sleep(1000);

	    writer.flush();
	     //}
	    writer.close();
	    socket.close();
	} catch (Exception e) {
		// TODO: handle exception
	}

    }
	
}
