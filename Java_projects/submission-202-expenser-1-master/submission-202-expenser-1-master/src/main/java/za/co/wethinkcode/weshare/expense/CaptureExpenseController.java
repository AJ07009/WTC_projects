//package za.co.wethinkcode.weshare.expense;
//
//import io.javalin.http.Context;
//
///**
// * Controller for handling API calls for Expenses
// */
//public class CaptureExpenseController {
//
//    public static void createExpense(Context context){
//
//    }
//}
package za.co.wethinkcode.weshare.expense;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.nettexpenses.NettExpensesController;

import java.time.LocalDate;
import java.util.Map;

/**
 * Controller for handling API calls for Expenses
 */
public class CaptureExpenseController {

    public static final String PATH = "/newexpense";
    public static final String SUBMIT_PATH = "/expenseform";

    public static void renderCaptureExpensePage(Context context){
        Map<String, Object> viewModel = Map.of( );

        context.render("expenseform.html", viewModel);

    }

    public static void handleExpense(Context context) {
        Person username = (Person)context.sessionAttributeMap().get("user");
        String description = context.formParam("description");
        Double amount = Double.parseDouble(context.formParam("amount"));
        LocalDate date =  LocalDate.parse(context.formParam("date"));


        final Expense expense = DataRepository.getInstance().addExpense(new Expense(amount, date, description, username));


        context.redirect(NettExpensesController.PATH);
    }

}