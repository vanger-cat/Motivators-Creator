package ru.vangercat.rusjogging;

import com.google.inject.Inject;

import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 08.01.12
 * Time: 21:20
 */
public class MotivatorsFactory {

    @Inject
    public MotivatorsFactory() {
    }

    public BufferedImage getResult() {
        return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

}
