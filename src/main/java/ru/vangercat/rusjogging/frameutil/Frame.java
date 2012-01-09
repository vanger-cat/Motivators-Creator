package ru.vangercat.rusjogging.frameutil;

import static ru.vangercat.rusjogging.frameutil.FrameOrientation.*;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 08.01.12
 * Time: 23:39
 */
public class Frame {

    private final int width;
    private final int height;

    public Frame(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Frame getFrameSizedToFit(Frame frameToResize) {
        float widthProportion = getProportionBetween(frameToResize.getWidth(), this.getWidth());
        float heightProportion = getProportionBetween(frameToResize.getHeight(), this.getHeight());

        if (widthProportion == heightProportion) {
            return this;
        }

        float biggerProportion = Math.max(widthProportion, heightProportion);// ? widthProportion : heightProportion;
        return new Frame(
                Math.round(frameToResize.getWidth() / biggerProportion),
                Math.round(frameToResize.getHeight() / biggerProportion));
    }

    private static float getProportionBetween(long parameter1, long parameter2) {
        return parameter1 / (float) parameter2;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Frame)) return false;

        Frame frame = (Frame) o;
        return height == frame.height && width == frame.width;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return "Frame{" + width + ", " + height + '}';
    }

    public FrameOrientation getOrientation() {
        if (width == height) {
            return ANY;
        }
        return width > height ? HORIZONTAL : VERTICAL;
    }
}
