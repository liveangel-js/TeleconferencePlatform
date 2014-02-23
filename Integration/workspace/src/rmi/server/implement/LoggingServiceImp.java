/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.server.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.server.interfaces.LoggingService;
import sql.Interface.UserIndentifyInterface;
import sql.UserIndentifySQL;

/**
 *
 * @author Gyx
 */
public class LoggingServiceImp extends UnicastRemoteObject implements LoggingService{

    UserIndentifyInterface userIndentify=null;
    public LoggingServiceImp()throws RemoteException{
        userIndentify=new UserIndentifySQL();
    }
    
    @Override
    public int checkPassword(String username, String password) throws RemoteException {
        
     if(UserIndentifyInterface.CORRECT==userIndentify.checkUser(username, password))
            return 1;
        else 
            return 0;
    }
    
}
