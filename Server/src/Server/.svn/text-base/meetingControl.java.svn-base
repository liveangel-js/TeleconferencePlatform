/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import data.Meeting;
import data.MeetingInfo;
import data.record.MeetingRecordManager;
import data.record.RecordUpdateInterface;
import data.record.RecordWriterInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.ServerManager;
import rmi.server.interfaces.MeetingControlService;
import rmi.server.interfaces.MemberControlInterface;
import sql.BoardRecordSQL;
import sql.Interface.BoardElementDataInterface;
import sql.Interface.MeetingDataInterface;
import sql.MeetingRecordSQL;
import voiceServer.voiceServer;

/**
 *
 * @author Gyx
 */
public class meetingControl extends UnicastRemoteObject implements MeetingControlService{
	public static ArrayList<Integer> boardIDList ;
	
	MeetingInfo info;
    private Meeting m;
    memberControl memberC;
    
    
    public meetingControl() throws RemoteException{
        info=new MeetingInfo();
        ServerManager.setMeetingID(0);
    }

    public meetingControl(memberControl MemberC) throws RemoteException{
        MeetingDataInterface meetingCheck=new MeetingRecordSQL();
        memberC=MemberC;
        info=new MeetingInfo();
        if(!meetingCheck.checkLastMeeting())
        {
            info.MeetingID=meetingCheck.getLastMeetingID();
            ServerManager.setMeetingID(info.MeetingID);
            info.MeetingState=1;
            m=new Meeting();
        }
    }

    @Override
    public int getMeetingState() throws RemoteException {
        return info.MeetingState;
    }

    @Override
    public int getMeetingNo() throws RemoteException {
    	return info.MeetingID;
    	
    }

    @Override
    public void startMeeting(String name) throws RemoteException {
        if(info.MeetingState==0){
            info.MeetingState=1;
            Date now=new Date(System.currentTimeMillis());
            m=new Meeting(now);
            RecordWriterInterface rwi=new MeetingRecordManager();
            info.MeetingID=rwi.writeRecord(0, m, name);
            ServerManager.setMeetingID(info.MeetingID);
        }
    }

    @Override
    public int stopMeeting(String name) throws RemoteException {
        if(info.MeetingState==1){
            if(check(name)){
                m.setEndTime(new Date(System.currentTimeMillis()));
                RecordUpdateInterface rui=new MeetingRecordManager();
                rui.updateRecordByID(info.MeetingID, m, "System");
                info.MeetingState=0;
                info.MeetingID=0;
                ServerManager.setMeetingID(0);
                return 1;
            }else{
                return 0;
            }
        }
        return -1;
    }

    @Override
    public int addABoard(String name,int  boardID) throws RemoteException {
        BoardElementDataInterface s=new BoardRecordSQL();
        int i= s.newBoard(info.MeetingID, name,boardID);
        info.boardID.add(i);
        return i;
    }

    @Override
    public ArrayList<Integer> getBoardList() throws RemoteException {
    
    	BoardElementDataInterface s=new BoardRecordSQL();
        MeetingDataInterface meetingData = new MeetingRecordSQL();
        ArrayList<Integer> tempList = s.getBoardIDList(meetingData.getLastMeetingID());
    	if(tempList!=null){
    		boardIDList = tempList;
    	}else{
    		boardIDList = new ArrayList<Integer>() ;
    	}

        return boardIDList;
    }

	@Override
	/**
	 * add board
	 * @author masking
	 */
	public void addBoardID(int boardID) throws RemoteException {

		BoardElementDataInterface sql = new BoardRecordSQL();
		sql.newBoard(boardID/100, "system", boardID);// 将新建白板存入数据库
		System.out.println("AddBoard------>"+boardID);
	}

	@Override
	/**
	 * delete board
	 * @author masking
	 */
	public void deleteBoardID(int boardID) throws RemoteException {
		
		BoardElementDataInterface sql = new BoardRecordSQL();
		sql.deleteBoard(boardID);
	}
    /**
     * masking send meeting id for client board
     */
	@Override
    public int getMeetingID() throws RemoteException {
		MeetingDataInterface meetingData = new MeetingRecordSQL();
    	return meetingData.getLastMeetingID();	
    }

        int d,hd,t;
    private boolean check(String name) {
        int i=0,max=memberC.names.size();
        d=0;hd=0;t=0;
        for(;i<max;i++){
            if (!memberC.names.get(i).n.equals(name))
                new Thread(new RunnableImpl(i, name)).start();
        }
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(meetingControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                t=1;
                System.out.println("t=1");
            }
        }).start();
        
        while((hd<max-1) && (t==0)){
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(meetingControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        d=d+max-hd;
        if(d<max/2) return false;
        else return true;
    }

    class RunnableImpl implements Runnable {

        private final int i;
        private final String name;

        public RunnableImpl(int i, String name) {
            this.i = i;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                if(0==memberC.names.get(i).rmi.ifAllow(name))
                    d++;
                hd++;
            } catch (RemoteException ex) {
                Logger.getLogger(meetingControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
