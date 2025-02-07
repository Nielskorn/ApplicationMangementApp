package org.neuefische.applicationmangementapp.exceptions;

public class NoSuchId extends Exception {
    public NoSuchId(String message) {
        super("no such id: " + message);
    }
}
