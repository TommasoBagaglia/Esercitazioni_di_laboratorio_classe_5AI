package com.mycompany.mainserver;

public class MainServer {

    public static void main(String[] args) {
        int porta = 12346;
        Server server = new Server(porta);
        server.avvia();
    }
}