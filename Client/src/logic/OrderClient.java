/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.ServiceManager;
import rmi.server.interfaces.OrderControlInterface;

/**
 *
 * @author Gyx
 */
public class OrderClient {
    private String name;
    private OrderControlInterface oci;
    private OrderChoose oc;
    private int c;

    public OrderClient(String n) throws RemoteException{
        oc=new OrderChoose();
        name=n;
        c=0;
        try {
            oci=ServiceManager.getOrderControl();
        } catch (NotBoundException ex) {
            Logger.getLogger(OrderClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(OrderClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void getin(){
        try {
            oci.join(oc, name);
        } catch (RemoteException ex) {
            Logger.getLogger(OrderClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        c=1;
    }

    public void exit() {
        try {
            oci.exit(name);
        } catch (RemoteException ex) {
            Logger.getLogger(OrderClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        c=0;
    }

    public boolean cutin() {
        try {
            if(oci.cutIn(oc, name)){
                c=1;
                return true;
            }else return false;
        } catch (RemoteException ex) {
            Logger.getLogger(OrderClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public int getC(){
        return c;
    }

    public void refresh() {
        try {
            oci.refresh(name);
        } catch (RemoteException ex) {
            Logger.getLogger(OrderClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> getOrder() {
        try {
            return oci.getOrder();
        } catch (RemoteException ex) {
            Logger.getLogger(OrderClient.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<String>();
        }
    }
}
