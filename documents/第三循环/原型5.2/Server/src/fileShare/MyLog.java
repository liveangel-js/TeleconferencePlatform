/* �ļ�����MyLog.java
 * ���ڣ�2006-11-7
 * ���ߣ�̷����
 */


package fileShare;

import java.io.*;
import java.util.*;
import java.text.*;

public class MyLog
{
	//log�ļ���
	private String logFileName;

	public MyLog(String fileName) throws IOException
	{
		this.logFileName = fileName;
		FileWriter fw = new FileWriter(this.logFileName);
		Date d = new Date();
		fw.write("Log at:" + d.toString() + "\r\n");
		fw.write("--------------------------------------------\r\n");
		fw.close();
	}
	
	public MyLog(String fileName, boolean append) throws IOException
	{		
		this.logFileName = fileName;
		FileWriter fw = new FileWriter(this.logFileName, append);
		Date d = new Date();
		fw.write("Log at:" + d.toString() + "\r\n");
		fw.write("--------------------------------------------\r\n");
		fw.close();
	}
	
	//��log�ļ���д��Ϣ
	public void log(String message)
	{
		try
		{
			FileWriter fw = new FileWriter(this.logFileName, true);
			fw.write(message + "\r\n");
			fw.close();
		}
		catch(Exception e)
		{
			
		}
	}
}
