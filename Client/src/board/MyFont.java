package board;

import java.awt.Graphics2D;
import java.awt.Shape;

import data.ShapeModel;

public class MyFont extends ShapeModel{
	int fontSize;
	String textFont;
	String textWord;
	double sw,sh;
	
	
    public MyFont(){
    	id=8;
    }
    public MyFont(int size,double sw,Double sh,String font,String word) {
		id=8;
		this.sw=sw;
		this.sh=sh;
		fontSize=size;
		textFont=font;
		textWord=word;
	}

	@Override
	public void show(Graphics2D g) {
	
	}

}
