package logic;

import java.net.Socket;


public class VoiceClient implements Runnable{
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket s=null;
        try{
            s=new Socket("172.25.67.58",5555);
            new Capture(s).start();
            new Playback(s).start();
        }catch(Exception e){
            e.printStackTrace();
        }
	}
}
