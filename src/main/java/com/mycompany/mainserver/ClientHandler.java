package com.mycompany.mainserver;
/**
 *
 * @author tommy
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
class ClientHandler implements Runnable { // La classe implementa Runnable per essere eseguita su un thread.
    private Socket clientSocket; // Socket per la connessione con il client.
    private String clientName; // Nome identificativo del client.

    public ClientHandler(Socket clientSocket, String clientName) {
        this.clientSocket = clientSocket; // Inizializza la socket del client.
        this.clientName = clientName; // Inizializza il nome del client.
    }

    @Override
    public void run() { // Metodo run eseguito dal thread.
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Crea BufferedReader per leggere dal client.
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // Crea PrintWriter per scrivere al client.
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in)); // Crea BufferedReader per input da console del server.

            String inputLine; // Variabile per memorizzare l'input del client.
            while ((inputLine = in.readLine()) != null && !inputLine.equals("exit")) { // Continua a leggere fino a "exit".
                System.out.println("Messaggio ricevuto da " + clientName + ": " + inputLine); // Stampa il messaggio ricevuto.
                System.out.print("Inserisci la risposta per " + clientName + ": "); // Invita l'utente del server a rispondere.
                String serverResponse = consoleInput.readLine(); // Legge la risposta dal server.
                out.println("Risposta dal server per " + clientName + ": " + serverResponse); // Invia la risposta al client.
            }

            System.out.println("Connessione chiusa con " + clientName); // Stampa quando il client si disconnette.
            clientSocket.close(); // Chiude la socket del client.
        } catch (IOException e) {
            System.out.println("Errore nella gestione del client " + clientName); // Gestisce le eccezioni.
        }
    }
}