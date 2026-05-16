package fa.training.dao;

import fa.training.entities.Order;

import java.util.List;

public interface OrderDAO {
    List<Order> getAllOrdersByCustomerId(int customerId);
    boolean addOrder(Order order);
    boolean updateOrderTotal(int orderId);
}
