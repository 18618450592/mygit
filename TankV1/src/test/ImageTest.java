package test;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageTest {

    @Test
     public void test(){
        try{
           //  BufferedImage image= ImageIO.read(new File("D:\\Project\\tools\\TankV1\\src\\main\\java\\images\\0.gif"));
           // assertNotNull(image);

            BufferedImage image2=ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("e0.gif"));
            assertNotNull(image2);
        }catch (IOException e){

        }
    }
}
