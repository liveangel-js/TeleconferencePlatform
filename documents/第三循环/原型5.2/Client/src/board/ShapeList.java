package board;

import java.util.ArrayList;


public class ShapeList {//��List��¼���л����ϵ�ͼԪ����
	ArrayList<ShapeModel> arrayList;
	
	ShapeList(){
		arrayList = new ArrayList<ShapeModel>();
	}
	public void add(ShapeModel s){
		arrayList.add(s);
	}
	public void delete(ShapeModel s){//ɾ��һ��ͼ��λ
		arrayList.remove(s);
	}
}
