package com.app.customer;




import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
@Service
public class DataService {

    Logger logger = Logger.getLogger(getClass().getName());

    public void displayPopup(String message) {
        logger.info(String.format("Displaying popup: %s", message));

    }


    private final CustomerRepository dataRepository;


    @Autowired
    public DataService (CustomerRepository dataRepository) {
        this.dataRepository = dataRepository;

    }


    public String createAccount(DataForm data, CustomerDb dataEntity) {
     boolean existingData=dataRepository.existsByName(data.getUserName());
logger.info(data.getUserName());
        if (existingData) {

            logger.info("User ID already exists");
            return "User ID already exists";
        }


        else {
          if ( !(data.getPassword().equals(data.getConfirmPassword()))) {


              return "Password and Confirm Password do not match.";

          }

            dataEntity.setName(data.getUserName());
            dataEntity.setId(data.getUserId());
            dataEntity.setEmail(data.getEmail());
            dataEntity.setPass(data.getPassword());
            dataEntity.setConfPass(data.getConfirmPassword());
            dataEntity.setBirthDate(data.getBirthDate());
            dataEntity.setGender(data.getGender());
            data.setUserId(3211);
            dataEntity.setId(data.getUserId());
            logger.info("the "+dataEntity.getId());
            dataRepository.save(dataEntity);
          logger.info("Account created successfully");
            return "Account created successfully";

        }


    }




    public void saveData(DataForm dataForm) {
        CustomerDb dataEntity = new CustomerDb();
        // Map data from DataForm to DataEntity (assuming DataEntity is your JPA entity)
        dataEntity.setId((dataForm.getUserId()));
        dataEntity.setName(dataForm.getUserName());
        // Set other fields accordingly

        dataRepository.save(dataEntity);
    }

    public String searchAccount(DataForm data) {
        try {
           logger.info(data.getUserName());
            logger.info("Searching for user: " + data.getUserName());
            logger.info("Searching for pass: " + data.getPassword());

            Optional<CustomerDb> userOptional = dataRepository.findByNameAndPass(
                    data.getUserName().trim(), data.getPassword().trim()
            );

            logger.info("User found: " + userOptional.isPresent());

            if (userOptional.isPresent()) {
                CustomerDb user = userOptional.get();
                String role = user.getRole();

                if ("admin".equals(role)) {
                    return "Admin";
                } else if ("customer".equals(role)) {
                    return "Customer";
                } else if ("installer".equals(role)) {
                    return "Installer";
                }
            }

            return "Not Found";
        } catch (Exception e) {
            // Handle the exception, log it, or return an appropriate error message
            return "Error";
        }
    }
    public CustomerDb findByUsername(String username) {
        return dataRepository.findByName(username);
    }
    public List<CustomerDb> getAllCustomers() {
        return dataRepository.findAll();
    }

    public Optional<CustomerDb> findById(int id) {
        return dataRepository.findById(id);
    }

    public CustomerDb saveCustomer(CustomerDb customer) {
        return dataRepository.save(customer);
    }

    public void updateCustomer(int id, CustomerDb editedCustomer) {
        Optional<CustomerDb> existingCustomerOptional = dataRepository.findById(id);

        if (existingCustomerOptional.isPresent()) {
            CustomerDb existingCustomer = existingCustomerOptional.get();
            existingCustomer.setName(editedCustomer.getName());
            existingCustomer.setEmail(editedCustomer.getEmail());
            // Update other fields as needed
            dataRepository.save(existingCustomer);
        } else {
            // Handle the case where the customer with the given ID is not found
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }

    public void deleteCustomer(int id) {
        dataRepository.deleteById(id);
    }
//public UserResult searchAccountForProfile(DataForm data) {
//    try {
//        logger.info("Searching for user: " + data.getUserName());
//        logger.info("Searching for pass: " + data.getPassword());
//
//        Optional<CustomerDb> userOptional = dataRepository.findByNameAndPass(
//                data.getUserName().trim(), data.getPassword().trim()
//        );
//
//        logger.info("User found: " + userOptional.isPresent());
//
//        if (userOptional.isPresent()) {
//            CustomerDb user = userOptional.get();
//            String role = user.getRole();
//
//            if ("admin".equals(role)) {
//                return new UserResult("Admin", user);
//            } else if ("customer".equals(role)) {
//                return new UserResult("Customer", user);
//            } else if ("installer".equals(role)) {
//                return new UserResult("Installer", user);
//            }
//        }
//
//        return new UserResult("Not Found", null);
//    } catch (Exception e) {
//        // Handle the exception, log it, or return an appropriate error message
//        return new UserResult("Error", null);
//    }
//}
//
//    public static class UserResult {
//        private final String role;
//        private final CustomerDb user;
//
//        public UserResult(String role, CustomerDb user) {
//            this.role = role;
//            this.user = user;
//        }
//
//        public String getRole() {
//            return role;
//        }
//
//        public CustomerDb getUser() {
//            return user;
//        }
//    }

}
