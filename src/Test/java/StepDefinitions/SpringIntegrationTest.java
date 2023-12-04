//package StepDefinitions;
//
//
//import com.app.customer.*;
//import io.cucumber.spring.CucumberContextConfiguration;
//import org.junit.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootContextLoader;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.ContextConfiguration;
//
//@SpringBootTest
//@CucumberContextConfiguration
//@ContextConfiguration(classes = {AppointmenRepository.class, AppointmentService.class, CustomerRepository.class, CustomerController.class})
//@ComponentScan(basePackages = "StepDefinitions")
//public class SpringIntegrationTest {
//    @Autowired
//    AppointmenRepository appointmenRepository;
//    @Autowired
//    AppointmentService appointmentService;
//    @Autowired
//    CustomerRepository customerRepository;
//    @Autowired
//    CustomerController customerController;
//    @Autowired
//    DataService dataService;
//    @Before
//    public void setUp() {
//        System.out.println("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
//    }
//}
//
//
//
