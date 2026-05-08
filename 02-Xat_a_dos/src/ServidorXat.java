import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorXat {
    static final int PORT = 9999;
    static final String HOST = "localhost";
    public static final String MSG_SORTIR = "sortir";
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    
    public void iniciarServidor(){
        try {
            serverSocket = new ServerSocket(PORT);
            clientSocket = serverSocket.accept();
            System.out.println("Servidor en marxa a " + HOST + ":" + PORT);
            System.out.println("Esperant connexions a "  + HOST + ":" + PORT);
            System.out.println("Client connectat: " + clientSocket.getInetAddress());

        } catch (IOException e) {
            Logger.getLogger(ServidorXat.class.getName())
            .log(Level.SEVERE, null, e);
        }
    }


    public void pararServidor(){
        try{
            System.out.println("Servidor tancat.");
            serverSocket.close();
            clientSocket.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getNom(){
        return clientSocket.getInetAddress().toString();
    }

    public static void main(String[] args) {
        try{
            ServidorXat servidorXat = new ServidorXat();
            servidorXat.iniciarServidor();
            Scanner sc = new Scanner(System.in);
            PrintWriter out = new PrintWriter(
                servidorXat.clientSocket.getOutputStream(), true);
            FilServidorXat fil = new FilServidorXat(servidorXat.clientSocket);
            fil.start();
            String msg;
            while (!(msg = sc.nextLine()).equals(MSG_SORTIR)) {
                out.println(msg);
            }
            fil.join();
            servidorXat.pararServidor();
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
