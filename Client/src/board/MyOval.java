package board;

import java.awt.Graphics2D;

import data.ShapeModel;


public class MyOval extends ShapeModel{
    public MyOval(){	
    	id=5;
    }
	@Override
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawOval(Math.min(startP.x,endP.x),
				Math.min(startP.y,endP.y),
				Math.abs(startP.x-endP.x),
				Math.abs(startP.y-endP.y));
	}

}
