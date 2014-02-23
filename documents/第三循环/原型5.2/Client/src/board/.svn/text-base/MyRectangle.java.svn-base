package board;

import java.awt.Graphics2D;

public class MyRectangle extends ShapeModel{

	public MyRectangle() {
		id=2;
	}
	
	@Override
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawRect(Math.min(startP.x,endP.x),
				Math.min(startP.y,endP.y),
				Math.abs(startP.x - endP.x),
				Math.abs(startP.y - endP.y));
	}
}
