package com.app.customer;
import com.app.appointment.AppointmenRepository;
import com.app.appointment.AppointmentDb;
import com.app.appointment.AppointmentForm;
import com.app.appointment.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class CustomerController {
    private final AppointmentService appointmentService;
    private final AppointmenRepository appointmenRepository;
    private final CustomerRepository customerRepository;
    private final DataService customerService;
    private AppointmentDb appoinmentDb;
    private static final String CUSTOMER = "customer";

    @Autowired
    public CustomerController(AppointmenRepository appointmenRepository, CustomerRepository cust
            , DataService customerService, AppointmentService appointmentService) {
        this.appointmentService=appointmentService;
        this.appointmenRepository = appointmenRepository;
        this.customerRepository = cust;
        this.customerService = customerService;
        this.appoinmentDb = new AppointmentDb();
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

}
