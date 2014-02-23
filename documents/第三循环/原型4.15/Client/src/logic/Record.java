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


    Capture(Socket s){//构造器 取得socket以获得网络输出流 
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
            captrueOutputStream=new BufferedOutputStream(s.getOutputStream());//建立输出流 此处可以加套压缩流用来压缩数据 
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


        byte[] data = new byte[1024];//此处的1024可以情况进行调整，应跟下面的1024应保持一致 
        int numBytesRead=0; 
        line.start();


        while (thread != null) { 
            numBytesRead = line.read(data, 0,1024);//取数据（1024）的大小直接关系到传输的速度，一般越小越快， 
            try { 
                captrueOutputStream.write(data, 0, numBytesRead);//写入网络流 
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

class C{
    public static void main(String args[]){
        Socket s=null;
        try{
            s=new Socket("127.0.0.1",5555);
            new Capture(s).start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}