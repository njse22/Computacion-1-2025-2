package ui;

import model.Client;
import model.IServer;
import model.Server;
import model.ServerSemaphore;

public class Main {

    public static void main(String[] args) {
	IServer server = new ServerSemaphore(); 
	for(int i = 0; i < 12; i++){
	    Client c = new Client(i, server); 
	    new Thread(c).start();
	}
    }
} 
