package model; 

public class Server implements IServer{

    private DataBase dataBase; 

    public Server(){
	dataBase = new DataBase(); 
    }


    @Override
    public void solveRequest(int clientId){
	dataBase.request(clientId);
	
    }
}
