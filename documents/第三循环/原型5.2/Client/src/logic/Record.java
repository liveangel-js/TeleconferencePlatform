package logic;

import java.io.*; 
import javax.sound.sampled.*; 
import java.net.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gyx
 */
class Capture implements Runnable {


    TargetDataLine line; 
    Thread thread; 
    Socket s; 
    BufferedOutputStream captrueOutputStream;


    Capture(Socket s){
        this.s=s; 
    }


    public void start() {
        thread = new Thread(this); 
        thread.setName("Capture"); 
        thread.start(); 
    }


    public void stop() { 
        thread = null; 
    }


    public void run() {
        try { 
            captrueOutputStream=new BufferedOutputStream(s.getOutputStream());
        } 
        catch (IOException ex) { 
            return; 
        }

        AudioFormat format =new AudioFormat(8000,16,2,true,true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class,format);

        try { 
            line = (TargetDataLine) AudioSystem.getLine(info); 
            line.open(format, line.getBufferSize()); 
        } catch (Exception ex) { 
            return; 
        }


        byte[] data = new byte[1024];
        int numBytesRead=0; 
        line.start();


        while (thread != null) { 
            numBytesRead = line.read(data, 0,1024);
            try { 
                captrueOutputStream.write(data, 0, numBytesRead);
            } 
            catch (Exception ex) { 
                break; 
            } 
        }


        line.stop(); 
        line.close(); 
        line = null;


        try { 
            captrueOutputStream.flush(); 
            captrueOutputStream.close(); 
        } catch (IOException ex) { 
            ex.printStackTrace(); 
        } 
    } 
}
