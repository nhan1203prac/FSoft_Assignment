package fa.training.dao;

import fa.training.common.DbConnection;
import fa.training.entities.LineItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineItemDAOImpl implements LineItemDAO {
    @Override
    public List<LineItem> getAllItemsByOrderId(int orderId) {
        List<LineItem> items = new ArrayList<>();
        String sql = "select * from LineItem where order_id=?";
        try(Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, orderId);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    LineItem item = new LineItem(
                            rs.getInt("order_id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity"),
                            rs.getDouble("price")
                    );
                    items.add(item);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Error in getAllItemsByOrderId " + e.getMessage());
        }
        return items;

    }

    @Override
    public Double computeOrderTotal(int orderId) {
        String sql = "select computeOrderTotal as total_price";
        try(Connection con = DbConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, orderId);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return rs.getDouble("total_price");
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Error in computeOrderTotal " + e.getMessage());
        }
       return null;
    }

    @Override
    public boolean addLineItem(LineItem item) {
        String sql = "insert into LineItem(order_id,product_id,quantity,price) values(?,?,?,?)";
        try(Connection con = DbConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);){
            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getPrice());
            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            throw new RuntimeException("Error in addLineItem " + e.getMessage());
        }

    }
}
