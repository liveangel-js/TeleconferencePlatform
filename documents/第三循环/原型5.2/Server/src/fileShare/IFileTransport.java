/* �ļ�����IFileTransport.java
 * ���ڣ�2006-11-7
 * ���ߣ�̷����
 */

package fileShare;

import java.rmi.*;
import java.util.*;

//�������ļ�����Ľӿ�
public interface IFileTransport extends Remote
{
	//Ĭ�϶˿�
	int defaultPort = 2046;
	
	//��ȡ�����ļ��б�
	Vector<String> getFileList() throws Exception;	
	
	//��ȡ�ļ�����
	int getFileLength(String fileName) throws Exception;	
	
	//��ȡ�ļ�����
	//fileName: �ļ���
	//start����ʼλ��
	//lenghg���ֽڳ���
	byte[] getFile(String fileName, int start, int length) throws Exception;	
}

