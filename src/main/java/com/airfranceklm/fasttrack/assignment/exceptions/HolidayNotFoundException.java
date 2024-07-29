package com.airfranceklm.fasttrack.assignment.exceptions;

public class HolidayNotFoundException extends RuntimeException {

    public HolidayNotFoundException(String id) {
        super("Could not find holiday " + id);
    }
}
