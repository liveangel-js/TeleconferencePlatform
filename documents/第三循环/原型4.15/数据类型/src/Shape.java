import java.awt.Graphics2D;
import java.awt.event.MouseEvent;


public abstract class Shape {//ͼԪ�ӿ�
	int id;//ͼԪ���
	Point startP;
	Point endP;
	
	public abstract void show(int x0, int y0, Graphics2D g, MouseEvent e);//���Լ�������
	
}
