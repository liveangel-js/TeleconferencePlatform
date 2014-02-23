/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Gyx
 */
public interface LoggingService extends Remote{
    public static final int PORT=6666;
    public static final String NAME="Logging";
    
    
    
    public int checkPassword(String id,String password)
            throws RemoteException;
}
