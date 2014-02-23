/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.client.interfaces.MeetingReceiverInterface;

/**
 *
 * @author Gyx
 */
public class MeetingReceiver  extends UnicastRemoteObject implements MeetingReceiverInterface{

    public MeetingReceiver() throws RemoteException {
        
    }
    
    @Override
    public int ifAllow(String name) throws RemoteException {
        int answer=JOptionPane.showConfirmDialog(null, name+" wants to end the meeting, do you allow ?  "
                + "If you don't respond in 20 seconds, the system will choose yes." , "Request",JOptionPane.YES_NO_OPTION);
        return answer;
    }
    
}
