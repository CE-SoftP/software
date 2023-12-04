package com.app.Installation;
import com.app.customer.CustomerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class InstallationController {


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


}