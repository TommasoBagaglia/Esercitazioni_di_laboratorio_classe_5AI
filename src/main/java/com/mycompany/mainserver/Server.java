package com.mycompany.mainserver;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



class Server {
    private ServerSocket serverSocket;
    private int porta;

    public Server(int porta) {
        this.porta = porta;
    }

    public void avvia() {
        try {
            serverSocket = new ServerSocket(porta);
            System.out.println("Server in ascolto sulla porta " + porta);
            ExecutorService executor = Executors.newCachedThreadPool();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connessione accettata da: " + clientSocket.getInetAddress());

                // Passa un identificatore unico per il client al momento della creazione del gestore del client
                executor.submit(new ClientHandler(clientSocket, "Client" + clientSocket.getPort()));
            }
        } catch (IOException ex) {
            System.err.println("Errore nella fase di ascolto");
            ex.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private String clientName; // Aggiungi un campo per memorizzare il nome del client

    public ClientHandler(Socket clientSocket, String clientName) {
        this.clientSocket = clientSocket;
        this.clientName = clientName; // Memorizza il nome del client
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Messaggio ricevuto da " + clientName + ": " + inputLine);
                if (inputLine.equals("exit")) {
                    break;
                }
                System.out.print("Inserisci la risposta per " + clientName + ": ");
                String serverResponse = consoleInput.readLine();
                out.println("Risposta dal server per " + clientName + ": " + serverResponse);
            }

            System.out.println("Connessione chiusa con " + clientName);
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Errore nella gestione del client " + clientName);
            e.printStackTrace();
        }
    }
}