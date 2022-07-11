package com.codedifferently.tankofamerica.domain.account.exceptions;

public class OverDraftException extends Exception{
    public OverDraftException(String message) {
        super(message);
    }
}
