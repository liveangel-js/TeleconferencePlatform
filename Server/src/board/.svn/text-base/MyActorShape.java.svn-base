package board;

import java.awt.Graphics2D;

import data.Point;
import data.ShapeModel;


public class MyActorShape extends ShapeModel{

	public MyActorShape(){
		id=18;
	}
	@Override
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		Point _startP = new Point(Math.min(startP.x, endP.x),Math.min(startP.y, endP.y));
		Point _endP = new Point(Math.max(startP.x, endP.x),Math.max(startP.y, endP.y));
		
		int width = Math.abs(_startP.x-_endP.x);
		int height = Math.abs(_startP.y-_endP.y);
		g.drawOval(_startP.x+width/4, _startP.y, width/2, height/3);
		g.drawLine(_startP.x+width/2, _startP.y+height/3, _startP.x+width/2, _startP.y+2*height/3);
		g.drawLine(_startP.x, _startP.y+height/2, _startP.x+width, _startP.y+height/2);
		g.drawLine(_startP.x+width/2, _startP.y+2*height/3, _startP.x, _startP.y+height);
		g.drawLine(_startP.x+width/2, _startP.y+2*height/3, _startP.x+width, _startP.y+height);
	}

}

