package ru.vangercat.rusjogging.frameutil;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 08.01.12
 * Time: 23:47
 */
public class FrameTest {

    private int width;
    private int height;
    private Frame frame;

    @Before
    public void setUp() throws Exception {
        width = 200;
        height = 600;
        frame = new Frame(width, height);
    }

    @Test
    public void testFrameCreatedProperly() {
        assertEquals(frame.getWidth(), width);
        assertEquals(frame.getHeight(), height);
    }

    @Test
    public void testFrameIsEqulsWorksProperly() throws Exception {
        assertEquals(new Frame(width, height), frame);
    }

    @Test
    public void testNotResizedIfSizeIsTheSame() throws Exception {
        Frame frameToBeResized = new Frame(width, height);
        Frame resizedFrame = frame.getFrameSizedToFit(frameToBeResized);
        assertEquals(frameToBeResized, resizedFrame);
    }

    @Test
    public void givenFrameSimilirToOuterFrameThenResizedFrameEqualToOuterFrame() throws Exception {
        Frame frameToBeResized = new Frame(width * 2, height * 2);
        Frame resizedFrame = frame.getFrameSizedToFit(frameToBeResized);
        assertEquals(frame, resizedFrame);
    }

    @Test
    public void givenFrameWiderThenOuterFrameAndHasSameHeightThanResizedFrameShouldHaveSameWidthWithOuterAndDecreasedHeight() throws Exception {
        Frame frameToBeResized = new Frame(width * 2, height);
        Frame resizedFrame = frame.getFrameSizedToFit(frameToBeResized);
        assertEquals(new Frame(width, height / 2), resizedFrame);
    }

    @Test
    public void givenFrameHigherThenOuterFrameAndHasSameWidthThanResizedFrameShouldHaveSameHeightWithOuterAndDecreasedWidth() throws Exception {
        Frame frameToBeResized = new Frame(width, height * 2);
        Frame resizedFrame = frame.getFrameSizedToFit(frameToBeResized);
        assertEquals(new Frame(width / 2, height), resizedFrame);
    }

    @Test
    public void givenFrameSmallerThenOuterFrameThenResizedFrameEqualToOuterFrame() throws Exception {
        Frame frameToBeResized = new Frame(width / 4, height / 4);
        Frame resizedFrame = frame.getFrameSizedToFit(frameToBeResized);
        assertEquals(frame, resizedFrame);
    }

    @Test
    public void GivenFrameHasHeightBiggerThenWidthThanOrientationVertical() throws Exception {
        Frame verticalFrame = new Frame(1, 2);
        assertEquals(FrameOrientation.VERTICAL, verticalFrame.getOrientation());
    }

    @Test
    public void GivenFrameHasWidthBiggerThenHeightThanOrientationHorizontal() throws Exception {
        Frame verticalFrame = new Frame(2, 1);
        assertEquals(FrameOrientation.HORIZONTAL, verticalFrame.getOrientation());
    }

    @Test
    public void GivenFrameHasEqualWidthAndHeightThanOrientationAny() throws Exception {
        Frame verticalFrame = new Frame(2, 2);
        assertEquals(FrameOrientation.ANY, verticalFrame.getOrientation());
    }
}
