package board;
import java.awt.image.BufferedImage;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ImgData extends BufferedImage implements Serializable{
	public ImgData(int width, int height, int imageType) {
		super(width, height, imageType);
		// TODO Auto-generated constructor stub
	}

}
