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
public class InstallationController {

    private final InstallationService installationService;

    private final InstallationRepository installationRepository;
    private static final String INSTALLATIONS = "installations";
    private static final String BUTTONS = "Buttons";
    private static final String LOGGED_IN_USER ="loggedInUser";
    private static final String USER_ROLE = "userRole";
    private static final String ADMIN = "admin";
    private static final String CUSTOMER = "customer";
    private static final String INSTALLER = "installer";
    Logger logger = Logger.getLogger(getClass().getName());
    @Autowired
    public InstallationController(InstallationRepository installationRepository , InstallationService installationService ) {
        this.installationRepository = installationRepository;
        this.installationService=installationService;
    }

    @GetMapping("/installation-requests")
    public String viewInstallationRequests(Model model , HttpSession session) {
        List<InstallationDB> installations = installationRepository.findAll();
        model.addAttribute(INSTALLATIONS, installations);
        session.setAttribute(BUTTONS, "NO");
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        String userRole = loggedInUser.getRole();
        session.setAttribute(USER_ROLE, userRole);
        model.addAttribute(USER_ROLE, userRole );
        return "ViewInstallReqManeger";
    }

    @GetMapping("/installations/{installationId}")
    public String showInstallationDetails(@PathVariable Long installationId, Model model, HttpSession session) {
        logger.info("Inside showInstallationDetails method");
        InstallationDB installation = installationRepository.findById(Math.toIntExact(installationId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid installation id: " + installationId));
        model.addAttribute("installation", installation);
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        String userRole = loggedInUser.getRole();
        session.setAttribute(USER_ROLE, userRole);

        String buttonsValue = (String) session.getAttribute(BUTTONS);
        model.addAttribute(BUTTONS, buttonsValue);

        logger.info(userRole);
        model.addAttribute(USER_ROLE, userRole );
        return "installationDetails";
    }

    @GetMapping(value = "/CustomerInstallReq")
    public String showCustomerInstallReq(Model model , HttpSession session) {
        Object userRole = session.getAttribute(USER_ROLE);
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        if (userRole != null) {
            if (ADMIN.equals(userRole.toString()) || INSTALLER.equals(userRole.toString())) {

                List<InstallationDB> installations = installationService.getInstallationsByCheckedAdmin("NO");
                model.addAttribute(INSTALLATIONS, installations);
                session.setAttribute(BUTTONS,"YES");

            } else if (CUSTOMER.equals(userRole.toString())) {

                List<InstallationDB> installations = installationService.getInstallationsByCheckedUserAndCustomerId("NO", loggedInUser.getId());
                model.addAttribute(INSTALLATIONS, installations);
                session.setAttribute(BUTTONS,"YES");
            }
        }
        session.setAttribute(USER_ROLE, userRole);
        model.addAttribute(USER_ROLE, userRole );
        return "CustomerInstallReq";
    }

    @GetMapping(value = "/CustomerAllInstallReq")
    public String showCustomerAllInstallReq(Model model , HttpSession session) {

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        int userId = loggedInUser.getId();
        String userRole = loggedInUser.getRole();
        session.setAttribute(USER_ROLE, userRole);
        model.addAttribute(USER_ROLE, userRole );

        List<InstallationDB> installations = installationService.getInstallationsByCustomerId(userId);
        model.addAttribute(INSTALLATIONS, installations);
        session.setAttribute(BUTTONS, "NO");
        return "CustomerInstallReq";
    }

    @PostMapping("/editInstall/{id}")
    public String saveChanges(@PathVariable int id, @ModelAttribute InstallationDB installation , HttpSession session) {
        InstallationDB existingInstallation = installationRepository.findById(id).orElse(null);
        Object userRole = session.getAttribute(USER_ROLE);
        if (existingInstallation != null) {
            existingInstallation.setInstallDate(installation.getInstallDate());
            existingInstallation.setInstallTime(installation.getInstallTime());
            if(userRole.equals(ADMIN) || userRole.equals(INSTALLER)) {
                existingInstallation.setChecked("YES");
                existingInstallation.setCheckedUser("NO");
            } else if (userRole.equals(CUSTOMER)) {
                existingInstallation.setChecked("NO");
                existingInstallation.setCheckedUser("YES");
            }
            installationRepository.save(existingInstallation);
        }

        return "redirect:/CustomerInstallReq";
    }

    @PostMapping("/approveInstall/{id}")
    public String approveInstall(@PathVariable int id, HttpSession session) {
        logger.info("In approve method");
        Object userRole = session.getAttribute(USER_ROLE);

        if (userRole != null) {
            logger.info(userRole.toString());
            if (ADMIN.equals(userRole.toString()) || INSTALLER.equals(userRole.toString())) {
                logger.info("ADMIN APPROVED");
                InstallationDB installation = installationRepository.findById(id).orElse(null);
                if (installation != null) {
                    installation.setChecked("YES");
                    installationRepository.save(installation);
                }
            } else if (CUSTOMER.equals(userRole.toString())) {
                logger.info("CUSTOMER APPROVED");
                InstallationDB installation = installationRepository.findById(id).orElse(null);
                if (installation != null) {
                    installation.setCheckedUser("YES");
                    installationRepository.save(installation);
                }
            }
        } else {
            logger.info("ERROR: User role not found");
        }

        return "redirect:/CustomerInstallReq";
    }

}