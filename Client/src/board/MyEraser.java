package board;

import java.awt.Graphics2D;

import data.ShapeModel;

public class MyEraser extends ShapeModel{
    public MyEraser() {
    	id=6;
    }

	@Override
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawLine(startP.x, startP.y, endP.x, endP.y);
	}
}
