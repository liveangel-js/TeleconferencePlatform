/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import data.Name;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.client.interfaces.MeetingReceiverInterface;
import rmi.server.interfaces.MemberControlInterface;

/**
 *
 * @author Gyx
 */
public class memberControl extends UnicastRemoteObject implements MemberControlInterface , Serializable{
    public ArrayList<Name> names;

    public memberControl() throws RemoteException{
        names=new ArrayList<Name>();
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(memberControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int i=0;
                    for(;i<names.size();i++){
                        names.get(i).x-=1;
                        if(names.get(i).x==0){
                            names.remove(i);
                            i--;
                        }
                    }
                }
            }
            
        }).start();
    }
    
    @Override
    public ArrayList<String> getIDs() throws RemoteException {
        ArrayList<String> n=new ArrayList<String>();
        for(Name e:names)
            n.add(e.n);
        return n;
    }


    @Override
    public void refreshMember(String name , MeetingReceiverInterface mri) throws RemoteException {
        boolean t=true;
        for(Name e:names)
            if (name.equals(e.n)) {
                t=false;
                e.x=5;
                break;
            }
        if(t) names.add(new Name(name,mri));
    }
}

