package data.paint;

import java.awt.Graphics2D;

public class MyRoundRectangle extends ShapeModel{

	public MyRoundRectangle() {
		// TODO Auto-generated constructor stub
	}
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawRoundRect(Math.min(startP.x,endP.x),
				Math.min(startP.y,endP.y),
				Math.abs(startP.x - endP.x),
				Math.abs(startP.y - endP.y),20,20);
	}

}
