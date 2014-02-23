/* 文件名：MyLog.java
 * 日期：2006-11-7
 * 作者：谭奇宇
 */


package fileShare;

import java.io.*;
import java.util.*;
import java.text.*;

public class MyLog
{
	//log文件名
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
	
	//往log文件中写信息
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
