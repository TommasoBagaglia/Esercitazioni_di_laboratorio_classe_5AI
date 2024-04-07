package com.mycompany.mainserver;
import java.io.IOException;
public class MainServer {
    public static void main(String[] args) {
        try {
            int porta = 1234; // Porta su cui il server ascolterà le richieste.
            Server server = new Server(porta); // Crea un'istanza del server sulla porta specificata.
            server.attendi(); // Mette il server in ascolto per i messaggi in arrivo dai client.
        } catch (IOException e) {
            e.printStackTrace(); // Stampa l'errore se si verifica un'eccezione I/O.
        }
    }
}