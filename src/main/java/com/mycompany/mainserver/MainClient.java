package com.mycompany.mainserver;
/**
 *
 * @author tommy
 */
import java.io.*;
import java.net.Socket;
//TCP multi-thread 
public class MainClient {
    public static void main(String[] args) { 
        String nomeServer = "localhost"; // Indirizzo del server.
        int portaServer = 12346; // Porta del server.

        try {
            Socket socket = new Socket(nomeServer, portaServer); // Crea una connessione al server.
            System.out.println("Connesso al server: " + nomeServer + " sulla porta: " + portaServer); // Stampa lo stato della connessione.
            System.out.println("se vuoi chiudere la connessione digitare exit"); // Istruzioni per chiudere la connessione.
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); // Input stream per l'input dell'utente.
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Per leggere le risposte dal server.
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true); // Per inviare comandi al server.

            String userInput; // Variabile per l'input dell'utente.
            while ((userInput = input.readLine()) != null && !userInput.equals("exit")) { // Continua fino al comando "exit".
                writer.println(userInput); // Invia l'input dell'utente al server.
                System.out.println(reader.readLine()); // Legge e stampa la risposta dal server.
            }

            socket.close(); // Chiude la connessione quando l'utente digita "exit".
        } catch (IOException e) {
            System.out.println("Errore durante la connessione al server."); // Stampa l'errore se non riesce a connettersi.
        }
    }
}