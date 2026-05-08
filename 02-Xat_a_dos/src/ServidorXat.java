import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            System.out.println("Servidor iniciat a " + HOST + ":" + PORT);
            clientSocket = serverSocket.accept();
            System.out.println("Client connectat: " + clientSocket.getInetAddress());

        } catch (IOException e) {
            Logger.getLogger(ServidorXat.class.getName())
            .log(Level.SEVERE, null, e);
        }
    }


    public void pararServidor(){
        try{
            serverSocket.close();
            clientSocket.close();
            System.out.println("Servidor aturat.");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getNom(ObjectInputStream in) throws Exception{
        String nom = (String) in.readObject(); // Serveix per rebre dades del servidor
        System.out.println("Nom rebut: " + nom);
        return nom;
    }

    public static void main(String[] args) {
        try{
            ServidorXat servidorXat = new ServidorXat();
            servidorXat.iniciarServidor();
            Scanner sc = new Scanner(System.in);
            // Canals entrada/salida del servidor
            ObjectOutputStream out = new ObjectOutputStream(
                servidorXat.clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(servidorXat.clientSocket.getInputStream());
            out.writeObject("Escriu el teu nom:"); // Envia dades al client
            out.flush();
            String nom = servidorXat.getNom(in);
            System.out.println("Fil de xat creat.");
            System.out.println("Fil de " + nom + " iniciat");
            // Fils encarregats de rebre missatges del client
            FilServidorXat fil = new FilServidorXat(servidorXat.clientSocket, in);
            fil.start();
            String msg;
            
            while (!(msg = sc.nextLine()).equals(MSG_SORTIR)) {
                out.writeObject(msg);
                out.flush();
            }
            fil.join();
            sc.close();
            servidorXat.pararServidor();
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
