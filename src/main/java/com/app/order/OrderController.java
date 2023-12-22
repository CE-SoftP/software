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
@SessionAttributes({"popupType", "popupMessage"})
public class OrderController {
    Logger logger = Logger.getLogger(getClass().getName());
    private final orderService orderService;
    private final orderRepository orderRepository;
    private final static String loggedInConst ="loggedInUser";
    private final static String userRoleConst = "userRole";


    @Autowired
    public OrderController(orderRepository OrderRepository , orderService orderService){
        this.orderRepository =OrderRepository;
        this.orderService=orderService;
    }


    @GetMapping(value = "/CustomerOrderHistory")
    public String showCustomerOrderHistory(Model model , HttpSession session) {

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(loggedInConst);
        int userId = loggedInUser.getId();

        if("admin".equals(loggedInUser.getRole()) ||"installer".equals(loggedInUser.getRole())){
            List<orderDB> orders = orderService.getOrderByConfAdmin("NO");
            model.addAttribute("orders", orders);
        }
        else{
            List<orderDB> orders = orderService.getOrderByCustomerId(userId);
            model.addAttribute("orders", orders);
        }

        CustomerDb loggedIn = (CustomerDb) session.getAttribute(loggedInConst);
        String userRole = loggedIn.getRole();
        session.setAttribute(userRoleConst, userRole);
        logger.info(userRole);
        model.addAttribute(userRoleConst, userRole );
        return "ViewOrder";
    }

    @GetMapping("/orders/{orderId}")
    public String showInstallationDetails(@PathVariable Long orderId, Model model, HttpSession session) {
        logger.info("Inside orderDetails method");
        orderDB order = orderRepository.findById(Math.toIntExact(orderId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid order id: " + orderId));
        model.addAttribute("order", order);
        logger.info("Installation Details: " + order);

        int orderID= order.getId();
        List<orderProductDB> orderProduct = orderService.getOrderProductByID(orderID);
        model.addAttribute("orderProducts", orderProduct);

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute(loggedInConst);
        String userRole = loggedInUser.getRole();
        session.setAttribute(userRoleConst, userRole);
        logger.info(userRole);
        model.addAttribute(userRoleConst, userRole );
        return "OrderDetails";
    }
     @PostMapping("/approveOrder/{id}")
     public String approveOrder(@PathVariable int id,Model model, HttpSession session) {
         orderDB order = orderRepository.findById(id).orElse(null);
         if (order != null) {
             order.setPopUpUser("NO");
             order.setConfAdmin("YES");
             orderRepository.save(order);
         }
         CustomerDb loggedInUser = (CustomerDb) session.getAttribute(loggedInConst);
         String userRole = loggedInUser.getRole();
         session.setAttribute(userRoleConst, userRole);
         logger.info(userRole);
         model.addAttribute(userRoleConst, userRole );
         return "redirect:/CustomerOrderHistory";
     }


}
