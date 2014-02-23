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
    int volume;


    Playback(Socket s){
        this.s=s; 
        volume=100;
    } 
    
    public void start() {
        thread = new Thread(this); 
        thread.setName("Playback"); 
        thread.start(); 
    }

    public void setVolume(int x){
        volume=x;
    }

    public void stop() { 
        thread = null; 
    }


    public void run() {
        AudioFormat format =AudioInfo.getAudioFormat();
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

        int bufferLengthInBytes =AudioInfo.cData;
        byte[] data = new byte[bufferLengthInBytes];
        int numBytesRead = 0; 
        line.start();


        while (thread != null) { 
            try{ 
                numBytesRead = playbackInputStream.read(data); 
                
                for(int i=0;i<numBytesRead;i++)
                    data[i]=(byte) ((int)data[i]*volume/100);
                
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