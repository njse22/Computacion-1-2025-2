package model; 

public class Client implements Runnable {

    private int id; 
    private IServer server; 

    public Client(int id, IServer server){
	this.id = id; 
	this.server = server; 

    }

    @Override
    public void run(){

	System.out.println("El clinte " + id +" esta solicitando el recurso ...");

	server.solveRequest(id); 

    }


}
