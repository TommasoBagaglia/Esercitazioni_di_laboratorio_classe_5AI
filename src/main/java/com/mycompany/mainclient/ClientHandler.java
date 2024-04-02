package com.mycompany.mainclient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
public class ClientHandler implements Runnable {
    private Socket clientSocket; // Il socket per la comunicazione con il client.

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket; // Inizializza il socket con quello passato dal server.
    }

    @Override
    public void run() {
        // Utilizza try-with-resources per assicurarsi che le risorse vengano chiuse automaticamente alla fine del blocco.
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Per leggere i dati in arrivo dal client.
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) { // Per inviare dati al client.

            String inputLine;
            // Continua a leggere i messaggi inviati dal client finché non riceve "exit" o la connessione viene interrotta.
            while ((inputLine = in.readLine()) != null) {
                if ("exit".equalsIgnoreCase(inputLine)) { // Se il client invia "exit", chiude la connessione.
                    out.println("Server: Connessione chiusa."); // Informa il client della chiusura della connessione.
                    break; // Esce dal ciclo per terminare il thread.
                } else if ("get time".equalsIgnoreCase(inputLine)) { // Se il client richiede l'ora ("get time").
                    // Ottiene l'ora corrente in formato RFC 3339, che include data, ora e fuso orario.
                    String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                    out.println("Server Time: " + currentTime); // Invia l'ora corrente al client.
                } else {
                    // Per qualsiasi altro input, effettua semplicemente un echo del messaggio ricevuto.
                    System.out.println("Client: " + inputLine); // Stampa a console il messaggio ricevuto dal client.
                    out.println("Server: Echo - " + inputLine); // Invia un echo del messaggio al client.
                }
            }
        } catch (IOException e) { // Gestisce eventuali eccezioni I/O che si verificano durante la comunicazione.
            System.err.println("Errore nella comunicazione con il client.");
        } finally {
            try {
                clientSocket.close(); // Assicura la chiusura del socket alla fine della comunicazione.
            } catch (IOException e) {
                System.err.println("Errore nella chiusura del socket con il client."); // Gestisce eventuali eccezioni durante la chiusura del socket.
            }
        }
    }
}