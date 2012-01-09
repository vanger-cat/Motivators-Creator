package ru.vangercat.rusjogging;

import ru.vangercat.rusjogging.frameutil.Frame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 09.01.12
 * Time: 3:24
 */
public class Motivator {

    private static final String STAMP_FILE_NAME = "resources/stamp.png";
    private static final String FONT_FILE_NAME = "resources/Izhitsa.TTF";
    private static final int FONT_SIZE = 155;
    private static final Color FONT_COLOR = new Color(210, 30, 40, 255);
    private static final Color TEXT_OUTLINE_COLOR = Color.white;
    private static final int TEXT_OUTLINE_WIDTH = 8;

    private final BufferedImage motivatingImage;
    private final String motivatorText;
    private FrameForMotivator rectangleOfFrame;
    private Frame motivatingImageRectangle;
    private BufferedImage motivatorFrameImage;
    private BufferedImage motivatorStampImage;
    private Font font;

    public Motivator(BufferedImage motivatingImage, String motivatorText) {
        this.motivatingImage = motivatingImage;
        this.motivatorText = motivatorText;
        initFrameOfMotivator(motivatingImage);
        initStampForMotivator();
        initFontForMotivatorText();
    }

    private void initFrameOfMotivator(BufferedImage motivatingImage) {
        motivatingImageRectangle = new Frame(motivatingImage.getWidth(), motivatingImage.getHeight());

        String motivatorFrameFileName;
        switch (motivatingImageRectangle.getOrientation()) {
            case HORIZONTAL: {
                motivatorFrameFileName = "resources/frame_horizontal.png";
                break;
            }
            case VERTICAL: {
                motivatorFrameFileName = "resources/frame_vertical.png";
                break;
            }
            default: {
                motivatorFrameFileName = "resources/frame_vertical.png";
                break;
            }
        }

        try {
            motivatorFrameImage = ImageIO.read(ClassLoader.getSystemResourceAsStream(motivatorFrameFileName));
            rectangleOfFrame = new FrameForMotivator(motivatorFrameImage);
        } catch (IOException e) {
            throw new MotivatorImageException("Не удаётся загрузить рамку для мотиватора", e);
        }
    }

    private void initStampForMotivator() {
        try {
            motivatorStampImage = ImageIO.read(ClassLoader.getSystemResourceAsStream(STAMP_FILE_NAME));
        } catch (IOException e) {
            throw new MotivatorImageException("Не удаётся загрузить печать для для мотиватора из файла " + STAMP_FILE_NAME, e);
        }
    }

    private void initFontForMotivatorText() {
        try {
            Font newFont = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream(FONT_FILE_NAME));
            font = newFont.deriveFont(Font.PLAIN, FONT_SIZE);
        } catch (Exception e) {
            throw new MotivatorImageException("Не удаётся загрузить шрифт из файла " + FONT_FILE_NAME, e);
        }
    }

    public BufferedImage getResultingImage() {
        BufferedImage resultingImage = new BufferedImage(rectangleOfFrame.getWidth(), rectangleOfFrame.getHeight(), motivatorFrameImage.getType());
        drawMotivatorFrame(resultingImage);
        drawMotivatorContent(resultingImage);
        drawMotivatorStamp(resultingImage);

        drawMotivatorText(resultingImage);

        return resultingImage;
    }

    private void drawMotivatorFrame(BufferedImage resultingImage) {
        resultingImage.getGraphics().drawImage(motivatorFrameImage, 0, 0, null);
    }

    private void drawMotivatorContent(BufferedImage resultingImage) {
        FrameInFrame contentFrame = rectangleOfFrame.getContentFrame();
        Frame frameSizedToFit = contentFrame.getFrameSizedToFit(motivatingImageRectangle);
        int xForMotivatigImage;
        int xOffset = (contentFrame.getWidth() - frameSizedToFit.getWidth()) / 2;
        xForMotivatigImage = xOffset + contentFrame.getX();

        int yForMotivatigImage;
        int yOffset = (contentFrame.getHeight() - frameSizedToFit.getHeight()) / 2;
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
    }

    private void drawMotivatorStamp(BufferedImage resultingImage) {
        FrameInFrame contentFrame = rectangleOfFrame.getContentFrame();
        int width = motivatorStampImage.getWidth();
        int xStamp = contentFrame.getX() + contentFrame.getWidth() - width;

        int height = motivatorStampImage.getHeight();
        int yStamp = contentFrame.getY() + contentFrame.getHeight() - height;
        resultingImage.getGraphics().drawImage(motivatorStampImage, xStamp, yStamp, null);
    }

    private void drawMotivatorText(BufferedImage resultingImage) {
        Graphics graphics = resultingImage.getGraphics();
        graphics.setFont(font);

        FrameInFrame textFrame = rectangleOfFrame.getTextFrame();
        FontMetrics fontMetrics = graphics.getFontMetrics();

        int textHeight = fontMetrics.getHeight();
        int textWidth = fontMetrics.stringWidth(motivatorText);

        int xForCaption = textFrame.getX() + (textFrame.getWidth() - textWidth) / 2;
        int yForCaption = textFrame.getY() + textHeight - 20;
        drawOutlinedText(graphics, motivatorText, xForCaption, yForCaption);
//        graphics.drawString(motivatorText, xForCaption, yForCaption);
    }

    private void drawOutlinedText(Graphics graphics, String text, int x, int y) {
        graphics.setColor(TEXT_OUTLINE_COLOR);
        for (int xShift = 0; xShift < TEXT_OUTLINE_WIDTH; xShift++) {
            for (int yShift = 0; yShift < TEXT_OUTLINE_WIDTH; yShift++) {
                graphics.drawString(text, ShiftWest(x, xShift), ShiftNorth(y, yShift));
                graphics.drawString(text, ShiftWest(x, xShift), ShiftSouth(y, yShift));
                graphics.drawString(text, ShiftEast(x, xShift), ShiftNorth(y, yShift));
                graphics.drawString(text, ShiftEast(x, xShift), ShiftSouth(y, yShift));
            }
        }


        graphics.setColor(FONT_COLOR);
        graphics.drawString(text, x, y);
    }

    private int ShiftNorth(int p, int distance) {
        return (p - distance);
    }

    private int ShiftSouth(int p, int distance) {
        return (p + distance);
    }

    private int ShiftEast(int p, int distance) {
        return (p + distance);
    }

    private int ShiftWest(int p, int distance) {
        return (p - distance);
    }
}
