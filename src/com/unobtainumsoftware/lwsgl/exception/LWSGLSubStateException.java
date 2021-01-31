package com.unobtainumsoftware.lwsgl.exception;

public class LWSGLSubStateException extends LWSGLStateException {
    public LWSGLSubStateException(String message) {
        super("");
        System.out.println("An LWSGLSubStateException has occurred:");
        System.out.println(message);
    }

    public LWSGLSubStateException(Throwable throwable){
        super(throwable);
        System.out.println("An LWSGLSubStateException has occurred:");
    }

    public LWSGLSubStateException(String message, Throwable throwable){
        super("", throwable);
        System.out.println("An LWSGLSubStateException has occurred:");
        System.out.println(message);
    }
}