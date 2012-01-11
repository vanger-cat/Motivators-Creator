package ru.vangercat.rusjogging;

import ru.vangercat.rusjogging.frameutil.Frame;

import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 09.01.12
 * Time: 2:57
 */
public class FrameForMotivator extends Frame {
    private static final int EMPTY_SPACE_RGB_VALUE = 0;
    private final FrameInFrame contentFrame;
    private final FrameInFrame textFrame;
    private final BufferedImage image;

    public FrameForMotivator(BufferedImage image) {
        super(image.getWidth(), image.getHeight());
        this.image = image;

        contentFrame = calculateFrameForContent();
        for (int x = contentFrame.getX(); x < contentFrame.getX() + contentFrame.getWidth(); x++) {
            for (int y = contentFrame.getY(); y < contentFrame.getY() + contentFrame.getHeight(); y++) {
                image.setRGB(x, y, 0);
            }
        }
        textFrame = calculateTextFrame();
    }

    public FrameInFrame getContentFrame() {
        return contentFrame;
    }

    public FrameInFrame getTextFrame() {
        return textFrame;
    }

    private FrameInFrame calculateFrameForContent() {
        Point upperLeftCornerOfContent = findUpperLeftCornerOfContent();

        int widthOfContent = findWidthOfContent(upperLeftCornerOfContent);
        int heightOfContent = findHeightOfContent(upperLeftCornerOfContent);

        return new FrameInFrame(upperLeftCornerOfContent.x, upperLeftCornerOfContent.y, widthOfContent, heightOfContent);
    }

    private FrameInFrame calculateTextFrame() {
        int y = contentFrame.getY() + contentFrame.getHeight();
        return new FrameInFrame(0, y, image.getWidth(), image.getHeight() - y);
    }

    private int findWidthOfContent(Point upperLeftCornerOfContent) {
        boolean isInEmptySpace = true;
        int x = upperLeftCornerOfContent.x;
        while (isInEmptySpace && x < image.getWidth()) {
            isInEmptySpace = isEmptyRGB(image.getRGB(x, upperLeftCornerOfContent.y));
            x++;
        }

        return x - upperLeftCornerOfContent.x;
    }

    private int findHeightOfContent(Point upperLeftCornerOfContent) {
        boolean isInEmptySpace = true;
        int y = upperLeftCornerOfContent.y;
        while (isInEmptySpace && y < image.getHeight()) {
            isInEmptySpace = isEmptyRGB(image.getRGB(upperLeftCornerOfContent.x, y));
            y++;
        }

        return y - upperLeftCornerOfContent.y;
    }

    private Point findUpperLeftCornerOfContent() {
        boolean isInEmptySpace = false;
        Point point = new Point();
        while (!isInEmptySpace && point.y < image.getHeight()) {
            point.x = 0;
            while (!isInEmptySpace && point.x < image.getWidth()) {
                isInEmptySpace = isEmptyRGB(image.getRGB(point.x, point.y));
                point.x++;
            }
            point.y++;
        }

        return point;
    }

    private boolean isEmptyRGB(int rgb) {
        return rgb == EMPTY_SPACE_RGB_VALUE || rgb == 16777215;
    }

    public BufferedImage getFrameWithContentFrameSizedFor(Frame motivatingImageRectangle) {
        Frame frameSizedToFit = this.getContentFrame().getFrameSizedToFit(motivatingImageRectangle);
        float widthProportion = calculateProportionBetween(this.getContentFrame().getWidth(), frameSizedToFit.getWidth());
        float heightProportion = calculateProportionBetween(this.getContentFrame().getHeight(), frameSizedToFit.getHeight());

        int newWidth = Math.round(this.getWidth() / widthProportion);
        int newHeight = Math.round(this.getHeight() / heightProportion);
        BufferedImage imageForNewFrame = new BufferedImage(newWidth, newHeight, image.getType());
        imageForNewFrame.getGraphics().drawImage(image, 0, 0, newWidth, newHeight, null);

        return imageForNewFrame;
    }

    private class Point {
        int x;
        int y;
    }


}
