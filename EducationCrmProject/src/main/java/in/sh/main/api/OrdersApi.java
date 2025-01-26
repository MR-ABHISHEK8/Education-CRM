package in.sh.main.api;

import in.sh.main.entities.Orders;
import in.sh.main.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrdersApi {
    @Autowired
    private OrderService orderService;

    @PostMapping("/storeOrderDetails")
    public ResponseEntity<String> storeUserDetails(@RequestBody Orders orders){
           orderService.storeUserOrders(orders);
           return ResponseEntity.ok("Order details stored successfully");
    }
}
