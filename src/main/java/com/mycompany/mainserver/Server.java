package com.mycompany.mainserver;
/**
 *
 * @author tommy
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//TCP multi-thread 
class Server {
    // Dichiarazione delle variabili di istanza
    private ServerSocket serverSocket; // Socket del server
    private int porta; // Numero di porta su cui il server ascolta

    // Costruttore della classe Server
    public Server(int porta) {
        this.porta = porta; // Inizializza il numero di porta
    }

    // Metodo per avviare il server
    public void avvia() {
        try {
            // Istanzia un oggetto ServerSocket sulla porta specificata
            serverSocket = new ServerSocket(porta);
            System.out.println("Server in ascolto sulla porta " + porta); // Stampa un messaggio di avviso
            ExecutorService executor = Executors.newCachedThreadPool(); // Crea un pool di thread per gestire i client

            // Loop infinito per accettare le connessioni dei client
            while (true) {
                // Accetta la connessione del client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connessione accettata da: " + clientSocket.getInetAddress()); // Stampa l'indirizzo IP del client

                // Passa un identificatore unico per il client al momento della creazione del gestore del client
                executor.submit(new ClientHandler(clientSocket, "Client" + clientSocket.getPort()));
            }
        } catch (IOException ex) { // Gestione delle eccezioni di I/O
            System.err.println("Errore nella fase di ascolto"); // Stampa un messaggio di errore
        }
    }
}
