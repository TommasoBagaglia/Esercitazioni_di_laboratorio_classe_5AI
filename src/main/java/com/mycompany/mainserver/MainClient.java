package com.mycompany.mainserver;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainClient {
    public static void main(String[] args) {
        Client client = new Client("DefaultName", "DefaultColor");
        client.connetti("localhost", 1234);
        client.scrivi("Ciao, sono il client!");
        String risposta = client.leggi();
        System.out.println("Risposta dal server: " + risposta);
        client.chiudi();
    }
}
