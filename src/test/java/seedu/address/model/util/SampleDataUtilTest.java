package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsNonEmptyArray() {
        // Run the method to be tested
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        // Check that it returns something and isn't empty
        assertTrue(samplePersons.length > 0, "Should return a non-empty array of sample persons");
    }

    @Test
    public void getSampleAddressBook_returnsPopulatedAddressBook() {
        // Run the method to be tested
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();

        // Check that the address book created is not empty
        assertTrue(sampleAddressBook.getPersonList().size() > 0, "Should return an address book with sample persons");
    }
}