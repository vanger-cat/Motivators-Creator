import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

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
        System.out.println("frame.getWidth() = " + image.getWidth());
        System.out.println("frame.getHeight() = " + image.getHeight());
        System.out.println("empty pixel = " + image.getRGB(1200, 1200));

        final int EMPTY_SPACE_VALUE = 16777215;
        boolean isInEmptySpace = false;
        boolean isPreviousLineHasEmptySpace = false;
        for (int y = 0; y < image.getHeight(); y++) {
            boolean isCurrentLineHasEmptySpace = false;

            for (int x = 0; x < image.getWidth(); x++) {
                if (!isInEmptySpace && image.getRGB(x, y) == EMPTY_SPACE_VALUE) {
                    isCurrentLineHasEmptySpace = true;
                    if (!isPreviousLineHasEmptySpace) {
                        System.out.printf("upper left corner: %d, %d\n", x, y);
                    }
                    isInEmptySpace = true;
                }

                if (isInEmptySpace && image.getRGB(x, y) != EMPTY_SPACE_VALUE) {
                    if (!isPreviousLineHasEmptySpace) {
                        System.out.printf("upper right corner: %d, %d\n", x, y);
                    }
                    isInEmptySpace = false;
                }
            }
            if (isPreviousLineHasEmptySpace && !isCurrentLineHasEmptySpace) {
                System.out.printf("bottom end = %d\n", y);
            }

            isPreviousLineHasEmptySpace = isCurrentLineHasEmptySpace;
        }
        assertNotNull(image);
    }

    @Test
    public void testStringSplit() throws Exception {
        System.out.println("result = " + Arrays.toString("ASd.dd.png".split("\\.")));
    }
}
