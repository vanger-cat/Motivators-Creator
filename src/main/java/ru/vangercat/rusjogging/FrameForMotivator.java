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
    private static final int EMPTY_SPACE_RGB_VALUE = 16777215;
    private final FrameInFrame contentFrame;
    private final FrameInFrame textFrame;
    private final BufferedImage image;

    public FrameForMotivator(BufferedImage image) {
        super(image.getWidth(), image.getHeight());
        this.image = image;

        contentFrame = calculateFrameForContent();
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
            isInEmptySpace = image.getRGB(x, upperLeftCornerOfContent.y) == EMPTY_SPACE_RGB_VALUE;
            x++;
        }

        return x - upperLeftCornerOfContent.x;
    }

    private int findHeightOfContent(Point upperLeftCornerOfContent) {
        boolean isInEmptySpace = true;
        int y = upperLeftCornerOfContent.y;
        while (isInEmptySpace && y < image.getHeight()) {
            isInEmptySpace = image.getRGB(upperLeftCornerOfContent.x, y) == EMPTY_SPACE_RGB_VALUE;
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
                isInEmptySpace = image.getRGB(point.x, point.y) == EMPTY_SPACE_RGB_VALUE;
                point.x++;
            }
            point.y++;
        }

        return point;
    }

    private class Point {
        int x;
        int y;
    }


}
