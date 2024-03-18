package com.mycompany.mainserver;
public class MainClient {
    public static void main(String[] args) {
        Client client = new Client(); // Creazione del client
        client.connetti("localhost", 1234); // Connessione al server locale sulla porta 1234
        if (client.isConnesso()) { // Verifica se la connessione è stata stabilita con successo
            client.scrivi("Ciao, sono il client!"); // Invio di un messaggio al server
            String risposta = client.leggi(); // Lettura della risposta dal server
            System.out.println("Risposta dal server: " + risposta); // Stampare la risposta dal server
            client.chiudi(); // Chiusura della connessione
        } else {
            System.out.println("Connessione al server non riuscita.");
        }
    }
}
