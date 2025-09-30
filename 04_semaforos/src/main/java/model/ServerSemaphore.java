package model; 
import java.util.concurrent.Semaphore;

public class ServerSemaphore implements IServer{

    private DataBase dataBase; 
    private Semaphore semaphore; 

    public ServerSemaphore(){
	dataBase = new DataBase(); 
	semaphore = new Semaphore(1);
    }

    @Override
    public void solveRequest(int clientId){
	try{

	    // primera operación 
	    semaphore.acquire();
	    // Antes de consumir el recurso, el semaforo lo bloquea con la 
	    // primera solicitud 
	    dataBase.request(clientId);
	}catch(InterruptedException e){
	    System.out.println("El cliente"+ clientId +"fue interrumpido ....");

	}
	finally{
	    // Al final de la operación liberamos el recurso 
	    // Ultima operación
	    semaphore.release();
	}
	
    }
}
