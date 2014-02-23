package voiceServer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class Mixer implements Runnable{
	
	Thread thread;
	TargetDataLine tline;
	SourceDataLine sline;
	BufferedOutputStream OutputStream;
	BufferedInputStream InputStream;
	int No;
	public static int userNumber;
	static byte[] sendData=new byte[1024];
	static byte[][] data=new byte[20][1024];
	static int[] available=new int[20];
	Socket s;
	
	public Mixer(Socket s){
		int i;
		for(i=0;i<userNumber;i++){
			if (available[i]!=1){
				No=i;
				available[i]=1;
				break;
			}
		}
		if(i==userNumber){
			No=userNumber;
			userNumber++;
			available[No]=1;
		}
		for(i=0;i<1024;i++)
			data[No][i]=0;
		this.s=s;
	}
	
	public void start() {
        thread = new Thread(this);  
        thread.start();
        userNumber++;
    }


    public void stop() { 
        thread = null; 
    }
	
	public void run() {
		int[] sdata = new int[1024];
		byte[] bdata = new byte[1024]; 
        int numBytesRead = 0; 
        int i = 0,total;
        
        try {
			InputStream=new BufferedInputStream(s.getInputStream());
			OutputStream=new BufferedOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        while(thread!=null){
        	try {
				numBytesRead = InputStream.read(data[No]);
				total=0;
				for(int j=0;j<1024;j++){
					sdata[j]=0;
				}
				for(i=0;i<userNumber;i++)
					if(available[i]==1){
						for(int j=0;j<1024;j++)
							sdata[j]+=data[No][j];
						total++;
					}
				
				if (total>0)
					for(int j=0;j<1024;j++)
						sdata[j]=(byte) (sdata[j]/total);
				for(int j=0;j<1024;j++)
					bdata[j]=(byte) sdata[j];
				OutputStream.write(bdata, 0, 1024);
			} catch (SocketException e){
				try {
					s.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				available[No]=0;
				if (No==userNumber-1)
					userNumber--;
				stop();	
			} 
        	catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
}