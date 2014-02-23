package board;

import java.awt.Graphics2D;


public class MyAssoShape extends ShapeModel{

	public MyAssoShape(){
		id=9;
	}
	@Override
	public void show(Graphics2D g) {
		// TODO Auto-generated method stub
		double  H  =   10 ;  // 箭头高度    
        double  L  =   7 ; // 底边的一半   
        int  x3  =   0 ;
       int  y3  =   0 ;
       int  x4  =   0 ;
       int  y4  =   0 ;
       double  awrad  =  Math.atan(L  /  H);  // 箭头角度    
        double  arraow_len  =  Math.sqrt(L  *  L  +  H  *  H); // 箭头的长度    
        double [] arrXY_1  =  rotateVec(endP.x  -  startP.x, endP.y  -  startP.y, awrad,  true , arraow_len);
       double [] arrXY_2  =  rotateVec(endP.x  -  startP.x, endP.y  -  startP.y,  - awrad,  true , arraow_len);
       x3  =  endP.x  -  (int)arrXY_1[ 0 ];  // (x3,y3)是第一端点    
       y3  =  endP.y  -  (int)arrXY_1[ 1 ];
       x4  =  endP.x  -  (int)arrXY_2[ 0 ]; // (x4,y4)是第二端点    
       y4  =  endP.y  -  (int)arrXY_2[ 1 ];

      
       // g.setColor(SWT.COLOR_WHITE);
       // 画线 
       g.drawLine(startP.x, startP.y, endP.x, endP.y);
       // 画箭头的一半 
       g.drawLine(endP.x, endP.y, x3, y3);
       // 画箭头的另一半 
       g.drawLine(endP.x, endP.y, x4, y4);
	}
	
	 private   double [] rotateVec( int  px,  int  py,  double  ang,  boolean  isChLen,
             double  newLen)  {

         double  mathstr[]  =   new   double [ 2 ];
         // 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度    
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
