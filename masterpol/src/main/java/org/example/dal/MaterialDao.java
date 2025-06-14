package org.example.dal;

import org.example.model.Material;
import org.example.model.MaterialType;
import org.example.model.Product;
import org.example.model.ProductType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDao {
    private static final String FIND_ALL =
            "SELECT m.id, m.name, m.price, m.quantity, m.min_quantity, " +
                    "m.pack_size, m.unit, t.id AS type_id, t.name AS type_name, " +
                    "t.material_loss_percent " +
                    "FROM material m JOIN material_type t ON m.type_id = t.id";

    private static final String FIND_PRODUCTS_BY_MATERIAL =
            "SELECT p.id, p.name, p.sku, p.min_price_partner, " +  // Исправлено здесь
                    "pt.id AS product_type_id, pt.name AS product_type_name, " +
                    "pt.coefficient, mp.required_quantity " +
                    "FROM product p " +
                    "JOIN product_type pt ON p.type_id = pt.id " +
                    "JOIN material_product mp ON p.id = mp.product_id " +
                    "WHERE mp.material_id = ?";

    private static final String CALC_REQUIRED_QTY =
            "SELECT SUM(required_quantity) FROM material_product WHERE material_id = ?";

    private static final String INSERT =
            "INSERT INTO material(name, type_id, price, quantity, " +
                    "min_quantity, pack_size, unit) VALUES(?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE =
            "UPDATE material SET name = ?, type_id = ?, price = ?, " +
                    "quantity = ?, min_quantity = ?, pack_size = ?, unit = ? " +
                    "WHERE id = ?";

    private static final String DELETE =
            "DELETE FROM material WHERE id = ?";

    public List<Material> findAll() throws SQLException {
        List<Material> materials = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL)) {

            while (rs.next()) {
                Material material = new Material();
                material.setId(rs.getInt("id"));
                material.setName(rs.getString("name"));
                material.setPrice(rs.getDouble("price"));
                material.setQuantity(rs.getDouble("quantity"));
                material.setMinQuantity(rs.getDouble("min_quantity"));
                material.setPackSize(rs.getDouble("pack_size"));
                material.setUnit(rs.getString("unit"));

                MaterialType type = new MaterialType();
                type.setId(rs.getInt("type_id"));
                type.setName(rs.getString("type_name"));
                type.setMaterialLossPercent(rs.getDouble("material_loss_percent"));

                material.setType(type);
                materials.add(material);
            }
        }
        return materials;
    }

    public List<Product> findProductsByMaterial(int materialId) throws SQLException {
        List<Product> products = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_PRODUCTS_BY_MATERIAL)) {

            stmt.setInt(1, materialId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setArticle(rs.getString("sku"));
                    product.setMinPartnerPrice(rs.getDouble("min_price_partner"));

                    ProductType type = new ProductType();
                    type.setId(rs.getInt("product_type_id"));
                    type.setName(rs.getString("product_type_name"));
                    type.setCoefficient(rs.getDouble("coefficient"));

                    product.setType(type);
                    products.add(product);
                }
            }
        }
        return products;
    }

    public double calculateRequiredQuantity(int materialId) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CALC_REQUIRED_QTY)) {

            stmt.setInt(1, materialId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getDouble(1) : 0;
            }
        }
    }

    public void save(Material material) throws SQLException {
        String sql = material.getId() == 0 ? INSERT : UPDATE;

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, material.getName());
            stmt.setInt(2, material.getType().getId());
            stmt.setDouble(3, material.getPrice());
            stmt.setDouble(4, material.getQuantity());
            stmt.setDouble(5, material.getMinQuantity());
            stmt.setDouble(6, material.getPackSize());
            stmt.setString(7, material.getUnit());

            if (material.getId() != 0) {
                stmt.setInt(8, material.getId());
            }

            stmt.executeUpdate();

            if (material.getId() == 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        material.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}