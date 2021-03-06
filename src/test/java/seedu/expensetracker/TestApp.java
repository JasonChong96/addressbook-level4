package seedu.expensetracker;

import static seedu.expensetracker.model.encryption.EncryptionUtil.DEFAULT_ENCRYPTION_KEY;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.expensetracker.commons.core.Config;
import seedu.expensetracker.commons.core.GuiSettings;
import seedu.expensetracker.commons.exceptions.DataConversionException;
import seedu.expensetracker.commons.exceptions.IllegalValueException;
import seedu.expensetracker.commons.util.FileUtil;
import seedu.expensetracker.commons.util.XmlUtil;
import seedu.expensetracker.model.ExpenseTracker;
import seedu.expensetracker.model.Model;
import seedu.expensetracker.model.ReadOnlyExpenseTracker;
import seedu.expensetracker.model.UserPrefs;
import seedu.expensetracker.model.encryption.EncryptionUtil;
import seedu.expensetracker.model.exceptions.NoUserSelectedException;
import seedu.expensetracker.model.exceptions.NonExistentUserException;
import seedu.expensetracker.storage.UserPrefsStorage;
import seedu.expensetracker.storage.XmlSerializableExpenseTracker;
import seedu.expensetracker.testutil.TestUtil;
import seedu.expensetracker.testutil.TypicalExpenses;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("data/sampleData.xml");
    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyExpenseTracker> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyExpenseTracker> initialDataSupplier, Path saveFileLocation) throws
            IllegalValueException {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableExpenseTracker(EncryptionUtil.encryptTracker(
                    this.initialDataSupplier.get())), this.saveFileLocation);
        }
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        userPrefs.setExpenseTrackerDirPath(
                TestUtil.getFilePathInSandboxFolder(userPrefs.getExpenseTrackerDirPath().toString()));
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(800.0, 800.0, (int) x, (int) y));
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the expense tracker data stored inside the storage file.
     */
    public ExpenseTracker readStorageExpenseTracker() throws IllegalValueException {
        try {
            return new ExpenseTracker(storage.readAllExpenses(userPrefs.getExpenseTrackerDirPath()).get(
                    TypicalExpenses.SAMPLE_USERNAME).decryptTracker(DEFAULT_ENCRYPTION_KEY));
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the ExpenseTracker format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getExpensesDirPath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        try {
            Model copy = model.copy(userPrefs);
            ModelHelper.setFilteredList(copy, model.getFilteredExpenseList());
            return copy;
        } catch (NonExistentUserException | NoUserSelectedException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    /**
     * Returns the model.
     */
    public Model getActualModel() {
        return model;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, Path filePath) {
        try {
            FileUtil.createIfMissing(filePath);
            XmlUtil.saveDataToFile(filePath, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
