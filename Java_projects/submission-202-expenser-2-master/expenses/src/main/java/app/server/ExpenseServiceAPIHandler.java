package app.server;

import app.database.DataRepository;
import app.model.Expense;
import app.model.Person;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.NotFoundResponse;

import java.time.LocalDate;
import java.util.*;

public class ExpenseServiceAPIHandler {

    public void getExpenseByID(Context context) {
        String stringId = context.pathParamAsClass("id", String.class).get();

        UUID id;

        try {
            id = UUID.fromString(stringId);
        } catch (IllegalArgumentException exception) {
            throw new BadRequestResponse("Malformed ID: " + stringId);
        }

        DataRepository dataBase = DataRepository.getInstance();

        if (dataBase.getExpense(id).isEmpty()) {
            throw new NotFoundResponse("Expense not found: " + id);
        }

        Expense expense = dataBase.getExpense(id).get();

        context.json(expense);
    }

    public void getExpensesByEmail(Context context) {
        String email = context.queryParam("email");

        DataRepository dataBase = DataRepository.getInstance();

        if (dataBase.findPerson(email).isEmpty()) {
            throw new BadRequestResponse("Email not found: " + email);
        }

        Person person = dataBase.findPerson(email).get();

        ImmutableList<Expense> expenses = dataBase.getExpenses(person);


        context.json(expenses);
    }

    public void expenseController(Context context) {
        JsonNode expenseNode = context.bodyAsClass(JsonNode.class);
        if (expenseNode.has("expense")) {
            createExpense(context, expenseNode);
        } else if (expenseNode.has("addClaim")) {
            addClaimToExpense(context, expenseNode);
        } else if (expenseNode.has("settleClaim")) {
            settleClaimInExpense(context, expenseNode);
        } else {
            throw new BadRequestResponse("Incorrect body format");
        }
    }

    private void addClaimToExpense(Context context, JsonNode expenseNode) {
        try {
            DataRepository dataBase = DataRepository.getInstance();
            JsonNode expenseDetails = expenseNode.get("addClaim");
            UUID id = UUID.fromString(expenseDetails.get("id").asText());
            double claimAmount = expenseDetails.get("claimAmount").asDouble();
            Expense expense = dataBase.getExpense(id).get();
            expense.addClaim(claimAmount);
            context.status(HttpCode.OK);
        } catch (Exception e) {
            throw new BadRequestResponse("Incorrect body format");
        }
    }

    private void settleClaimInExpense(Context context, JsonNode expenseNode) {
        try {
            DataRepository dataBase = DataRepository.getInstance();
            JsonNode expenseDetails = expenseNode.get("settleClaim");
            UUID id = UUID.fromString(expenseDetails.get("id").asText());
            double settleAmount = expenseDetails.get("settleAmount").asDouble();
            Expense expense = dataBase.getExpense(id).get();
            expense.settleClaim(settleAmount);
            context.status(HttpCode.OK);
        } catch (Exception e) {
            throw new BadRequestResponse("Incorrect body format");
        }
    }

    public void createExpense(Context context, JsonNode expenseNode) {
        try {
            DataRepository dataBase = DataRepository.getInstance();
            JsonNode expenseDetails = expenseNode.get("expense");

//        Get details from the expense
            String email = expenseDetails.get("person").asText();
            double amount = expenseDetails.get("amount").asDouble();
            String description = expenseDetails.get("description").asText();

//        Create LocalDate from string
            String dateAsString = expenseDetails.get("date").asText();
            int dateYear = Integer.parseInt(dateAsString.split("-")[0]);
            int dateMonth = Integer.parseInt(dateAsString.split("-")[1]);
            int dateDay = Integer.parseInt(dateAsString.split("-")[2]);
            LocalDate date = LocalDate.of(dateYear, dateMonth, dateDay);

//        Find or add the person
            Person person;
            if (dataBase.findPerson(email).isEmpty()) {
                person = new Person(email);
                dataBase.addPerson(person);
            } else {
                person = dataBase.findPerson(email).get();
            }

//        Add the expense
            Expense expense = new Expense(amount, date, description, person);
            dataBase.addExpense(expense);

            context.status(HttpCode.OK);
        } catch (Exception e) {
            throw new BadRequestResponse("Incorrect body format");
        }
    }

    public void getInfoForPerson(Context context) {
        String email = context.queryParam("email");

        DataRepository dataBase = DataRepository.getInstance();

        if (dataBase.findPerson(email).isEmpty()) {
            throw new BadRequestResponse("Email not found: " + email);
        }

        Person person = dataBase.findPerson(email).get();

        Map<String, Double> summary =
                Map.of("sumOfExpenses", dataBase.getTotalExpensesAmountFor(person),
                        "sumOfUnsettledClaims", dataBase.getTotalUnsettledClaimAmountFor(person),
                        "sumOfSettledClaims", dataBase.getTotalSettledClaimAmountFor(person),
                        "sumOfNettExpenses", dataBase.getNettExpensesAmountFor(person));

        context.json(summary);
    }
}