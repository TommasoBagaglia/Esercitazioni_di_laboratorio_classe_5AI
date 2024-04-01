package com.mycompany.mainclient;
public class MainClient {
    public static void main(String[] args) {
        Client client = new Client();
        client.connetti("localhost", 1234);
        if (client.isConnesso()) {
            client.scrivi("Ciao, sono il client!");
            String risposta = client.leggi();
            System.out.println("Risposta dal server: " + risposta);
            client.chiudi();
        } else {
            System.out.println("Connessione al server non riuscita.");
        }
    }
}
