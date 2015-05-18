package com.bmcatech.lwsgl.exception;

public class LWSGLException extends Exception {
    public LWSGLException(String message) {
        super(message);
        System.out.println("An uncorrectable LWSGL error has occurred:");
        System.out.println(message);
    }

    public LWSGLException(Throwable throwable){
        super(throwable);
        System.out.println("An uncorrectable LWSGL error has occurred:");
    }

    public LWSGLException(String message, Throwable throwable){
        super(message, throwable);
        System.out.println("An uncorrectable LWSGL error has occurred:");
        System.out.println(message);
    }
}