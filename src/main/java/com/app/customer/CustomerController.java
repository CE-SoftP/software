package com.app.customer;
import com.app.appointment.AppointmenRepository;
import com.app.appointment.AppointmentDb;
import com.app.appointment.AppointmentForm;
import com.app.appointment.AppointmentService;
import com.app.installation.InstallationDB;
import com.app.installation.InstallationService;
import com.app.manegerAndProduct.*;
import com.app.order.orderDB;
import com.app.order.orderRepository;
import com.app.order.orderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private final String loggedInConst ="loggedInUser";
    private final String userRoleConst = "userRole";
    private final String customerConst = "customer";
    private final String popUpTypeConst = "popupType";
    private final String popUpMessageConst = "popupMessage";
    private final String errorConst = "error";
    private final String successConst = "success";
    private final String redirectForm = "redirect:/form";

    @Autowired
    public CustomerController(AppointmenRepository appointmenRepository, CustomerRepository cust, DataService customerService , orderRepository OrderRepository
    , CatagroisRepositary catagroisRepositary ,ProductRepository productRepository , AppointmentService appointmentService
    , InstallationService installationService ,orderService OrderService ) {
        this.catagroisRepositary=catagroisRepositary;
        this.productRepository=productRepository;
        this.appointmentService=appointmentService;
        this.installationService=installationService;
        this.orderService =OrderService;
        this.appointmenRepository = appointmenRepository;
        this.customerRepository = cust;
        this.customerService = customerService;
        this.appoinmentDb = new AppointmentDb();
        this.orderRepository =OrderRepository;

    }

    @GetMapping(value = "/form")
    public String showForm() {

        return "signup";
    }
    @GetMapping(value = "/home")
    public String showForm2(Model model,DataForm dataForm,HttpSession session) {
        List<Catagroies> productList = catagroisRepositary.findAll();
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(loggedInConst);
        String userRole = loggedInUser.getRole();
        model.addAttribute(userRoleConst, userRole);
        model.addAttribute("categories", productList);
        return "Home";
    }
    @GetMapping(value = "/")
    public String showForm3(HttpSession session,Model model) {
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(loggedInConst);
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
        model.addAttribute(customerConst, customer);
        return "customerDetails";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(loggedInConst);

        if (loggedInUser != null) {

            model.addAttribute("user", loggedInUser);
            model.addAttribute(customerConst, loggedInUser);

            CustomerDb loggedIn = (CustomerDb) session.getAttribute(loggedInConst);
            String userRole = loggedIn.getRole();
            session.setAttribute(userRoleConst, userRole);
            logger.info(userRole);
            model.addAttribute(userRoleConst, userRole );

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
            model.addAttribute(popUpTypeConst, successConst);
            model.addAttribute(popUpMessageConst, "Account created successfully");
            return "redirect:/home";

        }

        else if (signUpResult.equals("User Name already exists")) {
            model.addAttribute(popUpTypeConst, errorConst);
            model.addAttribute(popUpMessageConst, "User Name already exists");
            return redirectForm;

        }
        else if(signUpResult.equals("Password and Confirm Password do not match")) {

            model.addAttribute(popUpTypeConst, errorConst);
            model.addAttribute(popUpMessageConst, "Password and Confirm Password do not match");
            return redirectForm;

        }


         else if(signUpResult.equals("Email already exists Enter another one")) {

            model.addAttribute(popUpTypeConst, errorConst);
            model.addAttribute(popUpMessageConst, "Email already exists Enter another one");
            return redirectForm;

        }
            else {
            model.addAttribute(popUpTypeConst, successConst);
            model.addAttribute(popUpMessageConst, "Please enter a valid email address");
            return redirectForm;

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
        model.addAttribute(customerConst, customer);
        return "edit-customer";
    }



    @PostMapping("/delete-customer/{id}")
    public String deleteCustomer(@PathVariable int id ){
        customerRepository.deleteById(id);
        return "redirect:/ViewCustomers";
    }

    @PostMapping("/edit/{id}")
    public String processEditForm(@PathVariable int id, @ModelAttribute CustomerDb editedCustomer) {
        customerService.updateCustomer(id, editedCustomer);
        return "redirect:/customers/" + id;
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable int id, Model model) {
        Optional<CustomerDb> customer = customerService.findById(id);
        model.addAttribute(customerConst, customer.orElse(null));
        return "delete-confirmation";
    }

    @PostMapping("/delete/{id}")
    public String processDelete(@PathVariable int id) {

        customerService.deleteCustomer(id);
        return "redirect:/ViewCustomers";
    }

    @PostMapping("/editProfile/{id}")
    public String processEditProfileForm(@PathVariable int id, @ModelAttribute CustomerDb editedCustomer , Model model) {
        CustomerDb updatedCustomer =  customerService.updateCustomer(id, editedCustomer);
        model.addAttribute(customerConst, updatedCustomer);
        logger.info("Customer updated successfully");
        return "profile";
    }

    private void handleNotFound(Model model) {
        model.addAttribute(popUpTypeConst, errorConst);
        model.addAttribute(popUpMessageConst, "You have entered wrong value");
        logger.info("NOT FOUND");
    }

    private void handleLoggedInUser(DataForm data, Model model, HttpSession session, String logInResult) {
        CustomerDb user = customerService.findByUsername(data.getUserName());
        model.addAttribute("userId", user.getId());
        model.addAttribute(userRoleConst, logInResult);
        model.addAttribute("categories", catagroisRepositary.findAll());
        session.setAttribute(loggedInConst, user);
        model.addAttribute("products", productRepository.findAll());

        handleUserRoleSpecificLogic(user, model, session);
    }
    private void handleUserRoleSpecificLogic(CustomerDb user, Model model, HttpSession session) {
        String userRole = user.getRole();
        session.setAttribute(userRoleConst, userRole);

        if (userRole.equals(customerConst)) {
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
        System.out.println(message);

        if (!installations.isEmpty() || !orders.isEmpty()) {
            model.addAttribute(popUpTypeConst, successConst);
            model.addAttribute(popUpMessageConst, messageBuilder);
        }
    }
    private void handleAdminInstallerLogic(Model model) {
        List<InstallationDB> installations = installationService.getInstallationsByCheckedAdmin("NO");
        if (!installations.isEmpty()) {
            model.addAttribute(popUpTypeConst, successConst);
            model.addAttribute(popUpMessageConst, "You have " + installations.size() + " Requests to check");
        }
    }


}
