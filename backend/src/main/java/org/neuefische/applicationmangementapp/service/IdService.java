package org.neuefische.applicationmangementapp.service;

import java.util.UUID;

public class IdService {
    IdService() {
        throw new IllegalStateException("Utility class");
    }

    public static String getId() {
        return UUID.randomUUID().toString();
    }
}
