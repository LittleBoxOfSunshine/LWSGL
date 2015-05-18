package com.bmcatech.lwsgl.exception;

public class LWSGLLayerException extends LWSGLStateException {
    public LWSGLLayerException(String message) {
        super("");
        System.out.println("An LWSGLLayerException has occurred:");
        System.out.println(message);
    }

    public LWSGLLayerException(Throwable throwable){
        super(throwable);
        System.out.println("An LWSGLLayerException has occurred:");
    }

    public LWSGLLayerException(String message, Throwable throwable){
        super("", throwable);
        System.out.println("An LWSGLLayerException has occurred:");
        System.out.println(message);
    }
}