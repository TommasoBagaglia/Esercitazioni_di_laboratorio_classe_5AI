package com.mycompany.mainclient;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class Server {
    private ServerSocket serverSocket; // ServerSocket usato per ascoltare le connessioni in entrata
    private Socket clientSocket; // Socket del client connesso
    private int porta; // Porta su cui il server è in ascolto

    // Costruttore del server che imposta la porta di ascolto
    public Server(int porta) {
        this.porta = porta;
    }

    // Metodo per mettersi in ascolto e accettare connessioni
    public void attendi() {
        try {
            serverSocket = new ServerSocket(porta); // Inizializzazione del ServerSocket
            System.out.println("Server in ascolto sulla porta " + porta);

            while (true) { // Ciclo infinito per accettare connessioni multiple
                clientSocket = serverSocket.accept(); // Accetta una connessione dal client
                System.out.println("Connessione accettata da: " + clientSocket.getInetAddress());

                leggi(); // Legge i messaggi dal client
                chiudi(); // Chiude la connessione con il client
            }
        } catch (IOException ex) {
            System.err.println("Errore nella fase di ascolto");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Metodo per inviare un messaggio al client
    public void scrivi(String messaggio) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(messaggio); // Invia il messaggio al client
        } catch (IOException e) {
            System.out.println("Errore nell'invio del messaggio.");
            e.printStackTrace();
        }
    }

    // Metodo per leggere i messaggi inviati dal client
    public void leggi() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String messaggio;
            // Continua a leggere i messaggi fino a quando il client invia "QUIT"
            while ((messaggio = in.readLine()) != null && !messaggio.equalsIgnoreCase("QUIT")) {
                System.out.println("Messaggio ricevuto dal client: " + messaggio);
                scrivi("risposta dal server: " + messaggio); // Invia una risposta echo al client
            }
            System.out.println("Client ha richiesto la chiusura della connessione.");
        } catch (IOException e) {
            System.out.println("Errore nella lettura del messaggio.");
            e.printStackTrace();
        }
    }

    // Metodo per chiudere la connessione con il client
    public void chiudi() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
                System.out.println("Connessione chiusa con il client.");
            }
        } catch (IOException e) {
            System.out.println("Errore nella chiusura del socket.");
            e.printStackTrace();
        }
    }
}
