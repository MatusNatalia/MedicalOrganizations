package ru.nsu.g20202.nmatus.medicalorg.exceptions;

import java.util.List;

public class IllegalValueException extends RuntimeException{
    private List<String> messages;

    public IllegalValueException(List<String> messages){
        this.messages = messages;
    }

    public List<String> getMessages(){
        return messages;
    }

}
