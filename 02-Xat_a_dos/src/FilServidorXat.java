import java.io.ObjectInputStream;
import java.net.Socket;

public class FilServidorXat extends Thread {
    Socket socket = null;
    public FilServidorXat(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try{
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            String msg;
            while (true) {
                msg = (String) in.readObject();
                
                if (msg.equals(ServidorXat.MSG_SORTIR)) {
                    break;
                }
                System.out.println(msg);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
