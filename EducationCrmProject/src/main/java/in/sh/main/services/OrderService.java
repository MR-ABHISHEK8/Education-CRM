package in.sh.main.services;

import in.sh.main.entities.Orders;
import in.sh.main.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;

    public void storeUserOrders(Orders orders){
           ordersRepository.save(orders);
    }
}
