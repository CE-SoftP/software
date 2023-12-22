package com.app.installation;
import com.app.customer.CustomerDb;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@SessionAttributes({"popupType", "popupMessage"})
public class InstallationController {

    private final InstallationService installationService;

    private final InstallationRepository installationRepository;
    private final String install = "installations";
    private final String buttons = "Buttons";
    private final String loggedIn ="loggedInUser";
    private final String userRoleConst = "userRole";
    private final String Admin = "admin";
    private final String Customer = "customer";
    private final String Installer = "installer";
    Logger logger = Logger.getLogger(getClass().getName());
    @Autowired
    public InstallationController(InstallationRepository installationRepository , InstallationService installationService ) {
        this.installationRepository = installationRepository;
        this.installationService=installationService;
    }

    @GetMapping("/installation-requests")
    public String viewInstallationRequests(Model model , HttpSession session) {
        List<InstallationDB> installations = installationRepository.findAll();
        model.addAttribute(install, installations);
        session.setAttribute(buttons , "NO");
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(loggedIn);
        String userRole = loggedInUser.getRole();
        session.setAttribute(userRoleConst, userRole);
        model.addAttribute(userRoleConst, userRole );
        return "ViewInstallReqManeger";
    }

    @GetMapping("/installations/{installationId}")
    public String showInstallationDetails(@PathVariable Long installationId, Model model, HttpSession session) {
        logger.info("Inside showInstallationDetails method");
        InstallationDB installation = installationRepository.findById(Math.toIntExact(installationId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid installation id: " + installationId));
        model.addAttribute("installation", installation);
        logger.info("Installation Details: " + installation);
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(loggedIn);
        String userRole = loggedInUser.getRole();
        session.setAttribute(userRoleConst, userRole);

        String buttonsValue = (String) session.getAttribute(buttons);
        model.addAttribute(buttons, buttonsValue);

        logger.info(userRole);
        model.addAttribute(userRoleConst, userRole );
        return "installationDetails";
    }

    @GetMapping(value = "/CustomerInstallReq")
    public String showCustomerInstallReq(Model model , HttpSession session) {
        Object userRole = session.getAttribute(userRoleConst);
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(loggedIn);
        if (userRole != null) {
            if (Admin.equals(userRole.toString()) || Installer.equals(userRole.toString())) {

                List<InstallationDB> installations = installationService.getInstallationsByCheckedAdmin("NO");
                model.addAttribute(install, installations);
                session.setAttribute(buttons,"YES");

            } else if (Customer.equals(userRole.toString())) {

                List<InstallationDB> installations = installationService.getInstallationsByCheckedUserAndCustomerId("NO", loggedInUser.getId());
                model.addAttribute(install, installations);
                session.setAttribute(buttons,"YES");
            }
        }
        session.setAttribute(userRoleConst, userRole);
        model.addAttribute(userRoleConst, userRole );
        return "CustomerInstallReq";
    }

    @GetMapping(value = "/CustomerAllInstallReq")
    public String showCustomerAllInstallReq(Model model , HttpSession session) {

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(loggedIn);
        int userId = loggedInUser.getId();
        String userRole = loggedInUser.getRole();
        session.setAttribute(userRoleConst, userRole);
        model.addAttribute(userRoleConst, userRole );

        List<InstallationDB> installations = installationService.getInstallationsByCustomerId(userId);
        model.addAttribute(install, installations);
        session.setAttribute(buttons , "NO");
        return "CustomerInstallReq";
    }

    @PostMapping("/editInstall/{id}")
    public String saveChanges(@PathVariable int id, @ModelAttribute InstallationDB installation , HttpSession session) {
        InstallationDB existingInstallation = installationRepository.findById(id).orElse(null);
        Object userRole = session.getAttribute(userRoleConst);
        if (existingInstallation != null) {
            existingInstallation.setInstallDate(installation.getInstallDate());
            existingInstallation.setInstallTime(installation.getInstallTime());
            if(userRole.equals(Admin) || userRole.equals(Installer)) {
                existingInstallation.setChecked("YES");
                existingInstallation.setCHECKED_USER("NO");
            } else if (userRole.equals(Customer)) {
                existingInstallation.setChecked("NO");
                existingInstallation.setCHECKED_USER("YES");
            }
            installationRepository.save(existingInstallation);
        }

        return "redirect:/CustomerInstallReq";
    }

    @PostMapping("/approveInstall/{id}")
    public String approveInstall(@PathVariable int id, HttpSession session) {
        logger.info("In approve method");
        Object userRole = session.getAttribute(userRoleConst);

        if (userRole != null) {
            logger.info(userRole.toString());
            if (Admin.equals(userRole.toString()) || Installer.equals(userRole.toString())) {
                logger.info("ADMIN APPROVED");
                InstallationDB installation = installationRepository.findById(id).orElse(null);
                if (installation != null) {
                    installation.setChecked("YES");
                    installationRepository.save(installation);
                }
            } else if (Customer.equals(userRole.toString())) {
                logger.info("CUSTOMER APPROVED");
                InstallationDB installation = installationRepository.findById(id).orElse(null);
                if (installation != null) {
                    installation.setCHECKED_USER("YES");
                    installationRepository.save(installation);
                }
            }
        } else {
            logger.info("ERROR: User role not found");
        }

        return "redirect:/CustomerInstallReq";
    }

}