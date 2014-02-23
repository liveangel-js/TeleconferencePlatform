/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import data.OrderInfo;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.client.interfaces.OrderRecieverInterface;
import rmi.server.interfaces.OrderControlInterface;

/**
 *
 * @author Gyx
 */
public class OrderControl extends UnicastRemoteObject implements OrderControlInterface{
    ArrayList<OrderInfo> thisOrder;
    public OrderControl() throws RemoteException{
        thisOrder=new ArrayList<OrderInfo>();
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
                    for(;i<thisOrder.size();i++){
                        thisOrder.get(i).remaintime-=1;
                        if(thisOrder.get(i).remaintime==0){
                            thisOrder.remove(i);
                            i--;
                        }
                    }
                }
            }
            
        }).start();
    }
    
    @Override
    public void refresh(String name) throws RemoteException {
        for(OrderInfo e:thisOrder)
            if (name.equals(e.name)) {
                e.remaintime=5;
                break;
            }
    }

    
    int hc,c,t;
    String n;
    @Override
    public boolean cutIn(OrderRecieverInterface ori,String name) throws RemoteException {
        hc=0;c=1;t=0;n=name;
        if(thisOrder.size()>0){
            if((!thisOrder.get(0).name.equals(name))){
                
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            c=thisOrder.get(0).reciever.ifAllow(n);
                        } catch (RemoteException ex) {
                            Logger.getLogger(OrderControl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        hc=1;
                    }
                }).start();
                
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(OrderControl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        t=1;
                        c=0;
                    }   
                }).start();
                
                while(hc==0 && t==0)
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(OrderControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                if(c==0){
                    int i=0;
                    for(;i<thisOrder.size();i++){
                        if (name.equals(thisOrder.get(i).name))
                            thisOrder.remove(i);
                        break;
                    }
                    thisOrder.add(0, new OrderInfo(ori,name));
                    return true;
                }
                else 
                    return false;
            }
            else
                return false;
        }else{
            thisOrder.add(new OrderInfo(ori,name));
            return true;
        }
    }

    @Override
    public void exit(String name) throws RemoteException {
        int i=0;
        for(;i<thisOrder.size();i++){
            if (name.equals(thisOrder.get(i).name))
                thisOrder.remove(i);
            break;
        }
    }

    @Override
    public ArrayList<String> getOrder() throws RemoteException {
        ArrayList<String> n=new ArrayList<String>();
        for(int i=0;i<thisOrder.size();i++)
            n.add(thisOrder.get(i).name);
        return n;
    }

    @Override
    public void join(OrderRecieverInterface ori, String name) throws RemoteException {
        thisOrder.add(new OrderInfo(ori,name));
    }
}
