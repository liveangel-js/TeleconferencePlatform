/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package voiceServer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Gyx
 */
public class Record implements Runnable{
    private Thread thread;
    String filename;
    boolean onrun;
    File file;
    AudioInputStream audioInputStream ;
    AudioFileFormat.Type fileFormat=AudioFileFormat.Type.WAVE;
    ByteArrayInputStream bais;
    long length;
    
    
    
    public Record(){
        filename="try.wav";
        file=new File(filename);
        try {
            audioInputStream = AudioSystem.getAudioInputStream(
                                        file) ;
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Record.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Record.class.getName()).log(Level.SEVERE, null, ex);
        }
        onrun=true;
        length=0;
        
    }
    
    
    @Override
    public void run() {
        int[] sdata = new int[1024];
        byte[] bdata = new byte[1024];
        int i = 0,total;
        
        
        while(onrun){
            total=0;
            for(int j=0;j<1024;j++){
                sdata[j]=0;
            }
            for(i=0;i<Mixer.userNumber;i++)
                if(Mixer.available[i]==1){
                    for(int j=0;j<1024;j++)
                        sdata[j]+=Mixer.data[i][j];
                    total++;
                }
            if (total>0)
                for(int j=0;j<1024;j++)
                    sdata[j]=(byte) (sdata[j]/total);
            for(int j=0;j<1024;j++)
                bdata[j]=(byte) sdata[j];
            
            try {
                bais.read(bdata);
                length+=1024;
            } catch (IOException ex) {
                Logger.getLogger(Record.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void start(){
        thread = new Thread(this);  
        thread.setName("Voice Recorder");
        thread.start();
    }
    
    public void stop() {
        onrun=false;
        save();
        thread = null; 
    }

    private void save() {
        AudioFormat format =new AudioFormat(8000,16,2,true,true);
        int frameSizeInBytes = format.getFrameSize() ;
        audioInputStream = new AudioInputStream(bais , format,length/frameSizeInBytes );
        try {
            audioInputStream.reset();
        } catch (IOException ex) {
            Logger.getLogger(Record.class.getName()).log(Level.SEVERE, null, ex);
        }
        File f = new File(filename);
        try {
            AudioSystem.write(audioInputStream, fileFormat, f);
        } catch (IOException ex) {
            Logger.getLogger(Record.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
