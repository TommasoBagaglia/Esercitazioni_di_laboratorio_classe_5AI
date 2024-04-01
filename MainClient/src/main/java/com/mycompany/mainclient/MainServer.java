package com.mycompany.mainclient;
public class MainServer {
    public static void main(String[] args) {
        Server mioServer = new Server(1234);
        mioServer.attendi();
    }
}
