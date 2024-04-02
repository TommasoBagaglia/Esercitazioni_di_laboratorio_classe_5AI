package com.mycompany.mainclient;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int porta;

    public Server(int porta) {
        this.porta = porta; // La porta su cui il server ascolterà le connessioni in entrata
    }

    public void avvia() {
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Server in ascolto sulla porta " + porta);
            ExecutorService executor = Executors.newCachedThreadPool(); // Crea un pool di thread per gestire più connessioni client

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Aspetta e accetta una connessione client
                System.out.println("Connessione accettata da: " + clientSocket.getInetAddress());
                executor.submit(new ClientHandler(clientSocket)); // Delega la gestione della connessione al ClientHandler in un nuovo thread
            }
        } catch (IOException ex) {
            System.err.println("Errore durante l'ascolto sulla porta " + porta);
        }
    }
}
