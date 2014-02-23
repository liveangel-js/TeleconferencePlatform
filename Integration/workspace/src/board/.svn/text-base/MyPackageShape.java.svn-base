package board;

import java.awt.Graphics2D;

import data.ShapeModel;


public class MyPackageShape extends ShapeModel{
    public MyPackageShape() {
    	id=14;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		int width = Math.abs(endP.x - startP.x);
		int height = Math.abs(endP.y - startP.y);
		g.drawRect(Math.min(startP.x,endP.x),
				Math.min(startP.y,endP.y),
				2*width/3,height/4);
		g.drawRect(Math.min(startP.x,endP.x),
				Math.min(startP.y,endP.y)+height/4,
				width,3*height/4);
	}
			
}
