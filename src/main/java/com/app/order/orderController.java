package com.app.order;
import com.app.Installation.InstallationDB;
import com.app.Installation.InstallationRepository;
import com.app.Installation.InstallationService;
import com.app.customer.CustomerDb;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Controller
@SessionAttributes({"popupType", "popupMessage"})
public class orderController {
    @Autowired
    private orderService orderServicee;
    private final orderRepository OrderRepository;


    @Autowired
    public orderController (orderRepository OrderRepository){
        this.OrderRepository=OrderRepository;
    }


    @GetMapping(value = "/CustomerOrderHistory")
    public String showCustomerOrderHistory(Model model , HttpSession session) {

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");
        int userId = loggedInUser.getId();

        if("admin".equals(loggedInUser.getRole()) | "installer".equals(loggedInUser.getRole())){
            List<orderDB> orders = orderServicee.getOrderByConfAdmin("NO");
            model.addAttribute("orders", orders);
        }
        else{
            List<orderDB> orders = orderServicee.getOrderByCustomerId(userId);
            model.addAttribute("orders", orders);
        }

        CustomerDb loggedIn = (CustomerDb) session.getAttribute("loggedInUser");
        String userRole = loggedIn.getRole();
        session.setAttribute("userRole", userRole);
        System.out.println(userRole);
        model.addAttribute("userRole", userRole );
        return "ViewOrder";
    }

    @GetMapping("/orders/{orderId}")
    public String showInstallationDetails(@PathVariable Long orderId, Model model, HttpSession session) {
        System.out.println("Inside orderDetails method");
        orderDB order = OrderRepository.findById(Math.toIntExact(orderId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid order id: " + orderId));
        model.addAttribute("order", order);
        System.out.println("Installation Details: " + order);

        int orderID= order.getId();
        List<orderProductDB> orderProduct = orderServicee.getOrderProductByID(orderID);
        model.addAttribute("orderProducts", orderProduct);

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");
        String userRole = loggedInUser.getRole();
        session.setAttribute("userRole", userRole);
        System.out.println(userRole);
        model.addAttribute("userRole", userRole );
//        if(loggedInUser.getRole().equals("admin") | loggedInUser.getRole().equals("installer"))
//            return "AdminOrderDetails";
        return "OrderDetails";
    }
     @PostMapping("/approveOrder/{id}")
     public String approveOrder(@PathVariable int id,Model model, HttpSession session) {
         orderDB order = OrderRepository.findById(id).orElse(null);
         order.setPopUpUser("NO");
         CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");
         String userRole = loggedInUser.getRole();
         session.setAttribute("userRole", userRole);
         System.out.println(userRole);
         model.addAttribute("userRole", userRole );
         return "redirect:/CustomerOrderHistory";
     }


}
