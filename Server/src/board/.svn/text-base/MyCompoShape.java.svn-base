package board;

import java.awt.Graphics2D;

import data.Point;
import data.ShapeModel;


public class MyCompoShape extends ShapeModel{

	public MyCompoShape(){
		id=20;
	}
	@Override
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		int width = Math.abs(endP.x-startP.x);
		int height = Math.abs(endP.y-startP.y);
		
		int defaultW = Math.min(20,width);
		int defaultH = Math.min(10,height/4);
		
		Point _startP = new Point(Math.min(startP.x, endP.x),Math.min(startP.y, endP.y));
		Point _endP = new Point(Math.max(startP.x, endP.x),Math.max(startP.y, endP.y));
		
		g.drawLine(_startP.x, _startP.y, _endP.x, _startP.y);
		g.drawLine(_startP.x, _endP.y, _endP.x, _endP.y);
		g.drawLine(_endP.x, _startP.y, _endP.x, _endP.y);
		
		g.drawLine(_startP.x, _startP.y, _startP.x, _startP.y+height/4);
		g.drawLine(_startP.x, _startP.y+height/4+defaultH, _startP.x, _startP.y+(3*height/4 - defaultH));
		g.drawLine(_startP.x, _endP.y-height/4, _startP.x, _endP.y);
		
		g.drawRect(_startP.x-defaultW/2, _startP.y+height/4, defaultW, defaultH);
		g.drawRect(_startP.x-defaultW/2, _endP.y-height/4-defaultH, defaultW, defaultH);
	}

}
