package seedu.address.storage;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import seedu.address.model.budget.Budget;
import seedu.address.storage.storageutil.LocalDateTimeAdapter;


//@author winsonhys
/**
 * JAXB-friendly adapted version of the Budget.
 */

public class XmlAdaptedBudget {

    @XmlElement
    private double budgetCap;
    @XmlElement
    private double currentExpenses;
    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime nextRecurrence;
    @XmlElement
    private long numberOfSecondsToRecurAgain;


    /**
     * Constructs an XmlAdaptedBudget.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBudget() {
    }

    /**
     * Converts a given Budget into this class for JAXB use.
     *
     * @param source source budget
     */
    public XmlAdaptedBudget(Budget source) {
        this.budgetCap = source.getBudgetCap();
        this.currentExpenses = source.getCurrentExpenses();
        this.nextRecurrence = source.getNextRecurrence();
        this.numberOfSecondsToRecurAgain = source.getNumberOfSecondsToRecurAgain();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     */
    public Budget toModelType() {
        return new Budget(this.budgetCap, currentExpenses, this.nextRecurrence, this.numberOfSecondsToRecurAgain);
    }



}
