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
        setUserRoleAttributes(session, model);
        return "ViewInstallReqManeger";
    }

    @GetMapping("/installations/{installationId}")
    public String showInstallationDetails(@PathVariable Long installationId, Model model, HttpSession session) {
        logger.info("Inside showInstallationDetails method");
        InstallationDB installation = installationRepository.findById(Math.toIntExact(installationId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid installation id: " + installationId));
        model.addAttribute("installation", installation);
        setUserRoleAttributes(session, model);
        String buttonsValue = (String) session.getAttribute(BUTTONS);
        model.addAttribute(BUTTONS, buttonsValue);
        return "installationDetails";
    }

    @GetMapping(value = "/CustomerInstallReq")
    public String showCustomerInstallReq(Model model , HttpSession session) {
        Object userRole = session.getAttribute(USER_ROLE);
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        if (userRole != null) {
            handleInstallationsByUserRole(model, session, loggedInUser, userRole);
        }
        setUserRoleAttributes(session, model);
        return "CustomerInstallReq";
    }

    @GetMapping(value = "/CustomerAllInstallReq")
    public String showCustomerAllInstallReq(Model model , HttpSession session) {

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        int userId = loggedInUser.getId();
        setInstallationsByCustomerId(model, session, userId);
        setUserRoleAttributes(session, model);
        return "CustomerInstallReq";
    }

    @PostMapping("/editInstall/{id}")
    public String saveChanges(@PathVariable int id, @ModelAttribute InstallationDB installation , HttpSession session) {
        InstallationDB existingInstallation = installationRepository.findById(id).orElse(null);
        Object userRole = session.getAttribute(USER_ROLE);
        if (existingInstallation != null) {
            updateInstallationAttributes(installation, existingInstallation, userRole);
            installationRepository.save(existingInstallation);
        }

        return "redirect:/CustomerInstallReq";
    }

    @PostMapping("/approveInstall/{id}")
    public String approveInstall(@PathVariable int id, HttpSession session) {
        logger.info("In approve method");
        Object userRole = session.getAttribute(USER_ROLE);

        if (userRole != null) {
            handleApprovalByUserRole(id, userRole);
        } else {
            logger.info("ERROR: User role not found");
        }

        return "redirect:/CustomerInstallReq";
    }

    private void setUserRoleAttributes(HttpSession session, Model model) {
        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        String userRole = loggedInUser.getRole();
        session.setAttribute(USER_ROLE, userRole);
        model.addAttribute(USER_ROLE, userRole);
    }

    private void handleInstallationsByUserRole(Model model, HttpSession session, CustomerDb loggedInUser, Object userRole) {
        if (ADMIN.equals(userRole.toString()) || INSTALLER.equals(userRole.toString())) {
            List<InstallationDB> installations = installationService.getInstallationsByCheckedAdmin("NO");
            model.addAttribute(INSTALLATIONS, installations);
            session.setAttribute(BUTTONS, "YES");
        } else if (CUSTOMER.equals(userRole.toString())) {
            List<InstallationDB> installations = installationService.getInstallationsByCheckedUserAndCustomerId("NO", loggedInUser.getId());
            model.addAttribute(INSTALLATIONS, installations);
            session.setAttribute(BUTTONS, "YES");
        }
    }

    private void setInstallationsByCustomerId(Model model, HttpSession session, int userId) {
        List<InstallationDB> installations = installationService.getInstallationsByCustomerId(userId);
        model.addAttribute(INSTALLATIONS, installations);
        session.setAttribute(BUTTONS, "NO");
    }

    private void updateInstallationAttributes(InstallationDB source, InstallationDB target, Object userRole) {
        target.setInstallDate(source.getInstallDate());
        target.setInstallTime(source.getInstallTime());

        if (userRole.equals(ADMIN) || userRole.equals(INSTALLER)) {
            target.setChecked("YES");
            target.setCheckedUser("NO");
        } else if (userRole.equals(CUSTOMER)) {
            target.setChecked("NO");
            target.setCheckedUser("YES");
        }
    }

    private void handleApprovalByUserRole(int id, Object userRole) {
        if (ADMIN.equals(userRole.toString()) || INSTALLER.equals(userRole.toString())) {
            handleAdminInstallerApproval(id);
        } else if (CUSTOMER.equals(userRole.toString())) {
            handleCustomerApproval(id);
        }
    }

    private void handleAdminInstallerApproval(int id) {
        logger.info("ADMIN APPROVED");
        InstallationDB installation = installationRepository.findById(id).orElse(null);
        if (installation != null) {
            installation.setChecked("YES");
            installationRepository.save(installation);
        }
    }

    private void handleCustomerApproval(int id) {
        logger.info("CUSTOMER APPROVED");
        InstallationDB installation = installationRepository.findById(id).orElse(null);
        if (installation != null) {
            installation.setCheckedUser("YES");
            installationRepository.save(installation);
        }
    }


}