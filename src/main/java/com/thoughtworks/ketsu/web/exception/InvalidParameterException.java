package com.thoughtworks.ketsu.web.exception;

import java.util.ArrayList;
import java.util.List;

public class InvalidParameterException extends RuntimeException {
    private List<InvalidParamMessage> invalidParamMessagesList;

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(Exception e) {
        super(e);
    }

    public List<InvalidParamMessage> getInvalidParamMessagesList() {
        return invalidParamMessagesList;
    }

    public InvalidParameterException(List<String> invalidParamsList) {
        invalidParamMessagesList = new ArrayList<>();
        for (String invalidParam : invalidParamsList) {
            invalidParamMessagesList.add(new InvalidParamMessage(invalidParam));
        }
    }
}
