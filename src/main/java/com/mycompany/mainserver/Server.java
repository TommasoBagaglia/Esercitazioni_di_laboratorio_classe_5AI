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
// Definizione della classe Server
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

// Definizione della classe ClientHandler, implementa l'interfaccia Runnable
class ClientHandler implements Runnable {
    // Dichiarazione delle variabili di istanza
    private Socket clientSocket; // Socket del client
    private String clientName; // Nome del client

    // Costruttore della classe ClientHandler
    public ClientHandler(Socket clientSocket, String clientName) {
        this.clientSocket = clientSocket; // Inizializza la socket del client
        this.clientName = clientName; // Inizializza il nome del client
    }

    // Metodo run() che viene eseguito quando il gestore del client viene eseguito su un thread separato
    @Override
    public void run() {
        try {
            // Creazione dei flussi di input e output per la comunicazione con il client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in)); // Flusso di input da console

            String inputLine; // Variabile per memorizzare le righe di input dal client
            // Loop per leggere i messaggi inviati dal client
            while ((inputLine = in.readLine()) != null && !inputLine.equals("exit")) {
                System.out.println("Messaggio ricevuto da " + clientName + ": " + inputLine); // Stampa il messaggio ricevuto dal client
                System.out.print("Inserisci la risposta per " + clientName + ": "); // Richiesta di inserimento della risposta da parte dell'utente del server
                String serverResponse = consoleInput.readLine(); // Legge la risposta dall'input della console
                out.println("Risposta dal server per " + clientName + ": " + serverResponse); // Invia la risposta al client
            }

            System.out.println("Connessione chiusa con " + clientName); // Stampa un messaggio quando la connessione viene chiusa
            clientSocket.close(); // Chiude la socket del client
        } catch (IOException e) { // Gestione delle eccezioni di I/O
            System.out.println("Errore nella gestione del client " + clientName); // Stampa un messaggio di errore
        }
    }
}