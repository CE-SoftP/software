package com.app.Installation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstallationService {

    @Autowired
    private InstallationRepository installationRepository;


    public List<InstallationDB> getInstallationsByCheckedUserAndCustomerId(String checkedUser, int customerId) {
        return installationRepository.findByCHECKED_USERAndCustomerId(checkedUser, customerId);
    }

    public List<InstallationDB> getInstallationsByCheckedAdmin(String checked) {
        return installationRepository.findByCheckedAdmin(checked);
    }

    public List<InstallationDB> getInstallationsByCustomerId(int customerId) {
        return installationRepository.findByCustomerId(customerId);
    }


}