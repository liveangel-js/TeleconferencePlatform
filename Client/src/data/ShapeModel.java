package data;

import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class ShapeModel implements Serializable{//ͼԪ�ӿ�
	public int id;//ͼԪ���
	public int lineWidth=1;  //��¼���ʴ�ϸ
	public Point startP = new Point(0,0);
	public Point endP = new Point(0,0);
	
	public ShapeModel() {
	}
	
	public abstract void show(Graphics2D g);//�����̻�����
	 
	 public void setStartPoint(Point _startP){
		 startP=_startP;
	 }
	 public void setPoint(Point _startP,Point _endP){
		 startP=_startP;
		 endP=_endP;
	 }
}
