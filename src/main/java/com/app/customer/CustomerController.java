package com.app.customer;
import com.app.appointment.AppointmenRepository;
import com.app.appointment.AppointmentDb;
import com.app.appointment.AppointmentForm;
import com.app.appointment.AppointmentService;
import com.app.installation.InstallationDB;
import com.app.installation.InstallationService;
import com.app.maneger_and_product.*;
import com.app.order.orderDB;
import com.app.order.orderRepository;
import com.app.order.orderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@SessionAttributes({"popupType", "popupMessage"})
public class CustomerController {
    Logger logger = Logger.getLogger(getClass().getName());
    private final CatagroisRepositary catagroisRepositary;
    private final ProductRepository productRepository;
    private final AppointmentService appointmentService;

    private final AppointmenRepository appointmenRepository;
    private final CustomerRepository customerRepository;
    private final DataService customerService;
    private final InstallationService installationService;
    private final orderService orderService;
    private AppointmentDb appoinmentDb;
    private final orderRepository orderRepository;
    private static final String LOGGED_IN_USER ="loggedInUser";
    private static final String USER_ROLE = "userRole";
    private static final String CUSTOMER = "customer";
    private static final String POPUP_TYPE = "popupType";
    private static final String POPUP_MESSAGE = "popupMessage";
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    private static final String REDIRECT_FORM = "redirect:/form";

    @Autowired
    public CustomerController(AppointmenRepository appointmenRepository, CustomerRepository cust, DataService customerService , orderRepository orderRepository
    , CatagroisRepositary catagroisRepositary ,ProductRepository productRepository , AppointmentService appointmentService
    , InstallationService installationService ,orderService orderService ) {
        this.catagroisRepositary=catagroisRepositary;
        this.productRepository=productRepository;
        this.appointmentService=appointmentService;
        this.installationService=installationService;
        this.orderService =orderService;
        this.appointmenRepository = appointmenRepository;
        this.customerRepository = cust;
        this.customerService = customerService;
        this.appoinmentDb = new AppointmentDb();
        this.orderRepository =orderRepository;

    }

    @GetMapping(value = "/form")
    public String showForm() {

        return "signup";
    }
    @GetMapping(value = "/home")
    public String showForm2(Model model,DataForm dataForm,HttpSession session) {
        List<Catagroies> productList = catagroisRepositary.findAll();
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        String userRole = loggedInUser.getRole();
        model.addAttribute(USER_ROLE, userRole);
        model.addAttribute("categories", productList);
        return "Home";
    }
    @GetMapping(value = "/")
    public String showForm3(HttpSession session,Model model) {
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        model.addAttribute("user", loggedInUser);
        return "Login";
    }



    @GetMapping(value = "/ViewCustomers")
    public String showCustomers(Model model) {
        List<CustomerDb> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        return "ViewCustomers";
    }

    @GetMapping("/customers/{customerId}")
    public String showCustomerDetails(@PathVariable Long customerId, Model model) {
        CustomerDb customer = customerRepository.findById(Math.toIntExact(customerId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer id: " + customerId));
        model.addAttribute(CUSTOMER, customer);
        return "customerDetails";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);

        if (loggedInUser != null) {

            model.addAttribute("user", loggedInUser);
            model.addAttribute(CUSTOMER, loggedInUser);

            CustomerDb loggedIn = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
            String userRole = loggedIn.getRole();
            session.setAttribute(USER_ROLE, userRole);
            logger.info(userRole);
            model.addAttribute(USER_ROLE, userRole );

            return "profile";
        } else {

            return "redirect:/login";
        }

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


    @PostMapping(value = "/saveData")
    public String signUp(@ModelAttribute DataForm data ,Model model) {

        CustomerDb dataEntity = new CustomerDb();
        logger.info("Are you reach ???");

        String signUpResult = customerService.createAccount(data, dataEntity);
        logger.info(signUpResult);
        if (signUpResult.equals("Account created successfully")) {
            model.addAttribute(POPUP_TYPE, SUCCESS);
            model.addAttribute(POPUP_MESSAGE, "Account created successfully");
            return "redirect:/home";

        }

        else if (signUpResult.equals("User Name already exists")) {
            model.addAttribute(POPUP_TYPE, ERROR);
            model.addAttribute(POPUP_MESSAGE, "User Name already exists");
            return REDIRECT_FORM;

        }
        else if(signUpResult.equals("Password and Confirm Password do not match")) {

            model.addAttribute(POPUP_TYPE, ERROR);
            model.addAttribute(POPUP_MESSAGE, "Password and Confirm Password do not match");
            return REDIRECT_FORM;

        }


         else if(signUpResult.equals("Email already exists Enter another one")) {

            model.addAttribute(POPUP_TYPE, ERROR);
            model.addAttribute(POPUP_MESSAGE, "Email already exists Enter another one");
            return REDIRECT_FORM;

        }
            else {
            model.addAttribute(POPUP_TYPE, SUCCESS);
            model.addAttribute(POPUP_MESSAGE, "Please enter a valid email address");
            return REDIRECT_FORM;

        }


    }



    @GetMapping ("/manager")
    public String showManager() {
        return "manager";
    }




    @PostMapping(value = "/saveAppointment")
    public String sendRequest(@ModelAttribute AppointmentForm appoitmentForm){
      boolean sendResult=appointmentService.creatRequast(appoitmentForm,appoinmentDb);
        appointmenRepository.save(appoinmentDb);
        if(sendResult){
        return "Home";}
        else{

        return "signup";
    }}

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Optional<CustomerDb> customer = customerService.findById(id);
        model.addAttribute(CUSTOMER, customer);
        return "edit-customer";
    }



    @PostMapping("/delete-customer/{id}")
    public String deleteCustomer(@PathVariable int id ){
        customerRepository.deleteById(id);
        return "redirect:/ViewCustomers";
    }

    @PostMapping("/edit/{id}")
    public String processEditForm(@PathVariable int id, @ModelAttribute CustomerDb editedCustomer ) {
        customerService.updateCustomer(id, editedCustomer);
        return "redirect:/customers/" + id;
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable int id, Model model) {
        Optional<CustomerDb> customer = customerService.findById(id);
        model.addAttribute(CUSTOMER, customer.orElse(null));
        return "delete-confirmation";
    }

    @PostMapping("/delete/{id}")
    public String processDelete(@PathVariable int id) {

        customerService.deleteCustomer(id);
        return "redirect:/ViewCustomers";
    }

    @PostMapping("/editProfile/{id}")
    public String processEditProfileForm(@PathVariable int id, @ModelAttribute CustomerDb editedCustomer , Model model , SessionStatus sessionStatus) {
        CustomerDb updatedCustomer =  customerService.updateCustomer(id, editedCustomer);
        model.addAttribute(CUSTOMER, updatedCustomer);
        logger.info("Customer updated successfully");
        sessionStatus.setComplete();
        return "profile";
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
        List<orderDB> orders = orderService.getOrderByPopUpUser("NO", user.getId());

        String message = "";
        StringBuilder messageBuilder = new StringBuilder(message);
        if (!installations.isEmpty()) {
            messageBuilder.append("You have ").append(installations.size()).append(" Requests to check \n");
        }
        for (orderDB order : orders) {
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


}
