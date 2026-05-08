import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientXat {
    static final int PORT = ServidorXat.PORT;
    static final String HOST = ServidorXat.HOST;
    Socket clientSocket = null;
    ObjectOutputStream out;
    ObjectInputStream in;

    // Connexió al servidor y creació de fluxes
    public void connecta(){
        try{
            clientSocket = new Socket(HOST, PORT);
            System.out.println("Client connectat a " + HOST + ":" + PORT);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("Flux d'entrada i sortida creat.");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // metode que rep una string i l'envia al servidor
    public void enviarMissatge(String missatge){
        try{
            if (out!=null) {
                out.writeObject(missatge); // Enviar dades al servidor
                out.flush();
                System.out.println("Enviant missatge: " + missatge);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void tancarClient(){
        try{
            System.out.println("Tancant client...");
            out.close();
            clientSocket.close();
            System.out.println("Client tancat.");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientXat clientXat = new ClientXat();
        clientXat.connecta();
        FilLectorCX fil = new FilLectorCX(clientXat.in);
        fil.start();
        Scanner sc = new Scanner(System.in);
        try {
            Thread.sleep(100); // Espera a que el fil lector mostri "Escriu el teu nom:" abans de llegir
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String nom = sc.nextLine();
        clientXat.enviarMissatge(nom);
        String msg;
        System.out.print("Missatge ('sortir' per tancar): ");
        while (!(msg = sc.nextLine()).equals(ServidorXat.MSG_SORTIR)) {
            clientXat.enviarMissatge(msg);
            System.out.print("Missatge ('sortir' per tancar): ");
        }
        clientXat.enviarMissatge(ServidorXat.MSG_SORTIR);
        sc.close();
        clientXat.tancarClient();
    }

}
