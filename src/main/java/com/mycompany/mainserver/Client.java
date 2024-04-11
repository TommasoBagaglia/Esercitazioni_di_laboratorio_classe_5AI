package com.mycompany.mainserver;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
//UDP
public class Client {
    private DatagramSocket socket;
    private InetAddress address;
    private byte[] buf = new byte[256];

    // Costruttore che inizializza il socket del client e l'indirizzo del server
    public Client(String indirizzo, int porta) throws IOException {
        socket = new DatagramSocket();
        address = InetAddress.getByName(indirizzo);
    }

    // Metodo per inviare messaggi al server e ricevere risposte
    public void esegui() throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("se vuoi smettere di comunicare con il server, digitare chiudi");
            System.out.print("Tu: ");
            
            String input = scanner.nextLine();
            buf = input.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 1234);
            socket.send(packet); // Invia il messaggio al server

            if (input.trim().equalsIgnoreCase("chiudi")) {
                break; // Esce dal ciclo se l'utente digita "chiudi"
            }

            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet); // Riceve la risposta dal server
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Server: " + received);
        }

        socket.close(); // Chiude il socket alla fine della sessione
        scanner.close(); // Chiude lo scanner
    }
}