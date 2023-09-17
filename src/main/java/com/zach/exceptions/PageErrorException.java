package com.zach.exceptions;

public class PageErrorException extends RuntimeException{

    public PageErrorException(String message) {
        super(message);
    }
}
