package fa.training.dao;

import fa.training.common.DbConnection;
import fa.training.entities.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public List<Order> getAllOrdersByCustomerId(int customerId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Order> orders = new ArrayList<>();
        String sql = "select * from orders where customer_id = ?";
        try{
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order(
                        rs.getInt   ("order_id"),
                        rs.getDate  ("order_date"),
                        rs.getInt   ("customer_id"),
                        rs.getInt   ("employee_id"),
                        rs.getDouble("total")
                );
                orders.add(o);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error when adding order", e);
        } finally {
            if(rs != null) {
                try{
                    rs.close();
                }catch (SQLException e) {e.printStackTrace();}
            }
            if(ps != null) {
                try{
                    ps.close();
                }catch (SQLException e) {e.printStackTrace();}
            }
            if(conn != null) {
                try{
                    conn.close();
                }catch (SQLException e) {e.printStackTrace();}
            }
        }
        return orders;
    }

    @Override
    public boolean addOrder(Order order) {
        String sql = "insert into Orders(order_date,customer_id,employee_id,total) " +
                "values(?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(order.getOrderDate().getTime()));
            ps.setInt(2, order.getCustomerId());
            ps.setInt(3, order.getEmployeeId());
            ps.setDouble(4, order.getTotal());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error when adding order", e);
        }finally {
            if(ps != null) {
                try{
                    ps.close();
                }catch (SQLException e) {e.printStackTrace();}
            }
            if(conn != null) {
                try{
                    conn.close();
                }catch (SQLException e) {e.printStackTrace();}
            }
        }

    }

    @Override
    public boolean updateOrderTotal(int orderId) {
        String sql = "update Orders set total = computeOrderTotal(?) where order_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, orderId);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating order total", e);
        }finally {
            if(ps != null) {
                try{
                    ps.close();
                }catch (SQLException e) {e.printStackTrace();}
            }
            if(conn != null) {
                try{
                    conn.close();
                }catch (SQLException e) {e.printStackTrace();}
            }
        }

    }
}
