package com.mycompany.servermulticastudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
public class ClientMulticastUDP {

    //colore del prompt del Server
    public static final String ANSI_BLUE = "\u001B[34m";
    //colore del prompt del Client
    public static final String RED_BOLD = "\033[1;31m";
    //colore del prompt del gruppo
    public static final String GREEN_UNDERLINED = "\033[4;32m";
    //colore reset
    public static final String RESET = "\033[0m";
    
   private static boolean uscita() {
    // Crea un oggetto Scanner per leggere l'input da tastiera.
    Scanner scanner = new Scanner(System.in);
    String input = "";
    // Ciclo per ripetere la domanda fino a che l'utente non inserisce "s" o "n"
    while (true) {
        // Stampa un messaggio all'utente chiedendo se desidera uscire dall'applicazione.
        System.out.print("Vuoi uscire? (s/n): ");

        // Legge la riga di input inserita dall'utente.
        input = scanner.nextLine();

        // Confronta l'input dell'utente con la stringa "s" (per "sì") e "n" (per "no"), ignorando le differenze di maiuscolo/minisucolo.
        if (input.equalsIgnoreCase("s")) {
            return true;  // Restituisce true se l'utente desidera uscire.
        } else {
            System.out.println("Input non valido. Si prega di rispondere con 's' per sì o 'n' per no.");
        }
    }
}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //numero di porta server
        int port = 2000;
        //numero di porta del gruppo
        int portGroup = 1900;
        //indirizzo del server
        InetAddress serverAddress;
        //socket UDP
        DatagramSocket dSocket = null;
        //socket multicast UDP
        MulticastSocket mSocket = null;
        //indirizzo gruppo multicast UDP
        InetAddress group;

        //Datagramma UDP con la richiesta da inviare al server
        DatagramPacket outPacket;
        //Datagramma UDP di risposta ricevuto dal server
        DatagramPacket inPacket;

        //buffer di lettura
        byte[] inBuffer = new byte[256];
        byte[] inBufferG = new byte[1024];

        //messaggio di richiesta
        String messageOut = "Richiesta comunicazione";
        //messaggio di risposta
        String messageIn;

        try {
            System.out.println(RED_BOLD + "CLIENT UDP" + RESET);
            //1) RICHIESTA AL SERVER
            //si recupera l'IP del server UDP
            serverAddress = InetAddress.getLocalHost();
            System.out.println(RED_BOLD + "Indirizzo del server trovato!" + RESET);

            //istanza del socket UDP per la prima comunicazione con il server
            dSocket = new DatagramSocket();

            //si prepara il datagramma con i dati da inviare
            outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), serverAddress, port);

            //si inviano i dati
            dSocket.send(outPacket);
            System.out.println(RED_BOLD + "Richiesta al server inviata!" + RESET);

            //2) RISPOSTA DEL SERVER
            //si prepara il datagramma per ricevere dati dal server
            inPacket = new DatagramPacket(inBuffer, inBuffer.length);
            dSocket.receive(inPacket);

            //lettura del messaggio ricevuto e sua visualizzazione
            messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println(ANSI_BLUE + "Messaggio ricevuto dal server " + serverAddress + ":" + port + "\n\t" + messageIn + RESET);

            // Ricezione della citazione casuale dal server
            dSocket.receive(inPacket);
            messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println(GREEN_UNDERLINED + "Citazione ricevuta dal server: " + messageIn + RESET);


            //3) RICEZIONE MESSAGGIO DEL GRUPPO
            //istanza del Multicast socket e unione al gruppo
            mSocket = new MulticastSocket(portGroup);
            group = InetAddress.getByName("239.255.255.250");
            mSocket.joinGroup(group);

            //si prepara il datagramma per ricevere dati dal gruppo
            inPacket = new DatagramPacket(inBufferG, inBufferG.length);
            mSocket.receive(inPacket);

            //lettura del messaggio ricevuto e sua visualizzazione
            messageIn = new String(inPacket.getData(), 0, inPacket.getLength());

            System.out.println(GREEN_UNDERLINED + "Lettura dei dati ricevuti dai partecipanti al gruppo" + RESET);
            System.out.println(GREEN_UNDERLINED + "Messaggio ricevuto dal gruppo " + group
                    + ":" + portGroup + "\n\t" + messageIn + RESET);

            //uscita dal gruppo
            mSocket.leaveGroup(group);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientMulticastUDP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Errore di risoluzione");
        } catch (SocketException ex) {
            Logger.getLogger(ClientMulticastUDP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Errore di creazione socket");
        } catch (IOException ex) {
            Logger.getLogger(ClientMulticastUDP.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Errore di I/O");
        } finally {
            if (dSocket != null) {
                dSocket.close();
            }
            if (mSocket != null) {
                mSocket.close();
            }
        }
    }
}
