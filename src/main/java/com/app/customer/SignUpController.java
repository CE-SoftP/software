package com.app.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.logging.Logger;

@Controller
@SessionAttributes({"popupType", "popupMessage"})
public class SignUpController {
    Logger logger = Logger.getLogger(getClass().getName());
    private final CustomerService customerService;
    private static final String POPUP_TYPE = "popupType";
    private static final String POPUP_MESSAGE = "popupMessage";
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    private static final String REDIRECT_FORM = "redirect:/form";
    @Autowired
    public SignUpController(CustomerService customerService){
        this.customerService=customerService;
    }
    @GetMapping(value = "/form")
    public String showForm() {

        return "signup";
    }

    @PostMapping(value = "/saveData")
    public String signUp(@ModelAttribute CustomerForm data , Model model , SessionStatus sessionStatus) {

        CustomerDb dataEntity = new CustomerDb();
        logger.info("Are you reach ???");

        String signUpResult = customerService.createAccount(data, dataEntity);
        logger.info(signUpResult);
        if (signUpResult.equals("Account created successfully")) {
            handleSuccess(model, "Account created successfully");
            return "redirect:/home";

        }

        else if (signUpResult.equals("User Name already exists")) {
            handleError(model, "User Name already exists");
        }
        else if(signUpResult.equals("Password and Confirm Password do not match")) {
            handleError(model, "Password and Confirm Password do not match");
        }


        else if(signUpResult.equals("Email already exists Enter another one")) {
            handleError(model, "Email already exists Enter another one");
        }
        else {
            handleSuccess(model, "Please enter a valid email address");

        }
        sessionStatus.setComplete();
        return REDIRECT_FORM;
    }

    private void handleSuccess(Model model, String message) {
        model.addAttribute(POPUP_TYPE, SUCCESS);
        model.addAttribute(POPUP_MESSAGE, message);
    }

    private void handleError(Model model, String message) {
        model.addAttribute(POPUP_TYPE, ERROR);
        model.addAttribute(POPUP_MESSAGE, message);
    }
}
