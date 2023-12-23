package com.app.customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
@Service
public class DataService {

    Logger logger = Logger.getLogger(getClass().getName());
    private static final String CUSTOMER = "customer";


    private final CustomerRepository dataRepository;


    @Autowired
    public DataService (CustomerRepository dataRepository) {
        this.dataRepository = dataRepository;

    }


    public String createAccount(DataForm data, CustomerDb dataEntity) {
        boolean existingData = dataRepository.existsByName(data.getUserName());
        boolean emailExist =dataRepository.existsByEmail(data.getEmail());
       boolean n= (data.getPassword().equals(data.getConfirmPassword()));
        if (existingData) {
            logger.info("User Name already exists");
            return "User Name already exists";
        }
        else if(emailExist){
            return "Email already exists Enter another one";
        }
        else if (!n)  {

                return "Password and Confirm Password do not match";

            }

        String email = data.getEmail();


        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        Pattern pattern = Pattern.compile(emailPattern);


        Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                return "Please enter a valid email address";
            }



                dataEntity.setName(data.getUserName());
                dataEntity.setId(data.getUserId());
                dataEntity.setEmail(data.getEmail());
                dataEntity.setPass(data.getPassword());
                dataEntity.setConfPass(data.getConfirmPassword());
                dataEntity.setBirthDate(data.getBirthDate());
                dataEntity.setGender(data.getGender());

                dataEntity.setId(data.getUserId());
                dataEntity.setRole(CUSTOMER);
                logger.info("the " + dataEntity.getId());
                dataRepository.save(dataEntity);
                logger.info("Account created successfully");
                return "Account created successfully";

            }







    public void saveData(DataForm dataForm) {
        CustomerDb dataEntity = new CustomerDb();
        dataEntity.setId((dataForm.getUserId()));
        dataEntity.setName(dataForm.getUserName());


        dataRepository.save(dataEntity);
    }

    public String searchAccount(DataForm data) {
        try {

            Optional<CustomerDb> userOptional = dataRepository.findByNameAndPass(
                    data.getUserName().trim(), data.getPassword().trim()
            );

            logger.info("User found: " + userOptional.isPresent());

            if (userOptional.isPresent()) {
                CustomerDb user = userOptional.get();
                String role = user.getRole();

                if ("admin".equals(role)) {
                    return "admin";
                } else if (CUSTOMER.equals(role)) {
                    return CUSTOMER;
                } else if ("installer".equals(role)) {
                    return "installer";
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

    public CustomerDb updateCustomer(int id, CustomerDb editedCustomer) {
        Optional<CustomerDb> existingCustomerOptional = dataRepository.findById(id);

        if (existingCustomerOptional.isPresent()) {
            CustomerDb existingCustomer = existingCustomerOptional.get();
            existingCustomer.setName(editedCustomer.getName());
            existingCustomer.setEmail(editedCustomer.getEmail());
            dataRepository.save(existingCustomer);
            return  existingCustomer;
        } else {
            logger.info("Customer not found ");
        }
        return editedCustomer;
    }

    public void deleteCustomer(int id) {
        dataRepository.deleteById(id);
    }
}
