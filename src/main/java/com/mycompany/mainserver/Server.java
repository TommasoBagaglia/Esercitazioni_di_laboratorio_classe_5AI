package com.mycompany.mainserver;
import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private ServerSocket serverSocket;
    private socket clientSocket;
    private int porta;

    public Server(int porta) {
        this.porta = porta;
    }

    public Socket attendi() {
        socket = null;
        try {
            serverSocket = new ServerSocket(porta);
            System.out.println("Server in ascolto");
            socket = serverSocket.accept();
            System.out.println("il client ha fatto richesta e la connesione è avvenuta");
        }catch (BindException ex){
            System.err.println("la porte del server è gia occupata");
        }
        catch (IOException ex) {
            System.err.println("errore nella fase di ascolto");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return socket;
    }
    public void scrivi() {
         
    }

    public void leggi() {
       
    }

    public void chiudi() {
       
    }
    public void termina(){
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
   
}
