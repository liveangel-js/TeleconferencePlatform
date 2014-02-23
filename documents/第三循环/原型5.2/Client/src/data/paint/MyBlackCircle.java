package data.paint;

import java.awt.Graphics2D;

public class MyBlackCircle extends ShapeModel{
    public MyBlackCircle() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		g.fillOval(Math.min(startP.x,endP.x),
				Math.min(startP.y,endP.y), 
				Math.abs(endP.x-startP.x),
				Math.abs(endP.x-startP.x));
	}
	
}
