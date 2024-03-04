package com.mycompany.mainserver;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static void main(String[] args) {
        Server mioServer = new Server(1024);
        mioServer.attendi();
        /*
        server.attendi(); // Attende una connessione dal client
        String messaggio = server.leggi(); // Legge il messaggio inviato dal client
        System.out.println("Messaggio ricevuto: " + messaggio);
        server.scrivi("Messaggio ricevuto: " + messaggio); // Invia una risposta al client
        server.chiudi(); // Chiude la connessione */
        
    }
}

