import java.io.ObjectInputStream;

public class FilLectorCX extends Thread {
    private ObjectInputStream in;
    public FilLectorCX(ObjectInputStream in){
        this.in = in;
    }

    @Override
    public void run(){
        try{
            String msg;
            System.out.println("Missatge ('sortir' per tancar): Fil de lectura iniciat");
            // Lectura de missatges
            while (true) {
                msg = (String) in.readObject(); // Serveix per rebre dades del servidor
                if (msg.equals(ServidorXat.MSG_SORTIR)) {
                    break;
                }
                System.out.println("Rebut: " + msg);
            }
        } catch (Exception e){
            System.out.println("El servidor ha tancat la connexió.");
        }
    }
}
