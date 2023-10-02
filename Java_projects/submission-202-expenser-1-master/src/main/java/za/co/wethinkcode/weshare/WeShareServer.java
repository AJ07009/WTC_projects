package za.co.wethinkcode.weshare;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import za.co.wethinkcode.weshare.app.DefaultAccessManager;
import za.co.wethinkcode.weshare.app.Sessions;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.claim.ClaimExpenseController;
import za.co.wethinkcode.weshare.claim.ClaimsApiController;
import za.co.wethinkcode.weshare.expense.CaptureExpenseController;
import za.co.wethinkcode.weshare.login.LoginController;
import za.co.wethinkcode.weshare.nettexpenses.NettExpensesController;
import za.co.wethinkcode.weshare.settle.SettlementViewController;

import java.time.LocalDate;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;
//test
public class WeShareServer {
    private static final int DEFAULT_PORT = 7070;
    private static final String STATIC_DIR = "/html";


    /**
     * The main class starts the server on the default port 7070.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Javalin app = getInstance();
        app.start(DEFAULT_PORT);
    }

    /**
     * This is a convenience for running Selenium tests.
     * It allows the test to get access to the server to start and stop it.
     * @return a configured server for the app
     */
    public static Javalin getInstance() {
        configureThymeleafTemplateEngine();
        Javalin server = createAndConfigureServer();
        setupRoutes(server);
        return server;
    }

    /**
     * Setup the Thymeleaf template engine to load templates from 'resources/templates'
     */
    private static void configureThymeleafTemplateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateEngine.setTemplateResolver(templateResolver);

        templateEngine.addDialect(new LayoutDialect());
        JavalinThymeleaf.configure(templateEngine);
    }

    private static void setupRoutes(Javalin server) {
        server.routes(() -> {
            loginAndLogoutRoutes();
            homePageRoute();
            expenseRoutes();
            claimRoutes();
            settlementRoutes();
        });
    }

    private static void loginAndLogoutRoutes() {
        post(LoginController.LOGIN_PATH, LoginController::handleLogin);
        get(LoginController.LOGOUT_PATH, LoginController::handleLogout);
    }

    private static void settlementRoutes() {

    }

    private static void claimRoutes() {

    }
    
    private static void expenseRoutes() {
        path(CaptureExpenseController.PATH, () -> get(CaptureExpenseController::renderCaptureExpensePage));
    }

    private static void homePageRoute() {
        path(NettExpensesController.PATH, () -> get(NettExpensesController::renderHomePage));
    }

    @NotNull
    private static Javalin createAndConfigureServer() {
        return Javalin.create(config -> {
            config.addStaticFiles(STATIC_DIR, Location.CLASSPATH);
            config.sessionHandler(Sessions::nopersistSessionHandler);
            config.accessManager(new DefaultAccessManager());
        });
    }
}
