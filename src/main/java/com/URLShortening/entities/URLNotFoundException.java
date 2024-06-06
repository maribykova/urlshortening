package com.URLShortening.entities;

public class URLNotFoundException extends RuntimeException{
    public URLNotFoundException(Object id) {
        super("URL not found with id " + id);
    }
}
