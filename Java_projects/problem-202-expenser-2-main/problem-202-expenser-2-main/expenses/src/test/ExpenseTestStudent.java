package za.co.wethinkcode.weshare.model;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.weshare.TestData;
import za.co.wethinkcode.weshare.app.model.Expense;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTests {

    @Test
    public void newExpenseHasAnId() {
        Expense e = TestData.lunchExpense(500.00);
        assertNotNull(e.getId());
    }

    @Test
    void emptyDescriptionDefaultsToUnspecified() {
        Expense e = new Expense(100.00, LocalDate.of(2022, 11, 28), "", TestData.me());
        assertEquals("Unspecified", e.getDescription());
    }

    @Test
    void nullDescriptionDefaultsToUnspecified() {
        Expense e = new Expense(250.00, LocalDate.of(2020, 12, 12), null, TestData.me());
        assertEquals("Unspecified", e.getDescription());
    }


}