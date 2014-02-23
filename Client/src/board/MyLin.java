package board;

import java.awt.Graphics2D;

import data.ShapeModel;

public class MyLin extends ShapeModel{
    public MyLin() {
    	id=7;
    }

	@Override
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		int startx = Math.min(startP.x,endP.x);
		int starty = Math.min(startP.y,endP.y);
		int endx = Math.max(startP.x,endP.x);
		int endy = Math.max(startP.y,endP.y);
		
		g.drawLine((startx+endx)/2, starty, startx, (starty+endy)/2);
		g.drawLine((startx+endx)/2, starty, endx, (starty+endy)/2);
		g.drawLine((startx+endx)/2, endy, startx, (starty+endy)/2);
		g.drawLine((startx+endx)/2, endy, endx, (starty+endy)/2);
	}
}
