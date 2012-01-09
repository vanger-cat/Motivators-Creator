package ru.vangercat.rusjogging;

import ru.vangercat.rusjogging.frameutil.Frame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 09.01.12
 * Time: 3:24
 */
public class Motivator {

    private final FrameForMotivator rectangleOfFrame;
    private final BufferedImage motivatingImage;
    private final Frame motivatingImageRectangle;
    private final BufferedImage motivatorFrameImage;

    public Motivator(BufferedImage motivatingImage) {
        this.motivatingImage = motivatingImage;
        motivatingImageRectangle = new Frame(motivatingImage.getWidth(), motivatingImage.getHeight());

        String motivatorFrameFileName;
        switch (motivatingImageRectangle.getOrientation()) {
            case HORIZONTAL: {
                motivatorFrameFileName = "frame_horizontal.png";
                break;
            }
            case VERTICAL: {
                motivatorFrameFileName = "frame_vertical.png";
                break;
            }
            default: {
                motivatorFrameFileName = "frame_vertical.png";
                break;
            }
        }

        try {
            motivatorFrameImage = ImageIO.read(new File(motivatorFrameFileName));
            rectangleOfFrame = new FrameForMotivator(motivatorFrameImage);
        } catch (IOException e) {
            throw new MotivatorImageException("Не удаётся загрузить рамку для мотиватора", e);
        }
    }


    public BufferedImage getResultingImage() {
        BufferedImage resultingImage = new BufferedImage(rectangleOfFrame.getWidth(), rectangleOfFrame.getHeight(), motivatorFrameImage.getType());
        resultingImage.getGraphics().drawImage(motivatorFrameImage, 0, 0, null);

        FrameInFrame contentFrame = rectangleOfFrame.getContentFrame();
        Frame frameSizedToFit = contentFrame.getFrameSizedToFit(motivatingImageRectangle);
        int xForMotivatigImage;
        int xOffset = (contentFrame.getWidth() - frameSizedToFit.getWidth()) / 2;
        xForMotivatigImage = xOffset + contentFrame.getX();

        int yForMotivatigImage;
        int yOffset = -(contentFrame.getHeight() - frameSizedToFit.getHeight()) / 2;
        yForMotivatigImage = yOffset + contentFrame.getY();

        int width = frameSizedToFit.getWidth();
        int height = frameSizedToFit.getHeight();

        resultingImage.getGraphics().drawImage(
                motivatingImage,
                xForMotivatigImage,
                yForMotivatigImage,
                width,
                height,
                null);
        return resultingImage;
    }
}
