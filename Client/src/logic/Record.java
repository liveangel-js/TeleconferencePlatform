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
    int volume;
    private boolean access;


    Capture(){
        volume=100;
        access=true;
    }
    
    Capture(Socket s){
        this.s=s; 
        volume=100;
        access=true;
    }

    public void setSocket(Socket x){
        this.s=x;
    }

    public void start() {
        thread = new Thread(this); 
        thread.setName("Capture"); 
        thread.start(); 
    }


    public void stop() { 
        thread = null; 
    }

    public void setVolume(int x){
        volume=x;
    }
    
    public void run() {
        try { 
            captrueOutputStream=new BufferedOutputStream(s.getOutputStream());
        } 
        catch (IOException ex) { 
            return; 
        }

        AudioFormat format =AudioInfo.getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class,format);

        try { 
            line = (TargetDataLine) AudioSystem.getLine(info); 
            line.open(format, line.getBufferSize()); 
        } catch (Exception ex) { 
            return; 
        }

        final int bufferLengthInBytes=AudioInfo.cData;
        final int cTIMES=AudioInfo.cTIMES;
        final int cD=AudioInfo.cD;
        int d=0,times=cTIMES;
        byte[] data = new byte[bufferLengthInBytes];
        int numBytesRead=0;
        line.start();


        while (thread != null) { 
            numBytesRead = line.read(data, 0,bufferLengthInBytes);
            try { 
                if(!access){
                    for(int i=0;i<numBytesRead;i++){
                        data[i]=0;
                    }
                }else{
                    d=0;
                    for(int i=0;i<numBytesRead;i++){
                        d+=data[i]*data[i];
                    }
                    if(d<cD && times<0){
                        for(int i=0;i<numBytesRead;i++)
                            data[i]=0;
                        d=0;
                    }else{
                        for(int i=0;i<numBytesRead;i++)
                            data[i]=(byte) ((int)data[i]*volume/100);
                        if(d>cD) times=cTIMES;
                    }
                    times--;
                }
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

    void setAccess(boolean x) {
        this.access=x;
    }
}
