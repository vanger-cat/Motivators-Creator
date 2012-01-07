import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 07.01.12
 * Time: 14:01
 */

public class ImageLoaderTest {
    @Test
    public void testImageLoadedFromFile() throws Exception {
        BufferedImage image = ImageIO.read(new File("src/test/resources/1.png"));
        assertNotNull(image);
    }
}
