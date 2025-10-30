package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Displays a summary dashboard showing total contacts and tag distribution.
 */
public class SummaryDashboard extends UiPart<Region> {
    private static final String FXML = "SummaryDashboard.fxml";

    @FXML
    private Label totalContactsLabel;

    @FXML
    private PieChart tagPieChart;

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
    }
}
