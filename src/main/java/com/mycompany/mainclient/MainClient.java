package com.mycompany.mainclient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//day time
public class MainClient {
    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.connetti("localhost", 12346); // Si connette al server

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String inputLine;
            while (!(inputLine = userInput.readLine()).equalsIgnoreCase("exit")) {
                client.scrivi(inputLine); // Invia i messaggi digitati dall'utente al server
            }
            client.scrivi("exit"); // Invia "exit" al server per terminare la connessione
            client.chiudi(); // Chiude la connessione
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}