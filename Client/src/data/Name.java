/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;

/**
 *
 * @author Gyx
 */
public class Name implements Serializable{
    public int x;
    public String n;
    public Name(String nn) {
        n=nn;
        x=5;
    }
    
    public Name(String nn,int mm){
        n=nn;
        x=mm;
    }
}