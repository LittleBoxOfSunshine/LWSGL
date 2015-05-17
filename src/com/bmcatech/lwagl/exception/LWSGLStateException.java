package com.bmcatech.lwagl.exception;

public class LWSGLStateException extends LWSGLException {
    public LWSGLStateException(String message) {
        super("");
        System.out.println("An LWSGLStateException has occurred:");
        System.out.println(message);
    }

    public LWSGLStateException(Throwable throwable){
        super(throwable);
        System.out.println("An LWSGLStateException has occurred:");
    }

    public LWSGLStateException(String message, Throwable throwable){
        super("", throwable);
        System.out.println("An LWSGLStateException has occurred:");
        System.out.println(message);
    }
}