package com.app.order;
import com.app.customer.CustomerDb;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class OrderController {
    Logger logger = Logger.getLogger(getClass().getName());
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private static final String LOGGED_IN_USER ="loggedInUser";
    private static final String USER_ROLE = "userRole";


    @Autowired
    public OrderController(OrderRepository orderRepository , OrderService orderService){
        this.orderRepository =orderRepository;
        this.orderService=orderService;
    }


    @GetMapping(value = "/CustomerOrderHistory")
    public String showCustomerOrderHistory(Model model , HttpSession session) {

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        int userId = loggedInUser.getId();

        if("admin".equals(loggedInUser.getRole()) ||"installer".equals(loggedInUser.getRole())){
            List<OrderDatabase> orders = orderService.getOrderByConfAdmin("NO");
            model.addAttribute("orders", orders);
        }
        else{
            List<OrderDatabase> orders = orderService.getOrderByCustomerId(userId);
            model.addAttribute("orders", orders);
        }

        CustomerDb loggedIn = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        String userRole = loggedIn.getRole();
        session.setAttribute(USER_ROLE, userRole);
        logger.info(userRole);
        model.addAttribute(USER_ROLE, userRole );
        return "ViewOrder";
    }

    @GetMapping("/orders/{orderId}")
    public String showInstallationDetails(@PathVariable Long orderId, Model model, HttpSession session) {
        logger.info("Inside orderDetails method");
        OrderDatabase order = orderRepository.findById(Math.toIntExact(orderId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid order id: " + orderId));
        model.addAttribute("order", order);

        int orderID= order.getId();
        List<OrderProductDatabase> orderProduct = orderService.getOrderProductByID(orderID);
        model.addAttribute("orderProducts", orderProduct);

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
        String userRole = loggedInUser.getRole();
        session.setAttribute(USER_ROLE, userRole);
        logger.info(userRole);
        model.addAttribute(USER_ROLE, userRole );
        return "OrderDetails";
    }
     @PostMapping("/approveOrder/{id}")
     public String approveOrder(@PathVariable int id,Model model, HttpSession session) {
         OrderDatabase order = orderRepository.findById(id).orElse(null);
         if (order != null) {
             order.setPopUpUser("NO");
             order.setConfAdmin("YES");
             orderRepository.save(order);
         }
         CustomerDb loggedInUser = (CustomerDb) session.getAttribute(LOGGED_IN_USER);
         String userRole = loggedInUser.getRole();
         session.setAttribute(USER_ROLE, userRole);
         logger.info(userRole);
         model.addAttribute(USER_ROLE, userRole );
         return "redirect:/CustomerOrderHistory";
     }


}
