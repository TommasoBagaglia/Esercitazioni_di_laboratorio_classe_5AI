package com.mycompany.mainserver;
//TCP multi-thread 
public class MainServer {

    public static void main(String[] args) {
        int porta = 12346;
        Server server = new Server(porta);
        server.avvia();
    }
}