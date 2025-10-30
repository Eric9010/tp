package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Event;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import javafx.util.Pair;

/**
 * Displays a summary dashboard showing total contacts and tag distribution.
 */
public class SummaryDashboard extends UiPart<Region> {
    private static final String FXML = "SummaryDashboard.fxml";
    private static final DateTimeFormatter EVENT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @FXML
    private Label totalContactsLabel;

    @FXML
    private PieChart tagPieChart;

    @FXML
    private VBox upcomingEventsBox;

    /**
     * The dashboard displays summary statistics
     */
    public SummaryDashboard(ObservableList<Person> personList) {
        super(FXML);
        updateDashboard(personList);

        // Listen for dynamic updates
        personList.addListener((javafx.collections.ListChangeListener.Change<? extends Person> change) ->
                updateDashboard(personList));
    }

    private void updateDashboard(ObservableList<Person> personList) {
        int total = personList.size();
        totalContactsLabel.setText(String.valueOf(total));

        // Count tag frequencies
        Map<String, Integer> tagCountMap = new HashMap<>();
        for (Person person : personList) {
            for (Tag tag : person.getTags()) {
                tagCountMap.put(tag.tagName, tagCountMap.getOrDefault(tag.tagName, 0) + 1);
            }
        }

        ObservableList<PieChart.Data> pieData = javafx.collections.FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : tagCountMap.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        tagPieChart.setData(pieData);

        updateUpcomingEvents(personList);
    }

    /**
     * Finds and displays upcoming events for today and tomorrow.
     */
    private void updateUpcomingEvents(ObservableList<Person> personList) {
        upcomingEventsBox.getChildren().clear();

        // Sorted list of all upcoming events.
        List<Pair<Person, Event>> upcomingEvents = findUpcomingEvents(personList);
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        if (upcomingEvents.isEmpty()) {
            Label noEventsLabel = new Label("No upcoming events.");
            noEventsLabel.setStyle("-fx-text-fill: #999999;");
            upcomingEventsBox.getChildren().add(noEventsLabel);
        } else {
            // Limit display to up to 3 upcoming events.
            int count = 0;
            for (Pair<Person, Event> pair : upcomingEvents) {
                if (count >= 3) {
                    break;
                }

                Event event = pair.getValue();
                Person person = pair.getKey();
                LocalDateTime eventStart = LocalDateTime.parse(event.getStart(), EVENT_DATE_FORMATTER);
                LocalDateTime eventEnd = LocalDateTime.parse(event.getEnd(), EVENT_DATE_FORMATTER); // <-- MODIFIED: Get end time

                // Create event label.
                String titleText = String.format("%s (%s)", event.getTitle(), person.getName().fullName);
                Label titleLabel = new Label(titleText);
                titleLabel.setStyle("-fx-text-fill: #eeeeee;");
                titleLabel.setWrapText(true);

                // Add time label to each event.
                String startTimeStr = eventStart.toLocalTime().format(TIME_FORMATTER);
                String endTimeStr = eventEnd.toLocalTime().format(TIME_FORMATTER);
                String dayStr = "";
                if (eventStart.toLocalDate().isEqual(today)) {
                    dayStr = "Today";
                } else if (eventStart.toLocalDate().isEqual(tomorrow)) {
                    dayStr = "Tomorrow";
                }

                // Combined label into one/.
                String timeText = String.format("%s, %s - %s", dayStr, startTimeStr, endTimeStr);
                Label timeLabel = new Label(timeText);
                timeLabel.setStyle("-fx-text-fill: #999999; -fx-font-style: italic; -fx-font-size: 10px;");

                VBox eventEntryBox = new VBox(2);
                eventEntryBox.getChildren().addAll(titleLabel, timeLabel);

                upcomingEventsBox.getChildren().add(eventEntryBox);

                count++;
            }

            // Show "..." indicator if there are more than 3 events.
            if (upcomingEvents.size() > 3) { // <-- CHANGED from 4 to 3
                Label moreEventsLabel = new Label("...");
                moreEventsLabel.setStyle("-fx-text-fill: #999999; -fx-font-weight: bold;");
                upcomingEventsBox.getChildren().add(moreEventsLabel);
            }
        }
    }

    private List<Pair<Person, Event>> findUpcomingEvents(ObservableList<Person> personList) {
        // List of all events that occur today or tomorrow.
        List<Pair<Person, Event>> upcoming = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        // Iterate through every person and their associated events.
        for (Person person : personList) {
            for (Event event : person.getEvents()) {
                try {
                    LocalDateTime eventStart = LocalDateTime.parse(event.getStart(), EVENT_DATE_FORMATTER);
                    LocalDate eventDate = eventStart.toLocalDate();

                    // Check if event starts today or tomorrow.
                    if (eventDate.isEqual(today) || eventDate.isEqual(tomorrow)) {
                        upcoming.add(new Pair<>(person, event));
                    }
                } catch (Exception e) {
                    // For any events with invalid date formats.
                }
            }
        }

        // Sort so that event with the earliest start time is displayed first.
        upcoming.sort(Comparator.comparing(pair ->
                LocalDateTime.parse(pair.getValue().getStart(), EVENT_DATE_FORMATTER)));

        return upcoming;
    }
}
