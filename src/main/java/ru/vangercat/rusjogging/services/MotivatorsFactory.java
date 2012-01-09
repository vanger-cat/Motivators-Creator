package ru.vangercat.rusjogging.services;

import com.google.inject.ImplementedBy;
import ru.vangercat.rusjogging.Motivator;

import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 09.01.12
 * Time: 12:04
 */
public
@ImplementedBy(MotivatorsFactoryImpl.class)
interface MotivatorsFactory {
    Motivator getMotivator(BufferedImage motivationImage, String motivatorText);
}
