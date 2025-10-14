
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

    private static final int PORT = 12345;

    public static void main(String[] args) {
        System.out.println("Starting Game Server...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }
}
