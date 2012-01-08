package ru.vangercat.rusjogging;

/**
 * Created by IntelliJ IDEA.
 * User: vanger
 * Date: 07.01.12
 * Time: 22:22
 */
public class MotivatorImageException extends RuntimeException {
    public MotivatorImageException(String message, Exception e) {
        super(message, e);
    }
}
