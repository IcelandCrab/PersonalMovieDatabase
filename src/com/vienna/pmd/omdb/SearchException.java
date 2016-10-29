package com.vienna.pmd.omdb;

/**
 * Created by bobmo on 29.10.2016.
 */
public class SearchException extends Exception {

    public SearchException(String message) {
        super(message);
    }

    public SearchException(String message, Exception root) {
        super(message, root);
    }
}
