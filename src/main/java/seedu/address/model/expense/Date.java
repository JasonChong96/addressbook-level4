package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

//@@author jonathantjm
/**
 * Represents the date the Expense was added into the Expense tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String DATE_FORMAT_CONSTRAINTS =
            "Date should be valid. Format dd-MM-yyyy";

    public static final String DATE_VALIDATION_REGEX = "(\\d{1,2})(\\-)(\\d{1,2})(\\-)(\\d{4})";
    public final Calendar fullDate = Calendar.getInstance();


    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), DATE_FORMAT_CONSTRAINTS);
        String [] parsedDate = date.split("-");
        fullDate.set(Integer.parseInt(parsedDate[2]),
                Integer.parseInt(parsedDate[1]) - 1,
                Integer.parseInt(parsedDate[0]));
    }

    /**
     * Constructs a {@code Date} with current date.
     *
     */
    public Date() {}

    /**
     * return true is the given date is in the valid format.
     * */
    public static boolean isValidDate(String test) {
        if (test.matches(DATE_VALIDATION_REGEX)) {
            Calendar date = new GregorianCalendar();
            String [] parsedDate = test.split("-");
            date.setLenient(false);
            date.set(Integer.parseInt(parsedDate[2]),
                    Integer.parseInt(parsedDate[1]) - 1,
                    Integer.parseInt(parsedDate[0]));
            try {
                date.getTime();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * return true if {@code this} is earlier than {@param other}
     * */
    //@@author Jiang Chen
    public boolean isEalierThan(Date other) {

        int thisYear = this.fullDate.get(Calendar.YEAR);
        int otherYear = other.fullDate.get(Calendar.YEAR);
        if (thisYear < otherYear) {
            return true;
        }
        if (thisYear > otherYear) {
            return false;
        }


        int thisMonth = this.fullDate.get(Calendar.MONTH);
        int otherMonth = other.fullDate.get(Calendar.MONTH);
        if (thisMonth < otherMonth) {
            return true;
        }
        if (thisMonth > otherMonth) {
            return false;
        }


        int thisDay = this.fullDate.get(Calendar.DAY_OF_YEAR);
        int otherDay = other.fullDate.get(Calendar.DAY_OF_YEAR);

        if (thisDay < otherDay) {
            return true;
        }
        if (thisDay > otherDay) {
            return false;
        }

        return false;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(fullDate.getTime());
    }

    /**
     * Returns true if both Dates represent the same calendar date.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return fullDate.get(Calendar.DAY_OF_YEAR) == otherDate.fullDate.get(Calendar.DAY_OF_YEAR)
                && fullDate.get(Calendar.MONTH) == otherDate.fullDate.get(Calendar.MONTH)
                && fullDate.get(Calendar.YEAR) == otherDate.fullDate.get(Calendar.YEAR);
    }

    /**
     *
     * @param a - First Date to compare
     * @param b - Second Date to compare
     * @return 1 if b is after a, -1 if b is before a and 0 if they are equal
     */
    public static int compare(Date a, Date b) {
        if (a.equals(b)) {
            return 0;
        } else if (b.fullDate.after(a.fullDate)) {
            return 1;
        }
        return -1;
    }
}
