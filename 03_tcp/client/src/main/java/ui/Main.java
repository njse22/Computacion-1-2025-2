package ui;

import util.TCPConnection;

public class Main {
    
    public static void main(String[] args) {
    	TCPConnection connection = TCPConnection.getInstance(); 
// 	connection.initAsClient("127.0.0.1", 5000);
	connection.sendMessage("Hola desde el cliente", "127.0.0.1", 5000);

    }
}
