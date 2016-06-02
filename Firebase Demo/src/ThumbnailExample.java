import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import static org.imgscalr.Scalr.*;

public class ThumbnailExample {


	
	 
	public static BufferedImage createThumbnail(BufferedImage img) {
	  // Create quickly, then smooth and brighten it.
	  img = Scalr.resize(img, Method.SPEED, Mode.AUTOMATIC, 600);
//	  img = Scalr.resize(img, Method.QUALITY,Mode.AUTOMATIC, 300, Scalr.OP_ANTIALIAS);
	 
	  // Let's add a little border before we return result.
	  return img;
	}
	 public static void main(String[] args) throws IOException {
	  
	  File originImage = new File("e.png");
	 
	  BufferedImage img = ImageIO.read(originImage); // load image
	  BufferedImage thumbImg = createThumbnail(img);
	  
	  
	  //or wrtite to a file
	  
	  File f2 = new File("x.png");
	  
	  
	  ImageIO.write(thumbImg, "png", f2);
	  
	  System.out.println("DONE:image scale ");
	  System.exit(0);
	  
	  
	 }
}
