package org.neuefische.applicationmangementapp.service;

import java.util.UUID;

public class IdService {
    public static String getId() {
     return UUID.randomUUID().toString();
    }
}
