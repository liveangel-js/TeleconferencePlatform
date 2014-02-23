/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gyx
 */
public class loggingServer implements Runnable{
    private Thread thread;

    @Override
    public void run() {
        ServerSocket ss=null;
        try {
            ss=new ServerSocket(6666);
        } catch (IOException ex) {
            Logger.getLogger(memberControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Socket ts=null;
        while(true){
            try{
                ts=ss.accept();        
                new checkServer(ts).start();
            }catch(Exception e){
                e.printStackTrace();
	    }
        }
    }

    void start() {
        thread=new Thread(this);
        thread.setName("Logging Server");
        thread.start();
    }
    
}
class checkServer implements Runnable{
    private Socket s;
    private Thread thread;

    checkServer(Socket ts) {
        this.s=ts;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            String id=(String) ois.readObject();
            String password=(String) ois.readObject();
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            if(check(id,password)){
                oos.writeObject(new Integer(1));
                System.out.println("+1");
            }
            else
                oos.writeObject(new Integer(0));
            ois.close();
            oos.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(checkServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(checkServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void start(){
        thread=new Thread(this);
        thread.setName("Logging Server");
        thread.start();
    }

    private boolean check(String id, String password) {
        return true;
    }

}