package com.mycompany.mainserver;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int porta;

    // Costruttore che imposta la porta del server
    public Server(int porta) {
        this.porta = porta;
    }

    // Metodo per mettersi in ascolto di connessioni in entrata
    public void attendi() {
        try {
            serverSocket = new ServerSocket(porta); // Creazione del ServerSocket sulla porta specificata
            System.out.println("Server in ascolto sulla porta " + porta);
            clientSocket = serverSocket.accept(); // Accettazione della connessione dal client
            System.out.println("Connessione accettata da: " + clientSocket.getInetAddress());
            // In questo punto la connessione è stabilita e si può iniziare a comunicare con il client
            leggi(); // Metodo per leggere i dati inviati dal client
            scrivi("Ciao, sono il server!"); // Metodo per inviare una risposta al client
            chiudi(); // Chiusura della connessione
        } catch (IOException ex) {
            System.err.println("Errore nella fase di ascolto");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Metodo per scrivere dati al client (non implementato in questa versione)
    public void scrivi(String messaggio) {
        // Implementazione mancante
    }

    // Metodo per leggere dati inviati dal client
    public void leggi() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String messaggio = in.readLine(); // Legge una linea di testo inviata dal client
            System.out.println("Messaggio ricevuto dal client: " + messaggio);
        } catch (IOException e) {
            System.out.println("Errore nella lettura del messaggio.");
            e.printStackTrace();
        }
    }

    // Metodo per chiudere la connessione al client
    public void chiudi() {
        try {
            if (clientSocket != null) {
                clientSocket.close(); // Chiusura del socket del client
                System.out.println("Connessione chiusa con il client.");
            }
            if (serverSocket != null) {
                serverSocket.close(); // Chiusura del ServerSocket
                System.out.println("Server socket chiuso.");
            }
        } catch (IOException e) {
            System.out.println("Errore nella chiusura del socket.");
            e.printStackTrace();
        }
    }
}