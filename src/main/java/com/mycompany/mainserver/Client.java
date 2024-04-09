package com.mycompany.mainserver;
import java.io.*;
import java.net.Socket;
//TCP multi-thread 
public class Client {
    private Socket socket;

    // Metodo per connettersi al server specificato
    public void connetti(String nomeServer, int portaServer) {
        try {
            socket = new Socket(nomeServer, portaServer); // Connessione al server sulla porta specificata
            System.out.println("Connesso al server: " + nomeServer + " sulla porta: " + portaServer);
        } catch (IOException e) {
            System.out.println("Impossibile connettersi al server: " + nomeServer);
        }
    }

    // Metodo per verificare se il client è connesso al server
    public boolean isConnesso() {
        return socket != null && socket.isConnected();
    }

    // Metodo per scrivere un messaggio al server
    public void scrivi(String messaggio) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(messaggio); // Invia il messaggio al server
        } catch (IOException e) {
            System.out.println("Problema nell'invio del messaggio.");
        }
    }

    // Metodo per leggere una risposta dal server
    public String leggi() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return in.readLine(); // Legge una linea di testo inviata dal server
        } catch (IOException e) {
            System.out.println("Problema nella lettura del messaggio.");
            return null;
        }
    }

    // Metodo per chiudere la connessione al server
    public void chiudi() {
        try {
            if (socket != null) {
                socket.close(); // Chiusura del socket
                System.out.println("Connessione chiusa con il server.");
            }
        } catch (IOException e) {
            System.out.println("Problema nella chiusura del socket.");
        }
    }
}