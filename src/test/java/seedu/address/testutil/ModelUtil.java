package seedu.address.testutil;

import org.junit.Assert;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.exceptions.NonExistentUserException;
import seedu.address.model.exceptions.UserAlreadyExistsException;
import seedu.address.model.user.Username;

/**
 * Helper methods related to models.
 */
public class ModelUtil {
    public static final Username TEST_USERNAME = new Username("AAA");

    /**
     * Creates a model with a test user that is already logged in with username TEST_USERNAME. The address book is
     * empty.
     * @return a model logged in with a test user
     */
    public static Model modelWithTestUser() {
        Model model = new ModelManager();
        try {
            model.addUser(TEST_USERNAME);
            model.loadUserData(TEST_USERNAME);
        } catch (UserAlreadyExistsException uaee) {
            org.junit.Assert.fail("User already exists in newly created model.");
        } catch (NonExistentUserException e) {
            Assert.fail("Error loading user data, user was not added to model.");
        }
        return model;
    }
}