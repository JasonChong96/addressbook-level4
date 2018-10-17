package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handle to the {@code StatisticPanel} in the GUI.
 */
public class StatisticPanelHandle extends NodeHandle<Node> {

    public static final String STATISTIC_PANEL_ID = "#statisticPanel";

    public StatisticPanelHandle(Node statisticPanelHandle) {
            super(statisticPanelHandle);
    }

}
