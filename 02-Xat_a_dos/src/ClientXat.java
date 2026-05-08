import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientXat {
    static final int PORT = ServidorXat.PORT;
    static final String HOST = ServidorXat.HOST;
    Socket clientSocket = null;
    PrintWriter out;
    ObjectInputStream in;

    public void connecta(){
        try{
            clientSocket = new Socket(HOST, PORT);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // metode que rep una string, l'accepta i mostra el missatge
    public void enviarMissatge(String missatge){
        if (out!=null) {
            System.out.println("Enviat al servidor: " + missatge);
            out.println(missatge);
        }
    }
    
    public void tancarClient(){
        try{
            System.out.println("Tancant client...");
            clientSocket.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientXat clientXat = new ClientXat();
        clientXat.connecta();
        FilLectorCX fil = new FilLectorCX(out);
        fil.start();
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        sc.close();
        clientXat.tancarClient();
    }

}
