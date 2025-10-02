package ui;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Main{
    public static void main(String[] args) {
	ExecutorService pool = Executors.newFixedThreadPool(1); 
	Semaphore semaphore = new Semaphore(1); 
    	try {
	    // Gestion de conexiones desde el servidor
	    System.out.println("Esperando conexión de un cliente");
	    ServerSocket serverSocker = new ServerSocket(5000);

	    while (true) {
		// estoy aceptando conexiones del cliente
		Socket socket = serverSocker.accept();
		System.out.println("El cliente: " + socket.getInetAddress()
			+ " Se ha conectado ...");
		semaphore.acquire();
		pool.execute(new ClientHandler(socket));
		
		System.out.println("La conexión ha terminado ...");
		semaphore.release();
	    }

	} catch (Exception e) {
    		// TODO: handle exception
		pool.shutdown();
    	}
    }
}
