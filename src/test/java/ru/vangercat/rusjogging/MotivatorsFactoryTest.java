package ru.vangercat.rusjogging;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 08.01.12
 * Time: 21:20
 */
public class MotivatorsFactoryTest {


    @Test
    public void testMotivatorCreation() throws Exception {
        BufferedImage imageForMotivator = mock(BufferedImage.class);
        when(imageForMotivator.getHeight()).thenReturn(100);
        when(imageForMotivator.getWidth()).thenReturn(100);
        Injector injector = Guice.createInjector(new TestModule());
        MotivatorsFactory motivatorsFactory = injector.getInstance(MotivatorsFactory.class);

        assertNotNull(motivatorsFactory.getResult());
    }

    /*
    frame.getWidth() = 1518
    frame.getHeight() = 2024
    upper left corner: 25, 25
    upper right corner: 1493, 25
    bottom end = 1825
     */
}
