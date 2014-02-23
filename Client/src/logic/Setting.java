/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Gyx
 */
public class Setting {
    private static Properties ini= null;
    static File file=new File("config.ini");
    static {
        try{
            ini =new Properties();
            ini.load(new FileInputStream(file));
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static String get(String key){
        if(!ini.containsKey(key)){
            return "";
        }
        return ini.getProperty(key);
    }
    public static void set(String key,String value) throws IOException{
        ini.setProperty(key, value);
        try{
            ini.store(new FileOutputStream(file),"config.ini");
        } catch(java.io.FileNotFoundException e){
            
        }
    }
}
