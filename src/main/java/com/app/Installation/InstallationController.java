package com.app.Installation;
import com.app.customer.CustomerDb;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class InstallationController {

    @Autowired
    private InstallationService installationService;

    private final InstallationRepository installationRepository;

    @Autowired
    public InstallationController(InstallationRepository installationRepository) {
        this.installationRepository = installationRepository;
    }

    @GetMapping("/installation-requests")
    public String viewInstallationRequests(Model model) {
        List<InstallationDB> installations = installationRepository.findAll();
        model.addAttribute("installations", installations);
        return "ViewInstallReqManeger"; // Thymeleaf template name
    }

    @GetMapping("/installations/{installationId}")
    public String showInstallationDetails(@PathVariable Long installationId, Model model) {
        InstallationDB installation = installationRepository.findById(Math.toIntExact(installationId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid installation id: " + installationId));
        model.addAttribute("installation", installation);
        return "installationDetails";
    }

    @GetMapping(value = "/CustomerInstallReq")
    public String showCustomerInstallReq(Model model , HttpSession session) {
        //List<InstallationDB> customers = installationRepository.findByCHECKED_USER("NO");
//        List<InstallationDB> installations = installationService.getInstallationsByCheckedUser("NO");
//        model.addAttribute("installations", installations);

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");
        int userId = loggedInUser.getId();

        List<InstallationDB> installations = installationService.getInstallationsByCheckedUserAndCustomerId("NO", userId);
        model.addAttribute("installations", installations);
        return "CustomerInstallReq";
    }

    @GetMapping(value = "/CustomerAllInstallReq")
    public String showCustomerAllInstallReq(Model model , HttpSession session) {
        //List<InstallationDB> customers = installationRepository.findByCHECKED_USER("NO");
//        List<InstallationDB> installations = installationService.getInstallationsByCheckedUser("NO");
//        model.addAttribute("installations", installations);

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");
        int userId = loggedInUser.getId();

        List<InstallationDB> installations = installationService.getInstallationsByCustomerId(userId);
        model.addAttribute("installations", installations);
        return "CustomerInstallReq";
    }

//    @GetMapping("/editInstall/{id}")
//    public String showEditForm(@PathVariable int id, Model model) {
//        Optional<InstallationDB> installation = installationService.findById(id);
//        model.addAttribute("customer", customer);
//        return "edit-customer";
//    }
//
//
//    @PostMapping("/edit/{id}")
//    public String processEditForm(@PathVariable int id, @ModelAttribute CustomerDb editedCustomer) {
//        // Update customer in the database with the edited information
//        customerService.updateCustomer(id, editedCustomer);
//        return "redirect:/customers/" + id; // Redirect to the customer details page
//    }

    @PostMapping("/editInstall/{id}")
    public String saveChanges(@PathVariable int id, @ModelAttribute InstallationDB installation, Model model) {
        // Update the database with the new values from the form
        // Use the id to identify the specific installation record

        // Example code:
        InstallationDB existingInstallation = installationRepository.findById(id).orElse(null);
        if (existingInstallation != null) {
            existingInstallation.setInstallDate(installation.getInstallDate());
            existingInstallation.setAvailability(installation.getAvailability());
            installationRepository.save(existingInstallation);
        }

        // Redirect to a success page or wherever is appropriate
        return "redirect:/success";
    }

}