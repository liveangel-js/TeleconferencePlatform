/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import rmi.client.interfaces.OrderRecieverInterface;

/**
 *
 * @author Gyx
 */
public interface OrderControlInterface extends Remote{
    public static final int PORT=6669;
    public static final String NAME="OrderControlS";
    
    public void join(OrderRecieverInterface ori,String name) throws RemoteException;
    public void refresh(String name) throws RemoteException;
    public boolean cutIn(OrderRecieverInterface ori,String name) throws RemoteException;
    public void exit(String name) throws RemoteException;
    public ArrayList<String> getOrder()throws RemoteException;
}
