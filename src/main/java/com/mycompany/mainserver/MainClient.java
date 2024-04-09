package com.mycompany.mainserver;
import java.io.IOException;
//UDP
public class MainClient {
    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 1234); // Inizializza il client con l'indirizzo e la porta del server
            client.esegui(); // Avvia la comunicazione con il server
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}