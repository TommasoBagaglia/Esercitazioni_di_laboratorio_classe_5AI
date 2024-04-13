package com.mycompany.mainserver;
/**
 *
 * @author tommy
 */
import java.io.*;
import java.net.Socket;
//TCP multi-thread 
public class Client {
    private Socket socket; // Variabile privata per mantenere il riferimento al socket del client.

    
    // Metodo per connettersi al server specificato
    public void connetti(String nomeServer, int portaServer) {
        try {
            socket = new Socket(nomeServer, portaServer); // Crea una nuova connessione socket al server.
            System.out.println("Connesso al server: " + nomeServer + " sulla porta: " + portaServer); // Stampa lo stato della connessione.
        } catch (IOException e) {
            System.out.println("Impossibile connettersi al server: " + nomeServer); // Stampa un errore se la connessione fallisce.
        }
    }

    
    // Metodo per verificare se il client è connesso al server
    public boolean isConnesso() {
        return socket != null && socket.isConnected(); // Restituisce true se il socket è connesso, altrimenti false.
    }

    
    // Metodo per scrivere un messaggio al server
    public void scrivi(String messaggio) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Crea un PrintWriter per inviare messaggi al server.
            out.println(messaggio); // Invia il messaggio al server.
        } catch (IOException e) {
            System.out.println("Problema nell'invio del messaggio."); // Stampa un errore se non è possibile inviare il messaggio.
        }
    }

    
    // Metodo per leggere una risposta dal server
    public String leggi() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Crea un BufferedReader per ricevere messaggi dal server.
            return in.readLine(); // Legge una linea di testo dal server e la restituisce.
        } catch (IOException e) {
            System.out.println("Problema nella lettura del messaggio."); // Stampa un errore se non è possibile leggere il messaggio.
            return null; // Restituisce null in caso di errore.
        }
    }

    
    // Metodo per chiudere la connessione al server
    public void chiudi() {
        try {
            if (socket != null) {
                socket.close(); // Chiude il socket.
                System.out.println("Connessione chiusa con il server."); // Stampa un messaggio di conferma chiusura.
            }
        } catch (IOException e) {
            System.out.println("Problema nella chiusura del socket."); // Stampa un messaggio di errore in caso di problemi nella chiusura.
        }
    }
}