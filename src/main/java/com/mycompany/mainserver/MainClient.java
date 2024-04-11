package com.mycompany.mainserver;
import java.io.*;
import java.net.Socket;
//TCP multi-thread 
public class MainClient {
    public static void main(String[] args) {
        String nomeServer = "localhost";
        int portaServer = 12346;

        try {
            Socket socket = new Socket(nomeServer, portaServer);
            System.out.println("Connesso al server: " + nomeServer + " sulla porta: " + portaServer);
            System.out.println("se vuoi chiude la connesione digitare exit");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String userInput;
            while ((userInput = input.readLine()) != null && !userInput.equals("exit")) {
                writer.println(userInput);
                System.out.println(reader.readLine());
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Errore durante la connessione al server.");
        }
    }
}
