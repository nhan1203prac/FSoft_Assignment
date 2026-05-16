package fa.training.dao;

import fa.training.common.DbConnection;
import fa.training.entities.Customer;
import fa.training.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();
        String sql = "select * from Product";
        try(Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product c = new Product(rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getDouble("listPrice"));
                products.add(c);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


}
