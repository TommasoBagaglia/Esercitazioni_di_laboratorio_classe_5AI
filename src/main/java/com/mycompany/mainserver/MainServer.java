package com.mycompany.mainserver;
/**
 *
 * @author tommy
 */
//TCP multi-thread 
public class MainServer {
    public static void main(String[] args) {
        int porta = 12346; // Imposta la porta su cui il server ascolterà.
        Server server = new Server(porta); // Crea un'istanza della classe Server con la porta specificata.
        server.avvia(); // Chiama il metodo avvia su server per iniziare ad accettare connessioni.
    }
}