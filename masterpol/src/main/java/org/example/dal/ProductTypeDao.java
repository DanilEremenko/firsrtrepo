package org.example.dal;

import org.example.model.ProductType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductTypeDao {
    public List<ProductType> findAll() throws SQLException {
        List<ProductType> list = new ArrayList<>();
        try (Connection c = ConnectionManager.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM product_type")) {
            while (rs.next())
                list.add(new ProductType(rs.getInt("id"), rs.getString("name"), rs.getDouble("coefficient")));
        }
        return list;
    }
}
