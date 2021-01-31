package com.unobtainumsoftware.lwsgl.exception;

public class LWSGLMapException extends LWSGLException {
    public LWSGLMapException(String message) {
        super("");
        System.out.println("An LWSGLMapException has occurred:");
        System.out.println(message);
    }

    public LWSGLMapException(Throwable throwable){
        super(throwable);
        System.out.println("An LWSGLMapException has occurred:");
    }

    public LWSGLMapException(String message, Throwable throwable){
        super("", throwable);
        System.out.println("An LWSGLMapException has occurred:");
        System.out.println(message);
    }
}