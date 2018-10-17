package seedu.address.ui;

//import java.util.logging.Logger;

//import javafx.application.Platform;
//import javafx.collections.ObservableList;
import javafx.fxml.FXML;
//import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
//import seedu.address.commons.core.LogsCenter;
//import seedu.address.model.expense.Expense;

//@@author snookerballs
/**
 * Panel containing the notifications.
 */
public class NotificationPanel extends UiPart<Region> {
    private static final String FXML = "NotificationPanel.fxml";
    //private final Logger logger = LogsCenter.getLogger(NotificationPanel.class);

    @FXML
    private ListView<Object> notificationListView;

    public NotificationPanel() {
        super(FXML);
    }

    /*public NotificationPanel(ObservableList<Object> notificationList) {
        super(FXML);
        //setConnections(notificationList);
        registerAsAnEventHandler(this);
    }*/

    /*private void setConnections(ObservableList<Object> notificationList) {
        notificationListView.setItems(notificationList);
        //notificationListView.setCellFactory(listView -> new ExpenseListPanel.ExpenseListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        notificationListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in expense list panel changed to : '" + newValue + "'");
                        //raise(new ExpensePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ExpenseCard} at the {@code index} and selects it.
     */
    /* private void scrollTo(int index) {
        Platform.runLater(() -> {
            notificationListView.scrollTo(index);
            notificationListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Expense} using a {@code ExpenseCard}.
     */
    /*class ExpenseListViewCell extends ListCell<Expense> {
        @Override
        protected void updateItem(Expense expense, boolean empty) {
            super.updateItem(expense, empty);

            if (empty || expense == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExpenseCard(expense, getIndex() + 1).getRoot());
            }
        }
    }
    */
}
