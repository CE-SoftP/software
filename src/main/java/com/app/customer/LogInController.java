package com.app.customer;
import com.app.installation.InstallationDB;
import com.app.installation.InstallationService;
import com.app.maneger_and_product.Catagroies;
import com.app.maneger_and_product.CatagroisRepositary;
import com.app.maneger_and_product.ProductRepository;
import com.app.order.OrderDatabase;
import com.app.order.OrderRepository;
import com.app.order.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Controller
@SessionAttributes({"popupType", "popupMessage"})
public class LogInController {
    Logger logger = Logger.getLogger(getClass().getName());
    private final CatagroisRepositary catagroisRepositary;
    private final ProductRepository productRepository;
    private final DataService customerService;
    private final InstallationService installationService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private static final String LOGGED_IN_USER ="loggedInUser";
    private static final String USER_ROLE = "userRole";
    private static final String CUSTOMER = "customer";
    private static final String POPUP_TYPE = "popupType";
    private static final String POPUP_MESSAGE = "popupMessage";
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    @Autowired
    public LogInController(DataService customerService , OrderRepository orderRepository
            , CatagroisRepositary catagroisRepositary , ProductRepository productRepository
            , InstallationService installationService , OrderService orderService ) {
        this.catagroisRepositary=catagroisRepositary;
        this.productRepository=productRepository;
        this.installationService=installationService;
        this.orderService =orderService;
        this.customerService = customerService;
        this.orderRepository =orderRepository;

    }
    @GetMapping(value = "/")
    public String showLogInForm(HttpSession session, Model model) {
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        model.addAttribute("user", loggedInUser);
        return "Login";
    }

    @PostMapping(value = "/search")
    public String logInFunc(DataForm data, Model model, HttpSession session) {
        String logInResult = customerService.searchAccount(data);
        logger.info(logInResult);
        if(logInResult.equals("Not Found")) {
            handleNotFound(model);
            return "Login";
        }
        else{
            handleLoggedInUser(data, model, session, logInResult);
            return "Home";
        }
    }

    private void handleNotFound(Model model) {
        model.addAttribute(POPUP_TYPE, ERROR);
        model.addAttribute(POPUP_MESSAGE, "You have entered wrong value");
        logger.info("NOT FOUND");
    }

    private void handleLoggedInUser(DataForm data, Model model, HttpSession session, String logInResult) {
        CustomerDb user = customerService.findByUsername(data.getUserName());

        model.addAttribute("userId", user.getId());
        model.addAttribute(USER_ROLE, logInResult);
        model.addAttribute("categories", catagroisRepositary.findAll());
        session.setAttribute(LOGGED_IN_USER, user);
        model.addAttribute("products", productRepository.findAll());

        handleUserRoleSpecificLogic(user, model, session);
    }
    private void handleUserRoleSpecificLogic(CustomerDb user, Model model, HttpSession session) {
        String userRole = user.getRole();
        session.setAttribute(USER_ROLE, userRole);

        if (userRole.equals(CUSTOMER)) {
            handleCustomerLogic(user, model);
        } else if (userRole.equals("admin") || userRole.equals("installer")) {
            handleAdminInstallerLogic(model);
        }
    }

    private void handleCustomerLogic(CustomerDb user, Model model) {
        List<InstallationDB> installations = installationService.getInstallationsByCheckedUserAndCustomerId("NO", user.getId());
        List<OrderDatabase> orders = orderService.getOrderByPopUpUser("NO", user.getId());

        String message = "";
        StringBuilder messageBuilder = new StringBuilder(message);
        if (!installations.isEmpty()) {
            messageBuilder.append("You have ").append(installations.size()).append(" Requests to check \n");
        }
        for (OrderDatabase order : orders) {
            messageBuilder.append("Your order with id : ")
                    .append(order.getId())
                    .append(" have been confirmed \n");

            order.setPopUpUser("YES");
            orderRepository.save(order);
        }
        logger.info(message);

        if (!installations.isEmpty() || !orders.isEmpty()) {
            model.addAttribute(POPUP_TYPE, SUCCESS);
            model.addAttribute(POPUP_MESSAGE, messageBuilder);
        }
    }
    private void handleAdminInstallerLogic(Model model) {
        List<InstallationDB> installations = installationService.getInstallationsByCheckedAdmin("NO");
        if (!installations.isEmpty()) {
            model.addAttribute(POPUP_TYPE, SUCCESS);
            model.addAttribute(POPUP_MESSAGE, "You have " + installations.size() + " Requests to check");
        }
    }


    @GetMapping(value = "/home")
    public String showHomeForm(Model model,DataForm dataForm,HttpSession session , SessionStatus sessionStatus) {
        List<Catagroies> productList = catagroisRepositary.findAll();
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        String userRole = loggedInUser.getRole();
        model.addAttribute(USER_ROLE, userRole);
        model.addAttribute("categories", productList);
        sessionStatus.setComplete();
        return "Home";
    }
}
