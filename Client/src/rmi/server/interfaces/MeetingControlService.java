/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.server.interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Gyx
 */
public interface MeetingControlService extends Remote{
    public static final int PORT=6667;
    public static final String NAME="MeetingControlS";
    
    public int getMeetingState()
            throws RemoteException;
    public int getMeetingNo()
            throws RemoteException;
    public void startMeeting(String name)
            throws RemoteException;
    public int stopMeeting(String name)
            throws RemoteException;
    public int addABoard(String name,int  boardID)
            throws RemoteException;
    public ArrayList<Integer> getBoardList()
            throws RemoteException;
	// ��Ӱװ�ID
	public void addBoardID(int boardID)
			throws RemoteException; 
	// ɾ���װ�ID
	public void deleteBoardID(int boardID)
			throws RemoteException; 
    /**
     * masking send meeting id for client board
     */
	public int getMeetingID() throws RemoteException; 
}
