package board;

import java.awt.Graphics2D;


public class MyTimeShape extends ShapeModel{

    public MyTimeShape(){
    	id=17;
    }
	@Override
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		int width = Math.abs(endP.x-startP.x);
		int height = Math.abs(endP.y-startP.y);
		int defaultH = Math.min(50, height);
		
		Point _startP = new Point(Math.min(startP.x, endP.x),Math.min(startP.y, endP.y));
		Point _endP = new Point(Math.max(startP.x, endP.x),Math.max(startP.y, endP.y));
		
		g.drawRect(_startP.x, _startP.y, width, defaultH);
		g.drawLine((startP.x+endP.x)/2, startP.y+defaultH, (startP.x+endP.x)/2, endP.y);
	}

}
