package logic;

import java.awt.event.KeyEvent;
import java.net.Socket;


public class VoiceClient implements Runnable{
    
    Capture capture;
    Playback playback;
    public int keycode;
    
    public VoiceClient(){
        keycode=KeyEvent.VK_F2;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        Socket s=null;
        try{
            s=new Socket(Setting.get("ip"),5555);
            capture=new Capture(s);
            capture.start();
            playback=new Playback(s);
            playback.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void setCaptureVolume(int x){
        capture.setVolume(x);
    }
    
    public void setPlaybackVolume(int x){
        playback.setVolume(x);
    }
    
    public void setCaptureAccess(boolean x){
        capture.setAccess(x);
    }
}
