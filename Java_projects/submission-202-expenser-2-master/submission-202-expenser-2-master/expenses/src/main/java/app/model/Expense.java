package app.model;

import app.database.DataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;

import java.time.LocalDate;
import java.util.*;

/**
 * I record the details of an Expense, created when a person records an actual expense paid by them.
 * The person can then choose to (as a next step) lodge one or more claims from this expense against others.
 *
 * I use Person instances to record who incurred the Expense.
 *
 * Note that my instances are immutable.
 */
// TODO: to think about: does an expense automatically start with a claim from a person to him/herself? or is that unnecessary overhead?
// To keep things simple at the start we are assuming uniqueness based on id.

public class Expense extends AbstractTransactionModelBase {

    private final LocalDate date;
    private final String description;
    private final Person paidBy;

    private double claimsAll;

    private double claimsSettled;
//    private final Set<Claim> claims;


    //<editor-fold desc="Lifecycle">

    /**
     * Initialise an Expense instance with all it's needed data.
     * @param amount R value of the expense item
     * @param date Date on which the expense was paid
     * @param description Some descriptive text, possibly null or empty.
     * @param paidBy The Person who paid for the expense item.
     */
    public Expense(double amount, LocalDate date, String description, Person paidBy) {
        super( UUID.randomUUID(), amount );
        this.date = date;
        this.description = Strings.isNullOrEmpty(description) ? "Unspecified" : description;
        this.paidBy = paidBy;
        this.claimsSettled = 0;
        this.claimsAll = 0;
//        this.claims = new HashSet<>();
    }

    public void addClaim(double claimAmount){
        if (this.claimsAll + claimAmount < this.getAmount()){
            throw new RuntimeException("Claims exceed");
        }
        this.claimsAll = this.claimsAll + claimAmount;
    }

    public void settleClaim(double amountToSettle){
        if (this.claimsAll + this.claimsSettled < amountToSettle){
            throw new RuntimeException("Amount to settle exceeds the claims made against user in question");
        }
        this.claimsSettled = this.claimsSettled + amountToSettle;
    }

    public LocalDate getDate() { 
        return date; 
    }

    public String getDescription() {
        return description;
    }

    public Person getPaidBy() {
        return paidBy;
    }

    private void addClaimToExpense(Context context,     JsonNode expenseNode) {
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


            String email = expenseDetails.get("person").asText();
            double amount = expenseDetails.get("amount").asDouble();
            String description = expenseDetails.get("description").asText();

            String dateAsString = expenseDetails.get("date").asText();
            int dateYear = Integer.parseInt(dateAsString.split("-")[0]);
            int dateMonth = Integer.parseInt(dateAsString.split("-")[1]);
            int dateDay = Integer.parseInt(dateAsString.split("-")[2]);
            LocalDate date = LocalDate.of(dateYear, dateMonth, dateDay);

            Person person;
            if (dataBase.findPerson(email).isEmpty()) {
                person = new Person(email);
                dataBase.addPerson(person);
            } else {
                person = dataBase.findPerson(email).get();
            }

            Expense expense = new Expense(amount, date, description, person);
            dataBase.addExpense(expense);

            context.status(HttpCode.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestResponse("createExpense: Incorrect body format");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (!date.equals(expense.date)) return false;
        if (!description.equals(expense.description)) return false;
        return paidBy.equals(expense.paidBy);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + paidBy.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", paidBy=" + paidBy +
                ", id=" + id +
                ", amount=" + amount +
                '}';
    }

    public String getFormattedAmount(){
        return String.format("R %,.2f", this.amount);
    }

}