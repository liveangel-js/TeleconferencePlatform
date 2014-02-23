package voiceServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;




public class voiceServer implements Runnable{
    private Thread thread;
    private Mixer2 mix;
    
    public voiceServer(){
        mix=new Mixer2();
        mix.start();
    }
    
    @Override
	public void run(){
		ServerSocket ss=null;
		try {
			ss=new ServerSocket(5555);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Socket s=null;
	    while (true){
	    	try{
	    		s=ss.accept();
	    		mix.newSocket(s);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	}

    public void start() {
        thread=new Thread(this);
        thread.setName("Voice Server");
        thread.start();
    }
}


