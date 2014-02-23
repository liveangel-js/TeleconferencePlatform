import java.awt.Graphics2D;
import java.awt.event.MouseEvent;


public abstract class Shape {//图元接口
	int id;//图元类别
	Point startP;
	Point endP;
	
	public abstract void show(int x0, int y0, Graphics2D g, MouseEvent e);//将自己画出来
	
}
