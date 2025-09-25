package ui;

import util.TCPConnection;

public class Main implements TCPConnection.Listener {
    public static void main(String[] args) {
	Main m = new Main(); 
    	TCPConnection connection = TCPConnection.getInstance(); 
	connection.initAsServer(5000);
	connection.setListener(m);
	connection.start();

    }

    @Override 
    public void onMessage(String msj){
	System.out.println(msj);
    }
}
