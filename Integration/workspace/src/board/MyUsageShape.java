package board;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import data.ShapeModel;

public class MyUsageShape extends ShapeModel {
	
	public MyUsageShape(){
		id = 11;
	}

	@Override
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		double  H  =   10 ;  // ��ͷ�߶�    
        double  L  =   7 ; // �ױߵ�һ��   
        int  x3  =   0 ;
       int  y3  =   0 ;
       int  x4  =   0 ;
       int  y4  =   0 ;
       double  awrad  =  Math.atan(L  /  H);  // ��ͷ�Ƕ�    
        double  arraow_len  =  Math.sqrt(L  *  L  +  H  *  H); // ��ͷ�ĳ���    
        double [] arrXY_1  =  rotateVec(endP.x  -  startP.x, endP.y  -  startP.y, awrad,  true , arraow_len);
       double [] arrXY_2  =  rotateVec(endP.x  -  startP.x, endP.y  -  startP.y,  - awrad,  true , arraow_len);
       x3  =  endP.x  -  (int)arrXY_1[ 0 ];  // (x3,y3)�ǵ�һ�˵�    
       y3  =  endP.y  -  (int)arrXY_1[ 1 ];
       x4  =  endP.x  -  (int)arrXY_2[ 0 ]; // (x4,y4)�ǵڶ��˵�    
       y4  =  endP.y  -  (int)arrXY_2[ 1 ];

      int length = (startP.x-endP.x)*(startP.x-endP.x) + (startP.y-endP.y)*(startP.y-endP.y);
      length =  (int)Math.sqrt(length);
      int cutNum = length/30;
      
      // g.setColor(SWT.COLOR_WHITE);
       // ������
      float   dashArray[]   =   {6.0f};
      BasicStroke dashed =new BasicStroke(1.0f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f,dashArray,0.0f);
      g.setStroke(dashed);
      g.draw(new Line2D.Double(startP.x, startP.y, endP.x, endP.y));
      
       //g.drawLine(startP.x, startP.y, endP.x, endP.y);
       // ����ͷ��һ�� 
       g.drawLine(endP.x, endP.y, x3, y3);
       // ����ͷ����һ�� 
       g.drawLine(endP.x, endP.y, x4, y4);
	}
	
	 private   double [] rotateVec( int  px,  int  py,  double  ang,  boolean  isChLen,
             double  newLen)  {

         double  mathstr[]  =   new   double [ 2 ];
         // ʸ����ת��������������ֱ���x������y��������ת�ǡ��Ƿ�ı䳤�ȡ��³���    
          double  vx  =  px  *  Math.cos(ang)  -  py  *  Math.sin(ang);
         double  vy  =  px  *  Math.sin(ang)  +  py  *  Math.cos(ang);
         if  (isChLen)  {
             double  d  =  Math.sqrt(vx  *  vx  +  vy  *  vy);
            vx  =  vx  /  d  *  newLen;
            vy  =  vy  /  d  *  newLen;
            mathstr[ 0 ]  =  vx;
            mathstr[ 1 ]  =  vy;
        } 
         return  mathstr;
    }

}
