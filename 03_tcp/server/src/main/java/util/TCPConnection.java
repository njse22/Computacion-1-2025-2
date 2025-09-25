package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPConnection extends Thread{

    private static TCPConnection instance; 

    private Socket socket; 
    private Listener listener; 

    private TCPConnection(){}

    public static TCPConnection getInstance(){
	if(instance == null){
	    instance = new TCPConnection(); 
	}
	return instance; 
    }

    public void setListener(Listener listener){
	this.listener = listener;
    }

    public void initAsServer(int port){
	try{
	    socket = new ServerSocket(port).accept(); 
	}
	catch(IOException e){
	    e.printStackTrace();
	}
    }

    public void initAsClient(String ip, int port){
	try{
	    socket = new Socket(ip, port); 
	}
	catch(IOException e){
	    e.printStackTrace();
	}
    }

    @Override
    public void run(){
	// recepción 
	
	try{
	    // conectamos a Java con el Socket (NIC)
	    InputStream is = socket.getInputStream(); 

	    // Empaquetamos la información 
	    InputStreamReader isr = new InputStreamReader(is); 

	    // se la pasamos al objeto que nos ayuda a leer la 
	    // información 
	    BufferedReader reader = new BufferedReader(isr); 

	    String msj = reader.readLine(); 
	    System.out.println("TCPConnection::run::msj >>> " + msj + " " + socket.getLocalAddress());
	    listener.onMessage(msj);

	}catch(IOException e){

	}

    }


    public void sendMessage(String msj, String ipDest, int portDest){
        Thread sender = new Thread(
		() -> {
		    // enviar mensaje 
		    try {

			// nos conectamos con el otro nodo
			Socket remoteSockect = new Socket(ipDest, portDest);

			// nos conectamos con el socket (Java) 
			OutputStream os = remoteSockect.getOutputStream(); 

			// empaqueta la información 
			OutputStreamWriter osw = new OutputStreamWriter(os);

			// escribir el mensaje  
			BufferedWriter writer = new BufferedWriter(osw);
			writer.write(msj);

			writer.flush();
			writer.close();
			remoteSockect.close();

		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
		);
        sender.start();
    }

    public interface Listener{
	public void onMessage(String msj); 
    }



}



    // public void sendMessage(String msj, String ipDest, int portDest){
    //     SenderRunnable runnable = new SenderRunnable(); 
    //     Thread sender = new Thread(runnable);
    //     sender.start();
    // }


    // class SenderRunnable implements Runnable{

    //     @Override
    //     public void run(){
    //         // Envio
    //     }
    // }

