package com.app.Installation;
import com.app.customer.CustomerDb;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes({"popupType", "popupMessage"})
public class InstallationController {

    @Autowired
    private InstallationService installationService;

    private final InstallationRepository installationRepository;

    @Autowired
    public InstallationController(InstallationRepository installationRepository) {
        this.installationRepository = installationRepository;
    }

    @GetMapping("/installation-requests")
    public String viewInstallationRequests(Model model , HttpSession session) {
        List<InstallationDB> installations = installationRepository.findAll();
        model.addAttribute("installations", installations);
        session.setAttribute("Buttons" , "NO");
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");
        String userRole = loggedInUser.getRole();
        session.setAttribute("userRole", userRole);
        model.addAttribute("userRole", userRole );
        return "ViewInstallReqManeger"; // Thymeleaf template name
    }

    @GetMapping("/installations/{installationId}")
    public String showInstallationDetails(@PathVariable Long installationId, Model model, HttpSession session) {
        System.out.println("Inside showInstallationDetails method");
        InstallationDB installation = installationRepository.findById(Math.toIntExact(installationId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid installation id: " + installationId));
        model.addAttribute("installation", installation);
        System.out.println("Installation Details: " + installation);
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");
        String userRole = loggedInUser.getRole();
        session.setAttribute("userRole", userRole);

        String buttonsValue = (String) session.getAttribute("Buttons");
        model.addAttribute("Buttons", buttonsValue);

        System.out.println(userRole);
        model.addAttribute("userRole", userRole );
        return "installationDetails";
    }

    @GetMapping(value = "/CustomerInstallReq")
    public String showCustomerInstallReq(Model model , HttpSession session) {
        Object userRole = session.getAttribute("userRole");
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");
        if (userRole != null) {
            if ("admin".equals(userRole.toString()) | "installer".equals(userRole.toString())) {
                int userId = loggedInUser.getId();

                List<InstallationDB> installations = installationService.getInstallationsByCheckedAdmin("NO");
                model.addAttribute("installations", installations);
                session.setAttribute("Buttons","YES");

            } else if ("customer".equals(userRole.toString())) {
                int userId = loggedInUser.getId();

                List<InstallationDB> installations = installationService.getInstallationsByCheckedUserAndCustomerId("NO", userId);
                model.addAttribute("installations", installations);
                session.setAttribute("Buttons","YES");
            }
        }
        String userRole1 = loggedInUser.getRole();
        session.setAttribute("userRole", userRole);
        model.addAttribute("userRole", userRole );
        return "CustomerInstallReq";
    }

    @GetMapping(value = "/CustomerAllInstallReq")
    public String showCustomerAllInstallReq(Model model , HttpSession session) {

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");
        int userId = loggedInUser.getId();
        String userRole = loggedInUser.getRole();
        session.setAttribute("userRole", userRole);
        model.addAttribute("userRole", userRole );

        List<InstallationDB> installations = installationService.getInstallationsByCustomerId(userId);
        model.addAttribute("installations", installations);
        session.setAttribute("Buttons" , "NO");
        return "CustomerInstallReq";
    }

    @PostMapping("/editInstall/{id}")
    public String saveChanges(@PathVariable int id, @ModelAttribute InstallationDB installation , HttpSession session) {
        InstallationDB existingInstallation = installationRepository.findById(id).orElse(null);
        Object userRole = session.getAttribute("userRole");
        if (existingInstallation != null) {
            existingInstallation.setInstallDate(installation.getInstallDate());
            existingInstallation.setInstallTime(installation.getInstallTime());
            if(userRole.equals("admin") | userRole.equals("installer")) {
                existingInstallation.setChecked("YES");
                existingInstallation.setCHECKED_USER("NO");
            } else if (userRole.equals("customer")) {
                existingInstallation.setChecked("NO");
                existingInstallation.setCHECKED_USER("YES");
            }
            installationRepository.save(existingInstallation);
        }

        return "redirect:/CustomerInstallReq";
    }

    @PostMapping("/approveInstall/{id}")
    public String approveInstall(@PathVariable int id, HttpSession session) {
        System.out.println("In approve method");
        Object userRole = session.getAttribute("userRole");

        if (userRole != null) {
            System.out.println(userRole.toString());
            if ("admin".equals(userRole.toString()) || "installer".equals(userRole.toString())) {
                System.out.println("ADMIN APPROVED");
                InstallationDB installation = installationRepository.findById(id).orElse(null);
                if (installation != null) {
                    installation.setChecked("YES");
                    installationRepository.save(installation);
                }
            } else if ("customer".equals(userRole.toString())) {
                System.out.println("CUSTOMER APPROVED");
                InstallationDB installation = installationRepository.findById(id).orElse(null);
                if (installation != null) {
                    installation.setCHECKED_USER("YES");
                    installationRepository.save(installation);
                }
            }
        } else {
            System.out.println("ERROR: User role not found");
        }

        return "redirect:/CustomerInstallReq";
    }

}