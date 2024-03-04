package com.mycompany.mainserver;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    private String nome;
    private String colore;
    private Socket socket;

    public Client(String nomeDefault, String coloreDefault) {
        this.nome = nomeDefault;
        this.colore = coloreDefault;
    }

    public void connetti(String nomeServer, int portaServer) {
        try {
            socket = new Socket(nomeServer, portaServer);
            System.out.println("Connesso al server: " + nomeServer + " sulla porta: " + portaServer);
        } catch (IOException e) {
            System.out.println("Impossibile connettersi al server: " + nomeServer);
            e.printStackTrace();
        }
    }

    public void scrivi(String messaggio) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(messaggio);
        } catch (IOException e) {
            System.out.println("Problema nell'invio del messaggio.");
            e.printStackTrace();
        }
    }

    public String leggi() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return in.readLine();
        } catch (IOException e) {
            System.out.println("Problema nella lettura del messaggio.");
            e.printStackTrace();
            return null;
        }
    }

    public void chiudi() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Problema nella chiusura del socket.");
            e.printStackTrace();
        }
    }
}