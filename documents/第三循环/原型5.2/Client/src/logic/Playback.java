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
class Playback implements Runnable {


    final int bufSize = 16384; 
    SourceDataLine line; 
    Thread thread; 
    Socket s;


    Playback(Socket s){
        this.s=s; 
    } 
    
    public void start() {
        thread = new Thread(this); 
        thread.setName("Playback"); 
        thread.start(); 
    }


    public void stop() { 
        thread = null; 
    }


    public void run() {
        AudioFormat format =new AudioFormat(8000,16,2,true,true);
        BufferedInputStream playbackInputStream;
        try { 
            playbackInputStream=new BufferedInputStream(new AudioInputStream(s.getInputStream(),format,2147483647));
        } 
        catch (IOException ex) { 
            return; 
        }


        DataLine.Info info = new DataLine.Info(SourceDataLine.class,format);


        try { 
            line = (SourceDataLine) AudioSystem.getLine(info); 
            line.open(format, bufSize); 
        } catch (LineUnavailableException ex) { 
            return; 
        }


        byte[] data = new byte[1024];
        int numBytesRead = 0; 
        line.start();


        while (thread != null) { 
            try{ 
                numBytesRead = playbackInputStream.read(data); 
                line.write(data, 0,numBytesRead); 
            } catch (IOException e) { 
            break; 
            } 
        }


        if (thread != null) { 
            line.drain(); 
        }


        line.stop(); 
        line.close(); 
        line = null; 
    }
}