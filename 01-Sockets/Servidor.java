import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    static final int PORT = 7777;
    static final String HOST = "localhost";
    ServerSocket serverSocket=null;
    Socket clientSocket = null;
    private boolean end = false;
    
    public void connecta(){
        try{
            serverSocket = new ServerSocket(PORT);
            clientSocket = serverSocket.accept();
            System.out.println("Servidor en marxa a " + HOST + ":" + PORT);
            System.out.println("Esperant connexions a "  + HOST + ":" + PORT);
            System.out.println("Client connectat: /" + HOST);
            while (!end) {
                clientSocket = serverSocket.accept();
            }   
        } catch(IOException e){
            Logger.getLogger(Servidor.class.getName())
            .log(Level.SEVERE, null, e);;
        }
    }

    public void repDades(){
        try{
            BufferedReader br = new BufferedReader(
            new InputStreamReader(
                clientSocket.getInputStream()));
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println("Rebut: " + linea);
                }
                br.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void tanca() {
        try{
            System.out.println("Servidor tancat.");
            serverSocket.close();
            clientSocket.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.connecta();
        servidor.repDades();
        servidor.tanca();
    }
}
