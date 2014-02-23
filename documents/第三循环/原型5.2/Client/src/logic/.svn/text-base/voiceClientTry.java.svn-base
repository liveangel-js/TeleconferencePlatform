package logic;

import java.net.Socket;


public class voiceClientTry {
	public static void main(String []args){
		Socket s=null;
        try{
            s=new Socket("127.0.0.1",5555);
            new Capture(s).start();
            new Playback(s).start();
        }catch(Exception e){
            e.printStackTrace();
        }
	}
}
