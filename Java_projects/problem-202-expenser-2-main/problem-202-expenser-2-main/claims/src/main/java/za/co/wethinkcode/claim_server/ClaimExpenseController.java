package za.co.wethinkcode.claim_server;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import za.co.wethinkcode.app.db.DataRepository;
import za.co.wethinkcode.app.model.Claim;
import za.co.wethinkcode.app.model.Expense;
import za.co.wethinkcode.app.model.Person;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for handling calls from form submits for Claims
 */
public class ClaimExpenseController {
    public static final String PATH = "/claimexpense";

    public static void renderClaimExpensePage(Context context){
        UUID expenseId = UUID.fromString(context.queryParam("expenseId"));

        Optional<Expense> maybeExpense = DataRepository.getInstance().getExpense(expenseId);
        if (maybeExpense.isEmpty()) {
            context.status(HttpCode.BAD_REQUEST);
            context.result("Invalid expense");
            return;
        }
        Expense expense = maybeExpense.get();
        context.sessionAttribute("expense", expense);
        context.render("claimexpense.html",
                Map.of("newClaim", new Claim( expense, new Person("email@domain.com"), 0.0, LocalDate.now() ),
                        "expense", expense));
    }
}