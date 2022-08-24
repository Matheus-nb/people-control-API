package com.api.peoplecontrol.domain.error;

public class CpfAlreadyExistsException extends Exception{
    public CpfAlreadyExistsException(String message) {
        super(message);
    }
}
