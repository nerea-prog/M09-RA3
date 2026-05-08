import java.io.ObjectInputStream;
import java.net.Socket;

public class FilServidorXat extends Thread {
    Socket socket = null;
    ObjectInputStream in = null;
    public FilServidorXat(Socket socket, ObjectInputStream in){
        this.socket = socket;
        this.in = in;
    }

    public void run(){
        try{
            String msg;
            while (true) {
                // Lectura continua de missatges
                msg = (String) in.readObject(); // Serveix per rebre dades del client
                System.out.print("Missatge ('sortir' per tancar): Rebut: " + msg + "\n");
                if (msg.equals(ServidorXat.MSG_SORTIR)) {
                    System.out.println("Fil de xat finalitzat.");
                    break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
