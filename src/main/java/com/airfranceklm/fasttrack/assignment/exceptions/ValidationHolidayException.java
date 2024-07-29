package com.airfranceklm.fasttrack.assignment.exceptions;

public class ValidationHolidayException extends ValidationException{

    public ValidationHolidayException(String message) {
        super("Error in validating holiday. Message: " + message);
    }
}
