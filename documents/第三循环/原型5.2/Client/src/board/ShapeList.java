package board;

import java.util.ArrayList;


public class ShapeList {//此List记录所有画板上的图元对象
	ArrayList<ShapeModel> arrayList;
	
	ShapeList(){
		arrayList = new ArrayList<ShapeModel>();
	}
	public void add(ShapeModel s){
		arrayList.add(s);
	}
	public void delete(ShapeModel s){//删除一个图像单位
		arrayList.remove(s);
	}
}
