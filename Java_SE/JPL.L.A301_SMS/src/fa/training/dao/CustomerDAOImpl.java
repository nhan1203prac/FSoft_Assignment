package fa.training.dao;

import fa.training.common.DbConnection;
import fa.training.entities.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {


    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT customer_id, customer_name FROM Customer";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("customer_name")
                );
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public boolean addCustomer(Customer customer) {
        String sql = "{CALL addCustomer(?)}";

        try (Connection con = DbConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, customer.getCustomerName());

            int rows = cs.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean deleteCustomer(int customerId) {
        String sql = "{CALL deleteCustomer(?)}";

        try (Connection con = DbConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, customerId);

            int rows = cs.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean updateCustomer(Customer customer) {
        String sql = "{CALL updateCustomer(?, ?)}";

        try (Connection con = DbConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, customer.getCustomerId());
            cs.setString(2, customer.getCustomerName());

            int rows = cs.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}