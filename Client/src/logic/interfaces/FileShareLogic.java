package logic.interfaces;

import java.util.ArrayList;
import view.FileSharePanel.ProcedureController;



public interface FileShareLogic {
	
	public ArrayList<String>getFileList() ;
	
	public boolean downloadFile(String src,String des,ProcedureController procedure)  ;
	
	public boolean uploadloadFile(String fileName,ProcedureController procedure)  ;

}
