package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Event;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        Person alex = new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Google"), getTagSet("Collegue"), null, new HashSet<Event>());
        alex.addEvent(new Event("Team Lunch", "2025-10-20", "12:00", "F2F", "Google Office",
                "Lunch with Alex"));
        alex.addEvent(new Event("Project Deadline", "2025-11-01", "17:00", null,
                "Workstation", "Submit report for Alex"));

        Person bernice = new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Meta"), getTagSet("Interviewer", "Recruiter"), null,
                new HashSet<Event>());
        bernice.addEvent(new Event("Interview", "2025-10-18", "15:00", "ZOOM", "Online",
                "Super important interview"));

        Person charlotte = new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"), new Address("NUS"), getTagSet("Friend"), null,
                new HashSet<Event>());
        charlotte.addEvent(new Event("Coffee Chat", "2025-10-19", "12:00", null,
                "Starbucks @ Vivo",null));

        return new Person[] {
            alex, bernice, charlotte
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
