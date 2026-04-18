package com.sohel.hotel.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String msg)
    {
        super(msg);

    }
}
