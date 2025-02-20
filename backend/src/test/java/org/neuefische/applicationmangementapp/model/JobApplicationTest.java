package org.neuefische.applicationmangementapp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JobApplicationTest {
    @Test
    void testReminderNullToString() {
        String gotten = new JobApplicationTracker(null, new Application("testA", "testB", "testA.pdf", null, ApplicationStatus.OPEN, null, LocalDate.of(2025, 1, 20)), null).toString();
        String expected = "jobApplicationTracker" + null;
        assertEquals(expected, gotten);
    }

    @Test
    void testReminderToString() {
        String gotten = new JobApplicationTracker(null, new Application("testA", "testB", "testA.pdf", null, ApplicationStatus.OPEN, LocalDateTime.of(2025, 10, 12, 15, 0), LocalDate.of(2025, 1, 20)), null).toString();
        String expected = "jobApplicationTracker" + "2025-10-12T15:00";
        assertEquals(expected, gotten);
    }
}
