/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.server.interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import rmi.client.interfaces.MeetingReceiverInterface;

/**
 *
 * @author Gyx
 */
public interface MemberControlInterface extends Remote{
    public static final int PORT=6668;
    public static final String NAME="MemberControlS";
    
    public void refreshMember(String name, MeetingReceiverInterface mri)throws RemoteException;
    public ArrayList<String> getIDs()throws RemoteException;
}
