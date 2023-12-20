package com.app.customer;
import com.app.Appointment.AppointmenRepository;
import com.app.Appointment.AppointmentDb;
import com.app.Appointment.AppointmentForm;
import com.app.Appointment.AppointmentService;
import com.app.ManegerAndProduct.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SessionAttributes({"popupType", "popupMessage"})
public class CustomerController {
    Logger logger = Logger.getLogger(getClass().getName());

@Autowired
CatagroisRepositary catagroisRepositary;

@Autowired
ProductRepository productRepository;
    @Autowired
    AppointmentService appointmentService;

    private final AppointmenRepository appointmenRepository;
    private final CustomerRepository customerRepository;
    private final DataService customerService;


    private AppointmentDb appoinmentDb;



    @Autowired
    public CustomerController(AppointmenRepository appointmenRepository, CustomerRepository cust, DataService customerService) {
        this.appointmenRepository = appointmenRepository;
        this.customerRepository = cust;
        this.customerService = customerService;
       // this.appointmentService = appointmentService;
        this.appoinmentDb = new AppointmentDb();
    }

    @GetMapping(value = "/form")
    public String showForm() {

        return "signup"; // This assumes the HTML file is named "signup.html" in the "resources/templates" directory
    }
    @GetMapping(value = "/home")
    public String showForm2(Model model,DataForm dataForm) {
        List<Catagroies> productList = catagroisRepositary.findAll();
        String searchAdmin=customerService.searchAccount(dataForm);
        model.addAttribute("userRole",  searchAdmin );
        model.addAttribute("categories", productList);
        return "Home";
    }
    @GetMapping(value = "/")
    public String showForm3(HttpSession session,Model model) {
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");
        model.addAttribute("user", loggedInUser);
        return "Login";
    }

    @GetMapping(value = "/Admin")
    public String showForm4() {

        return "Admin";
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
        model.addAttribute("customer", customer);
        return "customerDetails";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {

            model.addAttribute("user", loggedInUser);

            return "profile";
        } else {

            return "redirect:/login";
        }

    }

    @PostMapping(value = "/search")
    public String LogInFunc(DataForm data, Model model, HttpSession session) {

        String logInResult = customerService.searchAccount(data);
        logger.info(logInResult);
        if(logInResult.equals("Not Found")) {
            return "Login";
        }
        else{
            CustomerDb user = customerService.findByUsername(data.getUserName());

            model.addAttribute("userId", user.getId());
            List<Catagroies> productList = catagroisRepositary.findAll();
            model.addAttribute("userRole", logInResult );
            model.addAttribute("categories", productList);
            session.setAttribute("loggedInUser", user);
            List<ProductDb> products=productRepository.findAll();
            model.addAttribute("products", products);
            return "Home";
    }
    }

    //FOR IMAAAAAAAAAAAAAAAAAAAAAGE profile
    @PostMapping(value = "/update-profile-image")
    public ResponseEntity<String> updateProfileImage(@RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok("Profile image updated successfully");
    }


    @PostMapping(value = "/saveData")
    public String signUp(@ModelAttribute DataForm data ,Model model) {

        CustomerDb dataEntity = new CustomerDb();
        logger.info("Are you reach ???");

        String signUpResult = customerService.createAccount(data, dataEntity);
        logger.info(signUpResult);
        if (signUpResult.equals("Account created successfully")) {
            model.addAttribute("popupType", "success");
            model.addAttribute("popupMessage", "Account created successfully");
            return "redirect:/home";

        }

        else if (signUpResult.equals("User Name already exists")) {
            model.addAttribute("popupType", "error");
            model.addAttribute("popupMessage", "User Name already exists");
            return "redirect:/form";

        }
        else if(signUpResult.equals("Password and Confirm Password do not match")) {

            model.addAttribute("popupType", "error");
            model.addAttribute("popupMessage", "Password and Confirm Password do not match");
            return "redirect:/form";

        }


         else if(signUpResult.equals("Email already exists Enter another one")) {

            model.addAttribute("popupType", "error");
            model.addAttribute("popupMessage", "Email already exists Enter another one");
            return "redirect:/form";

        }
            else {
            model.addAttribute("popupType", "error");
            model.addAttribute("popupMessage", "Please enter a valid email address");
            return "redirect:/form";

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
        model.addAttribute("customer", customer);
        return "edit-customer";
    }



    @PostMapping("/delete-customer/{id}")
    public String deleteCustomer(@PathVariable int id ){
        customerRepository.deleteById(id);
        return "redirect:/ViewCustomers";
    }

    @PostMapping("/edit/{id}")
    public String processEditForm(@PathVariable int id, @ModelAttribute CustomerDb editedCustomer) {
        // Update customer in the database with the edited information
        customerService.updateCustomer(id, editedCustomer);
        return "redirect:/customers/" + id; // Redirect to the customer details page
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable int id, Model model) {
        // Fetch customer by ID and add it to the model
        // This will be used to display customer details before deletion
        Optional<CustomerDb> customer = customerService.findById(id);
        model.addAttribute("customer", customer.orElse(null));
        return "delete-confirmation"; // Create a delete-confirmation.html page
    }

    @PostMapping("/delete/{id}")
    public String processDelete(@PathVariable int id) {
        // Delete customer from the database
        customerService.deleteCustomer(id);
        return "redirect:/ViewCustomers"; // Redirect to the customer list page
    }

}
