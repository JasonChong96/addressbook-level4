package guitests.guihandles;

import static seedu.address.testutil.EventsUtil.postNow;

import javafx.stage.Stage;
import seedu.address.commons.events.ui.SwapLeftPanelEvent;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final ExpenseListPanelHandle expenseListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final BudgetPanelHandle budgetPanel;
    //private final StatisticPanelHandle statisticPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        budgetPanel = new BudgetPanelHandle(getChildNode(BudgetPanelHandle.BUDGET_PANEL_ID));
        //statisticPanel = new StatisticPanelHandle(getChildNode(StatisticPanelHandle.STATISTIC_PANEL_ID));
        postNow(new SwapLeftPanelEvent(SwapLeftPanelEvent.PanelType.LIST));
        expenseListPanel = new ExpenseListPanelHandle(getChildNode(ExpenseListPanelHandle.EXPENSE_LIST_VIEW_ID));
        postNow(new SwapLeftPanelEvent(SwapLeftPanelEvent.PanelType.STATISTIC));


    }

    public ExpenseListPanelHandle getExpenseListPanel() {
        return expenseListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public BudgetPanelHandle getBudgetPanel() {
        return budgetPanel;
    }

}
