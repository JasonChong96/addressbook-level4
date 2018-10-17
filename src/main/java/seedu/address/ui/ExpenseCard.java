package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.expense.Expense;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class ExpenseCard extends UiPart<Region> {

    private static final String FXML = "ExpenseListCard.fxml";
    private static final String[] TAG_COLORS = {"teal", "red", "yellow", "blue", "orange", "brown", "green",
        "pink", "black", "grey"};
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Expense expense;
    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label category;
    @FXML
    private Label cost;
    @FXML
    private Label date;
    @FXML
    private FlowPane tags;

    public ExpenseCard(Expense expense, int displayedIndex) {
        super(FXML);
        this.expense = expense;
        id.setText(displayedIndex + ". ");
        name.setText(expense.getName().expenseName);
        category.setText(expense.getCategory().categoryName);
        cost.setText(expense.getCost().value);
        date.setText(expense.getDate().toString());
        expense.getTags().forEach(tag -> {
            Label tempLabel = new Label(tag.tagName);
            tempLabel.setStyle("-fx-background-color: " + getColorStyleOfTag(tag.tagName));
            tags.getChildren().add(tempLabel);
        });

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpenseCard)) {
            return false;
        }

        // state check
        ExpenseCard card = (ExpenseCard) other;
        return id.getText().equals(card.id.getText())
                && expense.equals(card.expense);
    }

    /**
     * Returns color style for a specific tag
     */
    public static String getColorStyleOfTag(String tagName) {
        return TAG_COLORS[Math.abs(tagName.hashCode() % TAG_COLORS.length)];
    }
}
