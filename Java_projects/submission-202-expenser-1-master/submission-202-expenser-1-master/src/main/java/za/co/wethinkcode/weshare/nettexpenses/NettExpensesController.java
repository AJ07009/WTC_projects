package za.co.wethinkcode.weshare.nettexpenses;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Person;

import java.util.Map;

public class NettExpensesController {
    public static final String PATH = "/home";

    public static void renderHomePage(Context context){
        DataRepository database = DataRepository.getInstance();
        Person person = context.sessionAttribute("user");
        Map<String, Object> viewModel = Map.of(
                "userExpenses", database.getExpenses(person),
                "userClaimBy", database.getClaimsBy(person,true),
                "userClaimFrom",database.getClaimsFrom(person,true),
                "userNetExpenses", database.getNettExpensesFor(person),
                "userUnsettledClaimsBy", database.getTotalUnsettledClaimsClaimedBy(person),
                "userUnsettledClaimsFrom", database.getTotalUnsettledClaimsClaimedFrom(person),
                "userTotal", database.getTotalExpensesFor(person)
        );
        context.render("home.html", viewModel);
    }
}
