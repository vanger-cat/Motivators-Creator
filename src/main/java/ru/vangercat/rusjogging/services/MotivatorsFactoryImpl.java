package ru.vangercat.rusjogging.services;

import com.google.inject.Inject;
import ru.vangercat.rusjogging.Motivator;

import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 08.01.12
 * Time: 21:20
 */
public class MotivatorsFactoryImpl implements MotivatorsFactory {

    @Inject
    public MotivatorsFactoryImpl() {
    }

    @Override
    public Motivator getMotivator(BufferedImage motivationImage) {
        return new Motivator(motivationImage);
    }

}
