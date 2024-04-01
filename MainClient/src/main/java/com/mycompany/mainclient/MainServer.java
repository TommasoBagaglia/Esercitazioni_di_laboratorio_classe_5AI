package com.mycompany.mainclient;
public class MainServer {
    public static void main(String[] args) {
        Server mioServer = new Server(1234); // Crea un'istanza del server sulla porta 1234
        mioServer.attendi(); // Mette il server in ascolto per le connessioni dei client
    }
}
