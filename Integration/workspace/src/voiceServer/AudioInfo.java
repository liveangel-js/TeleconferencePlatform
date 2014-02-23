/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package voiceServer;

import javax.sound.sampled.AudioFormat;

/**
 *
 * @author Gyx
 */
public class AudioInfo {
    public static AudioFormat getAudioFormat(){
        float sampleRate=44100.0F;
        int sampleSize=8;
        int channels=1;
        boolean bigEndian=false;
        return new AudioFormat( 
                AudioFormat.Encoding.PCM_SIGNED,
                sampleRate,
                sampleSize,
                channels,
                (sampleSize/8)*channels,
                sampleRate,bigEndian);
    }
    
    public static int cTIMES=5;
    public static int cD=30000;
    public static int cData=3000;
}
