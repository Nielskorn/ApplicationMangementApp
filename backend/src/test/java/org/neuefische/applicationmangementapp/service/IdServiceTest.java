package org.neuefische.applicationmangementapp.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IdServiceTest {
    @Test
    void testUtilityErrorIdService() {
        Exception exception = assertThrows(IllegalStateException.class, IdService::new);
        assertEquals("Utility class", exception.getMessage());

    }
}
