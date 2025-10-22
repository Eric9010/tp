package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void constructor_nullTitle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(null, "2025-09-10 15:50",
                "2025-09-10 15:50", null, null, null));
    }

    @Test
    public void constructor_nullStart_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event("Google Interview", null,
                "2025-09-10 15:50", null, null, null));
    }

    @Test
    public void constructor_nullEnd_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event("Google Interview", "2025-09-10 15:50",
                null, null, null, null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Event("", "2025-09-10 15:50",
                "2025-09-10 15:50", null, null, null));
    }

    @Test
    public void constructor_invalidStart_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Event("Google Interview", "2025",
                "2025-09-10 15:50", null, null, null));
    }

    @Test
    public void constructor_invalidEnd_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Event("Google Interview", "2025-09-10 15:50",
                "2025-09-10 15", null, null, null));
    }

    @Test
    public void constructor_startAfterEnd_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Event("Google Interview", "2025-09-10 15:50",
                "2025-09-10 15:00", null, null, null));
    }

    @Test
    public void constructor_invalidMode_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Event("Google Interview", "2025-09-10",
                "15:50", "Google Meet", null, null));
    }

    @Test
    public void constructor_invalidRemarks_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Event("Google Interview", "2025-09-10",
                "15:50", null, null, "Very long remarkssssssssssssssssssssssssssssss"
                + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
                + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
                + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
                + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
                + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"));
    }

    @Test
    public void constructor_validEventWithNoOptionalFields_success() {
        assertDoesNotThrow(() -> new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                null, null, null));
    }

    @Test
    public void constructor_validEventWithOneOptionalField_success() {
        assertDoesNotThrow(() -> new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                "f2f", null, null));
        assertDoesNotThrow(() -> new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                null, "Google Headquarters", null));
        assertDoesNotThrow(() -> new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                null, null, "Technical Interview - Practise Leetcode"));
    }

    @Test
    public void constructor_validEventWithAllOptionalFields_success() {
        assertDoesNotThrow(() -> new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                "f2f", "Google Headquarters", "Final Round"));
    }

    @Test
    public void isValidTitle_emptyTitle() {
        assertFalse(Event.isValidTitle(""));
    }

    @Test
    public void isValidTitle_validTitle() {
        assertTrue(Event.isValidTitle("Google Interview"));
    }

    @Test
    public void isValidStartEnd_invalidStartEnd() {
        assertFalse(Event.isValidStartEnd("", ""));
        assertFalse(Event.isValidStartEnd("2025-10-20", "15:00"));
        assertFalse(Event.isValidStartEnd("2025-13-12 15:00", "2025-13-12 2459"));
        assertFalse(Event.isValidStartEnd("2025-10-20 15:50", "2025-10-20 15:00"));
    }

    @Test
    public void isValidDate_validStartEnd() {
        assertTrue(Event.isValidStartEnd("2025-09-10 15:00", "2025-09-11 15:00"));
    }

    @Test
    public void isValidMode_invalidMode() {
        assertFalse(Event.isValidMode(""));
        assertFalse(Event.isValidMode("Physical"));
    }

    @Test
    public void isValidMode_validMode() {
        assertTrue(Event.isValidMode(null));
        assertTrue(Event.isValidMode("ZOOM"));
        assertTrue(Event.isValidMode("cAlL"));
        assertTrue(Event.isValidMode("f2f"));
    }

    @Test
    public void isValidLocation_invalidLocation() {
        assertFalse(Event.isValidLocation(""));
    }

    @Test
    public void isValidLocation_validLocation() {
        assertTrue(Event.isValidLocation(null));
        assertTrue(Event.isValidLocation("Google Headquarters"));
    }

    @Test
    public void isValidRemark_invalidRemark() {
        assertFalse(Event.isValidRemark(""));
        assertFalse(Event.isValidRemark("Very long remarksssssssssssssssssssssssssssssssssssssssssssssss"
                + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
                + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
                + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
                + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
                + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"));
    }

    @Test
    public void isValidRemark_validRemark() {
        assertTrue(Event.isValidRemark(null));
        assertTrue(Event.isValidRemark("Final Round"));
    }

    @Test
    public void isValidEvent_invalidEventAttributes() {
        assertFalse(Event.isValidEvent("", "2025-09-19 15:00", "2025-09-19 15:00", null,
                null, null));
        assertFalse(Event.isValidEvent("Google Interview", "2025-09", "2025-09-19 15:00", null,
                null, null));
        assertFalse(Event.isValidEvent("Google Interview", "2025-09-19 15:00", "15:", null,
                null, null));
        assertFalse(Event.isValidEvent("Google Interview", "2025-09-19 15:00", "2025-09-19 15:00",
                "GMeet", null, null));
        assertFalse(Event.isValidEvent("Google Interview", "2025-09-19 15:00", "2025-09-19 15:00",
                null, "", null));
        assertFalse(Event.isValidEvent("Google Interview", "2025-09-19 15:00", "2025-09-19 15:00",
                null, null, ""));
    }

    @Test
    public void isValidEvent_validEventAttributes() {
        assertTrue(Event.isValidEvent("Google Interview", "2025-09-19 15:00", "2025-09-19 15:00",
                null, null, null));
        assertTrue(Event.isValidEvent("Google Interview", "2025-09-19 15:00", "2025-09-19 15:00",
                "Zoom", null, "Technical Interview"));
        assertTrue(Event.isValidEvent("Google Interview", "2025-09-19 15:00", "2025-09-19 15:00",
                null, "Google Headquarters", null));
    }

    @Test
    public void equals_sameEventObject() {
        Event event = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                "f2f", "Google Headquarters", "Final Round");
        assertEquals(event, event);
    }

    @Test
    public void equals_notEventObject() {
        Event event = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                "f2f", "Google Headquarters", "Final Round");
        Email email = new Email("valid@email");
        assertNotEquals(event, email);
    }

    @Test
    public void equals_sameAttributes() {
        Event event1 = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                "f2f", "Google Headquarters", "Final Round");
        Event event2 = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                "f2f", "Google Headquarters", "Final Round");
        assertEquals(event1, event2);
    }

    @Test
    public void equals_sameTitleStartEnd() {
        Event event1 = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                "f2f", "Google Headquarters", "Final Round");
        Event event2 = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                "Zoom", null, "Technical Round");
        assertEquals(event1, event2);
    }

    @Test
    public void equals_differentTitle() {
        Event event1 = new Event("Amazon Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                "f2f", "Google Headquarters", "Final Round");
        Event event2 = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                "f2f", "Google Headquarters", "Final Round");
        assertNotEquals(event1, event2);
    }

    @Test
    public void equals_differentStart() {
        Event event1 = new Event("Google Interview", "2025-10-10 15:00", "2025-10-10 15:50",
                "f2f", "Google Headquarters", "Final Round");
        Event event2 = new Event("Google Interview", "2025-09-10 15:00", "2025-10-10 15:50",
                "f2f", "Google Headquarters", "Final Round");
        assertNotEquals(event1, event2);
    }

    @Test
    public void equals_differentEnd() {
        Event event1 = new Event("Google Interview", "2025-10-10 15:00", "2025-10-10 15:40",
                "f2f", "Google Headquarters", "Final Round");
        Event event2 = new Event("Google Interview", "2025-10-10 15:00", "2025-10-10 15:50",
                "f2f", "Google Headquarters", "Final Round");
        assertNotEquals(event1, event2);
    }

    @Test
    public void toString_eventWithAllFields() {
        Event event = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50",
                "f2f", "Google Headquarters", "Final Round");
        assertEquals("""
                Google Interview 2025-09-10 15:00 to 2025-09-10 15:50 F2F Google Headquarters
                Remarks: Final Round""", event.toString());
    }

    @Test
    public void toString_eventWithNoOptionalFields() {
        Event event = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50", null,
                null, null);
        assertEquals("Google Interview 2025-09-10 15:00 to 2025-09-10 15:50", event.toString());
    }
}
