package com.ncl.ccp.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NCLException extends Exception{
    private static final Logger LOGGER = LoggerFactory.getLogger(NCLException.class);
    String message ="";

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
