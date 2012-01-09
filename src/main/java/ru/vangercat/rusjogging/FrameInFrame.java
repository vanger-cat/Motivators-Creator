package ru.vangercat.rusjogging;

import ru.vangercat.rusjogging.frameutil.Frame;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 09.01.12
 * Time: 3:06
 */
public class FrameInFrame extends Frame {
    private final int x;
    private final int y;

    public FrameInFrame(int x, int y, int width, int height) {
        super(width, height);

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FrameInFrame)) return false;
        if (!super.equals(o)) return false;

        FrameInFrame that = (FrameInFrame) o;

        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return String.format("FrameInFrame{%d, %d, %d, %d}", getX(), getY(), getWidth(), getHeight());
    }
}
