package com.mycompany.mainserver;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

public class Server {
    private DatagramSocket socket; // Socket UDP per la comunicazione.
    private byte[] buf = new byte[256]; // Buffer per i dati in entrata e uscita.
    private Scanner scanner = new Scanner(System.in); // Scanner per leggere l'input da tastiera.

    // Costruttore che inizializza il socket del server sulla porta specificata.
    public Server(int porta) throws IOException {
        socket = new DatagramSocket(porta);
    }

    // Metodo per mettere il server in ascolto e permettere all'operatore di rispondere manualmente.
    public void attendi() throws IOException {
        System.out.println("Server in ascolto sulla porta " + socket.getLocalPort());

        while (true) { // Ciclo infinito per continuare a ricevere e rispondere ai messaggi.
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet); // Riceve il pacchetto dal client.
            String received = new String(packet.getData(), 0, packet.getLength()).trim(); // Estrae il messaggio dal pacchetto.

            System.out.println("Client: " + received); // Stampa il messaggio ricevuto dal client.

            // Controlla se il messaggio ricevuto è un comando di "chiudi" per terminare la sessione.
            if (received.equalsIgnoreCase("chiudi")) {
                System.out.println("Chiusura della sessione con il client.");
                continue; // Salta la risposta e torna ad ascoltare per il prossimo messaggio.
            }

            // Permette all'operatore del server di digitare una risposta.
            System.out.print("Risposta: ");
            String risposta = scanner.nextLine(); // Legge la risposta dall'input della tastiera.

            // Prepara e invia la risposta al client.
            buf = risposta.getBytes(); // Converte la risposta in byte.
            packet = new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort()); // Crea un nuovo pacchetto con la risposta.
            socket.send(packet); // Invia il pacchetto di risposta al client.
        }
    }

    // Metodo per chiudere lo scanner quando non è più necessario, per evitare perdite di risorse.
    public void close() {
        scanner.close();
    }
}