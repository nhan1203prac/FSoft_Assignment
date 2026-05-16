package fa.training.dao;

import fa.training.entities.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    List<Product> getAllProduct();

}
