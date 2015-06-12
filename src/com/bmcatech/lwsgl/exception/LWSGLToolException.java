package com.bmcatech.lwsgl.exception;

public class LWSGLToolException extends LWSGLException {
    public LWSGLToolException(String message) {
        super("");
        System.out.println("An LWSGLToolException has occurred:");
        System.out.println(message);
    }

    public LWSGLToolException(Throwable throwable){
        super(throwable);
        System.out.println("An LWSGLToolException has occurred:");
    }

    public LWSGLToolException(String message, Throwable throwable){
        super("", throwable);
        System.out.println("An LWSGLToolException has occurred:");
        System.out.println(message);
    }
}