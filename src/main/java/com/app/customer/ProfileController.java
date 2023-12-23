package com.app.customer;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.logging.Logger;

@Controller
@SessionAttributes({"popupType", "popupMessage"})
public class ProfileController {
    Logger logger = Logger.getLogger(getClass().getName());
    private final DataService customerService;
    private static final String LOGGED_IN_USER ="loggedInUser";
    private static final String USER_ROLE = "userRole";
    private static final String CUSTOMER = "customer";
    private static final String POPUP_TYPE = "popupType";
    private static final String POPUP_MESSAGE = "popupMessage";
    private static final String SUCCESS = "success";

    @Autowired
    public ProfileController(DataService customerService){
        this.customerService=customerService;
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

    @PostMapping("/editProfile/{id}")
    public String processEditProfileForm(@PathVariable int id, @ModelAttribute CustomerDb editedCustomer , Model model , SessionStatus sessionStatus) {
        CustomerDb updatedCustomer =  customerService.updateCustomer(id, editedCustomer);
        model.addAttribute(CUSTOMER, updatedCustomer);
        logger.info("Customer updated successfully");
        model.addAttribute(POPUP_TYPE, SUCCESS);
        model.addAttribute(POPUP_MESSAGE, "Customer updated successfully");
        sessionStatus.setComplete();
        return "profile";
    }
}
