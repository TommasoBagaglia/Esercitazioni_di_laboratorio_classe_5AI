package com.mycompany.mainclient;
public class MainClient {
    public static void main(String[] args) {
        Client client = new Client();
        client.connetti("localhost", 1234); // Connette il client al server
        if (client.isConnesso()) {
            client.iniziaComunicazione(); // Inizia la comunicazione con il server
        } else {
            System.out.println("Connessione al server non riuscita.");
        }
    }
}
