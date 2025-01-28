package in.sh.main.api;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import in.sh.main.entities.Orders;
import in.sh.main.services.OrderService;
import org.json.JSONObject;
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

//    @PostMapping("/storeOrderDetails")
//    public ResponseEntity<String> storeUserDetails(@RequestBody Orders orders){
//           orderService.storeUserOrders(orders);
//           return ResponseEntity.ok("Order details stored successfully");
//    }

    @PostMapping("/storeOrderDetails")
    public ResponseEntity<String> storeUserDetails(@RequestBody Orders orders) throws RazorpayException {


        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_9BLhKcsgvQhrXm", "ZhRSePRhZTU0voQVvQ4MRT12");

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount",orders.getCourseAmount()); // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
        orderRequest.put("currency","INR");
        orderRequest.put("receipt", "rcpt_id_"+System.currentTimeMillis());


        Order order = razorpayClient.orders.create(orderRequest);
//        System.out.println(order);

        String orderId=order.get("id");
        orders.setOrderId(orderId);



           orderService.storeUserOrders(orders);
           return ResponseEntity.ok("Order details stored successfully");
    }

}
