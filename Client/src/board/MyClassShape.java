package board;

import java.awt.Graphics2D;

import data.ShapeModel;


public class MyClassShape extends ShapeModel{
    public MyClassShape(){
    	id=13;
	}

	@Override
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		int width = Math.abs(endP.x - startP.x);
		int height = Math.abs(endP.y - startP.y);
		g.drawRect(Math.min(startP.x,endP.x),
				Math.min(startP.y,endP.y),
				width,height/3);
		g.drawRect(Math.min(startP.x,endP.x),
				Math.min(startP.y,endP.y)+height/3,
				width,height/3);
		g.drawRect(Math.min(startP.x,endP.x),
				Math.min(startP.y,endP.y)+height/3+height/3,
				width,	height/3);
	}
}
