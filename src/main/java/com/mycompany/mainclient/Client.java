package com.mycompany.mainclient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
//day time
public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public void connetti(String nomeServer, int portaServer) throws IOException {
        socket = new Socket(nomeServer, portaServer); // Si connette al server
        System.out.println("Connesso al server: " + nomeServer + " sulla porta: " + portaServer);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Per leggere le risposte dal server
        out = new PrintWriter(socket.getOutputStream(), true); // Per inviare messaggi al server

        // Thread per leggere e stampare le risposte dal server
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    System.out.println("Connessione chiusa.");
                }
            }
        }).start();
    }

    public void scrivi(String messaggio) {
        out.println(messaggio); // Invia un messaggio al server;
    }

    public void chiudi() throws IOException {
        socket.close(); // Chiude la connessione con il server
    }
}
