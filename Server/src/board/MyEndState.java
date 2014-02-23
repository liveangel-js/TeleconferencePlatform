package board;

import java.awt.Graphics2D;

import data.ShapeModel;

//½áÊø×´Ì¬
public class MyEndState extends ShapeModel{
    public MyEndState(){
    	id=16;
    }
    
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawOval(Math.min(startP.x,endP.x),
				Math.min(startP.y,endP.y), 
				Math.abs(endP.x-startP.x),
				Math.abs(endP.x-startP.x));
		int x=Math.min(startP.x,endP.x)+5;
		int y=Math.min(startP.y,endP.y)+5;
		int width=Math.abs(endP.x-startP.x)-10;
		g.fillOval(x, y, width, width);
	}

}
