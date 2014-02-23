/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JOptionPane;
import rmi.client.interfaces.OrderRecieverInterface;

/**
 *
 * @author Gyx
 */
public class OrderChoose extends UnicastRemoteObject implements OrderRecieverInterface{

    public OrderChoose()throws RemoteException {
    }
    
    @Override
    public int ifAllow(String name) throws RemoteException {
        int answer=JOptionPane.showConfirmDialog(null, name+" wants to cut in, do you allow ?" , "Request",JOptionPane.YES_NO_OPTION);
        return answer;
    }
    
}
