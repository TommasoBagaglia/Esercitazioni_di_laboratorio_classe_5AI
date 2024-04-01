package com.mycompany.mainclient;
import java.io.*;
import java.net.Socket;
public class Client {
    private Socket socket; // Socket per la connessione al server

    // Metodo per connettersi al server specificando indirizzo e porta
    public void connetti(String nomeServer, int portaServer) {
        try {
            socket = new Socket(nomeServer, portaServer);
            System.out.println("Connesso al server: " + nomeServer + " sulla porta: " + portaServer);
        } catch (IOException e) {
            System.out.println("Impossibile connettersi al server: " + nomeServer);
            e.printStackTrace();
        }
    }

    // Metodo per verificare se il client è connesso
    public boolean isConnesso() {
        return socket != null && socket.isConnected();
    }

    // Metodo per inviare un messaggio al server
    public void scrivi(String messaggio) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(messaggio); // Invia il messaggio
        } catch (IOException e) {
            System.out.println("Problema nell'invio del messaggio.");
            e.printStackTrace();
        }
    }

    // Metodo per leggere un messaggio dal server
    public String leggi() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return in.readLine(); // Legge la risposta
        } catch (IOException e) {
            System.out.println("Problema nella lettura del messaggio.");
            e.printStackTrace();
            return null;
        }
    }

    // Metodo per chiudere la connessione con il server
    public void chiudi() {
        try {
            if (socket != null) {
                socket.close(); // Chiude la connessione
                System.out.println("Connessione chiusa con il server.");
            }
        } catch (IOException e) {
            System.out.println("Problema nella chiusura del socket.");
            e.printStackTrace();
        }
    }

    // Metodo per gestire l'interazione con il server (invio di messaggi e ricezione delle risposte)
    public void iniziaComunicazione() {
        try (BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in))) {
            String inputUtente;
            String rispostaServer;

            while (true) {
                System.out.println("Scrivi un messaggio (digitare 'QUIT' per uscire): ");
                inputUtente = tastiera.readLine(); // Legge l'input dall'utente

                scrivi(inputUtente); // Invia l'input al server

                if (inputUtente.equalsIgnoreCase("QUIT")) {
                    break; // Esce se l'utente digita "QUIT"
                }

                rispostaServer = leggi(); // Legge la risposta del server
                System.out.println(rispostaServer); // Stampa la risposta
            }
        } catch (IOException e) {
            System.out.println("Errore durante la comunicazione con il server.");
            e.printStackTrace();
        } finally {
            chiudi(); // Chiude la connessione al termine della comunicazione
        }
    }
}
