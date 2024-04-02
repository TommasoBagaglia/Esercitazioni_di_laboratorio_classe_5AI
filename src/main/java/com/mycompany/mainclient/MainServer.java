package com.mycompany.mainclient;
public class MainServer {
    public static void main(String[] args) {
        int porta = 12346; // Scegli una porta su cui il server dovrà ascoltare le connessioni in entrata

        Server server = new Server(porta); // Crea un'istanza del server sulla porta specificata
        System.out.println("Avvio del server sulla porta " + porta);
        server.avvia(); // Avvia il server per metterlo in ascolto delle connessioni in arrivo
    }
}
