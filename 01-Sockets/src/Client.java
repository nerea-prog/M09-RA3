import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static final int PORT = Servidor.PORT;
    static final String HOST = Servidor.HOST;
    Socket socket = null;
    PrintWriter out = null;

    public void connecta(){
        try{
            socket = new Socket(HOST, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connectat a servidor en: " + HOST + ":" + PORT);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public void tanca(){
        try{
            out.close();
            socket.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // metode que rep una string, l'accepta i mostra el missatge
    public void envia(String missatge){
        if (out!=null) {
            System.out.println("Enviat al servidor: " + missatge);
            out.println(missatge);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connecta();
        client.envia("Prova d'enviament 1");
        client.envia("Prova d'enviament 2");
        client.envia("Adéu!");
        Scanner sc = new Scanner(System.in);
        System.out.println("Prem Enter per tancar el client...");
        sc.nextLine();
        client.tanca();
        System.out.println("Client tancat");
    }
}