package ru.vangercat.rusjogging.frameutil;

import static ru.vangercat.rusjogging.frameutil.FrameOrientation.*;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 08.01.12
 * Time: 23:39
 */
public class Frame {

    private final long width;
    private final long height;

    public Frame(long width, long height) {
        this.width = width;
        this.height = height;
    }

    public Frame getFrameSizedToFit(Frame frameToResize) {
        double widthProportion = getProportionBetween(frameToResize.getWidth(), this.getWidth());
        double heightProportion = getProportionBetween(frameToResize.getHeight(), this.getHeight());

        if (widthProportion == heightProportion) {
            return this;
        }

        double biggerProportion = widthProportion > heightProportion ? widthProportion : heightProportion;
        return new Frame(
                Math.round(frameToResize.getWidth() / biggerProportion),
                Math.round(frameToResize.getHeight() / biggerProportion));
    }

    private static double getProportionBetween(long parameter1, long parameter2) {
        return parameter1 / (double) parameter2;
    }

    public long getWidth() {
        return width;
    }

    public long getHeight() {
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
        int result = (int) (width ^ (width >>> 32));
        result = 31 * result + (int) (height ^ (height >>> 32));
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
