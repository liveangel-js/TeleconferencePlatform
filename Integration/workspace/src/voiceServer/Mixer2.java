/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package voiceServer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gyx
 */
public class Mixer2 implements Runnable{
    final static int cData=AudioInfo.cData;
    final static int MAXNUM=10;
    
    private Thread thread;
    private int userNumber;
    private int[] available;
    private Socket[] socket;
    private BufferedInputStream[] inputStream;
    private BufferedOutputStream[] outputStream;
    private byte[] sendData;
    private int[] tempData;
    private byte[][] data;
    private int n;

    public Mixer2(){
        userNumber=0;
        n=0;
        available=new int[MAXNUM];
        socket=new Socket[MAXNUM];
        inputStream=new BufferedInputStream[MAXNUM];
        outputStream=new BufferedOutputStream[MAXNUM];
        sendData=new byte[cData];
        tempData=new int[cData];
        data=new byte[MAXNUM][cData];
    }
    
    @Override
    public void run() {
        int i=0;
        int j=0;
        int numBytesRead=0;
        while(thread!=null){
            if(n==0){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Mixer2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for(j=0;j<cData;j++)
            	tempData[j]=0;
            
            for(i=0;i<MAXNUM;i++){
                if(available[i]==1){
                    try {
                        numBytesRead=inputStream[i].read(data[i]);
                        for(j=0;j<cData;j++)
                        	tempData[j]+=data[i][j];
                    }catch(SocketException ex){
                        someoneQuit(i);
                        ex.printStackTrace();
                    } 
                    catch (IOException ex) {
                        ex.printStackTrace();
                        someoneQuit(i);
                    }
                }
            }
            
            if(n>1)
            	for(j=0;j<cData;j++)
            		tempData[j]=tempData[j]/(n);
            for(j=0;j<cData;j++)
            	sendData[j]=(byte) tempData[j];

            for(i=0;i<MAXNUM;i++)
                if(available[i]==1)
                    try {
                        outputStream[i].write(sendData);
                        outputStream[i].flush();
                    } catch(SocketException ex){
                        someoneQuit(i);
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        someoneQuit(i);
                        ex.printStackTrace();
                    }
        }
    }
    
    public void start(){
        thread=new Thread(this);
        thread.setName("Mixer2");
        thread.start();
    }
    
    public void stop(){
        thread=null;
    }
    
    public void newSocket(Socket s){
        System.out.print("加入一个人");
        int i=0;
        for(;i<userNumber;i++){
            if(available[i]!=1){
                available[i]=1;
                socket[i]=s;
                n++;
                try {
                    inputStream[i]=new BufferedInputStream(s.getInputStream());
                    outputStream[i]=new BufferedOutputStream(s.getOutputStream());
                } catch (IOException ex) {
                    someoneQuit(i);
                    ex.printStackTrace();
                }
            }
        }
        if(i==userNumber){
            userNumber++;
            available[i]=1;
            socket[i]=s;
            n++;
            try {
                inputStream[i]=new BufferedInputStream(s.getInputStream());
                outputStream[i]=new BufferedOutputStream(s.getOutputStream());
            } catch (IOException ex) {
                someoneQuit(i);
                ex.printStackTrace();
            }
        }
    }

    private void someoneQuit(int i) {
        System.out.println(i+"离开了");
        available[i]=0;
        if(i==userNumber-1)
            userNumber--;
        n--;
    }
}
