package board;

import java.awt.Graphics2D;


public class MyNodeShape extends ShapeModel{

	public MyNodeShape(){
		id=19;
	}
	@Override
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		int width = Math.abs(endP.x-startP.x);
		int height = Math.abs(endP.y-startP.y);
		int defaultGap = Math.min(Math.min(width, height),30);
		
		Point _startP = new Point(Math.min(startP.x, endP.x),Math.min(startP.y, endP.y));
		Point _endP = new Point(Math.max(startP.x, endP.x),Math.max(startP.y, endP.y));
		
		g.drawLine(_startP.x+defaultGap, _startP.y, _endP.x, _startP.y);
		g.drawLine(_startP.x, _startP.y+defaultGap, _endP.x-defaultGap, _startP.y+defaultGap);
		g.drawLine(_startP.x, _endP.y, _endP.x-defaultGap, _endP.y);
		
		g.drawLine(_startP.x+defaultGap, _startP.y, _startP.x, _startP.y+defaultGap);
		g.drawLine(_endP.x, _startP.y,_endP.x-defaultGap, _startP.y+defaultGap);
		g.drawLine(_endP.x-defaultGap, _endP.y, _endP.x, _endP.y-defaultGap);
		
		g.drawLine(_startP.x, _startP.y+defaultGap, _startP.x, _endP.y);
		g.drawLine(_endP.x-defaultGap, _startP.y+defaultGap, _endP.x-defaultGap, _endP.y);
		g.drawLine(_endP.x, _startP.y, _endP.x, _endP.y-defaultGap);
	}

}
