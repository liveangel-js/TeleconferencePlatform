package board;

import java.awt.Graphics2D;

import data.ShapeModel;

public class MyStartState extends ShapeModel{
   public MyStartState(){
	   id=15;
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
