/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.client.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Gyx
 */
public interface OrderRecieverInterface extends Remote{
    
    public int ifAllow(String name) throws RemoteException;
}
