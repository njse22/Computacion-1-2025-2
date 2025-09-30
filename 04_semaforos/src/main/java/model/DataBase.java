package model; 

public class DataBase{
    public void request(int clientId){
	System.out.println("Client: " + clientId + " doing request ...");

	try {
		Thread.sleep(1000); 
	} catch(Exception e){
		e.printStackTrace();
	}
	System.out.println("Fin de la consulta del cliente: "+ clientId);
    }
}
