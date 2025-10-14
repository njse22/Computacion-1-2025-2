import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to game server.");

            // Thread to read messages from the server
            Thread serverListener = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Lost connection to the server.");
                }
            });
            serverListener.start();

            // Main thread to send user input to the server
            System.out.println("Enter commands to play:");
            while (scanner.hasNextLine()) {
                String userInput = scanner.nextLine();
                out.println(userInput);
            }

        } catch (IOException e) {
            System.err.println("Couldn't connect to the server: " + e.getMessage());
        }
    }
}
