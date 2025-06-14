package org.example.service;

import org.example.dal.MaterialDao;
import org.example.dal.ProductTypeDao;
import org.example.model.Material;
import org.example.model.MaterialType;
import org.example.model.Product;
import org.example.model.ProductType;

import javax.swing.*;
import java.util.List;

public class MaterialService {
    private final MaterialDao materialDao = new MaterialDao();
    private final ProductTypeDao productTypeDao = new ProductTypeDao();

    // Основные CRUD операции
    public List<Material> getAllMaterials() {
        try {
            return materialDao.findAll();
        } catch (Exception e) {
            showError("Ошибка загрузки материалов", e);
            return List.of();
        }
    }

    public void saveMaterial(Material material) {
        try {
            materialDao.save(material);
        } catch (Exception e) {
            showError("Ошибка сохранения материала", e);
        }
    }

    public void deleteMaterial(int id) {
        try {
            materialDao.delete(id);
        } catch (Exception e) {
            showError("Ошибка удаления материала", e);
        }
    }

    // Работа с продукцией
    public List<Product> getProductsUsingMaterial(int materialId) {
        try {
            return materialDao.findProductsByMaterial(materialId);
        } catch (Exception e) {
            showError("Ошибка загрузки продукции", e);
            return List.of();
        }
    }

    public double calculateRequiredMaterialQuantity(int materialId) {
        try {
            return materialDao.calculateRequiredQuantity(materialId);
        } catch (Exception e) {
            showError("Ошибка расчета требуемого количества", e);
            return 0;
        }
    }



    public int calculateProductCount(int productTypeId, int materialTypeId,
                                     double rawQuantity, double param1, double param2) {
        try {
            ProductType productType = getProductTypeById(productTypeId);
            MaterialType materialType = getMaterialTypeById(materialTypeId);

            if (productType == null || materialType == null ||
                    rawQuantity <= 0 || param1 <= 0 || param2 <= 0) {
                return -1;
            }

            double perUnit = param1 * param2 * productType.getCoefficient();
            double lossFactor = 1 + (materialType.getMaterialLossPercent() / 100.0);
            double totalPerUnit = perUnit * lossFactor;

            return (int) Math.floor(rawQuantity / totalPerUnit);
        } catch (Exception e) {
            showError("Ошибка расчета необходимых материалов продукции", e);
            return -1;
        }
    }

    // Вспомогательные методы
    public List<ProductType> getAllProductTypes() {
        try {
            return productTypeDao.findAll();
        } catch (Exception e) {
            showError("Ошибка загрузки типов продукции", e);
            return List.of();
        }
    }

    private ProductType getProductTypeById(int id) {
        return getAllProductTypes().stream()
                .filter(pt -> pt.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private MaterialType getMaterialTypeById(int id) {
        return getAllMaterials().stream()
                .map(Material::getType)
                .filter(mt -> mt.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private void showError(String message, Exception e) {
        JOptionPane.showMessageDialog(
                null,
                message + ": " + e.getMessage(),
                "Ошибка",
                JOptionPane.ERROR_MESSAGE
        );
    }
}



//package org.example.service;
//
//import org.example.dal.ConnectionManager;
//import org.example.dal.MaterialDao;
//import org.example.dal.ProductTypeDao;
//import org.example.model.Material;
//import org.example.model.MaterialType;
//import org.example.model.Product;
//import org.example.model.ProductType;
//
//import javax.swing.*;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MaterialService {
//    private MaterialDao dao = new MaterialDao();
//    private ProductTypeDao pdao = new ProductTypeDao();
//
//    public List<Material> getMaterials() {
//        try {
//            return dao.findAll();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
//            return List.of();
//        }
//    }
//
//    public List<ProductType> getProductTypes() {
//        try {
//            return pdao.findAll();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
//            return List.of();
//        }
//    }
//
//    public double calculatePurchase(Material m) {
//        double deficit = m.getMinQuantity() - m.getQuantity();
//        if (deficit <= 0) return 0;
//        double lossMultiplier = 1 + (m.getType().getMaterialLossPercent() / 100.0);
//        double effectiveDeficit = deficit * lossMultiplier;
//        double packs = Math.ceil(effectiveDeficit / m.getPackSize());
//        return packs * m.getPackSize() * m.getPrice();
//    }
//
//    public int calculateProductCount(int productTypeId, int materialTypeId,
//                                     double rawQty, double param1, double param2) {
//        try {
//            ProductType pt = getProductTypes().stream()
//                    .filter(p -> p.getId() == productTypeId)
//                    .findFirst().orElse(null);
//            MaterialType mt = getMaterials().stream()
//                    .map(Material::getType)
//                    .filter(t -> t.getId() == materialTypeId)
//                    .findFirst().orElse(null);
//
//            if (pt == null || mt == null || rawQty <= 0 || param1 <= 0 || param2 <= 0)
//                return -1;
//
//            double perUnit = param1 * param2 * pt.getCoefficient();
//            double lossFactor = 1 + (mt.getMaterialLossPercent() / 100.0);
//            double totalPerUnit = perUnit * lossFactor;
//
//            return (int) Math.floor(rawQty / totalPerUnit);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Ошибка расчета: " + e.getMessage());
//            return -1;
//        }
//    }
//
//
//    public void save(Material m) {
//        try {
//            dao.save(m);
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Сохранение не удалось: " + e.getMessage());
//        }
//    }
//
//    public void delete(int id) {
//        try {
//            dao.delete(id);
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Удаление не удалось: " + e.getMessage());
//        }
//    }
//
//    public List<Product> getProductsUsingMaterial(int materialId) {
//        String sql = "SELECT p.*, pt.name AS type_name, pt.coefficient " +
//                "FROM product p JOIN product_type pt ON p.type_id=pt.id " +
//                "JOIN material_product mp ON p.id=mp.product_id " +
//                "WHERE mp.material_id=?";
//        List<Product> products = new ArrayList<>();
//        try (Connection c = ConnectionManager.getConnection();
//             PreparedStatement ps = c.prepareStatement(sql)) {
//            ps.setInt(1, materialId);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    ProductType type = new ProductType(
//                            rs.getInt("type_id"),
//                            rs.getString("type_name"),
//                            rs.getDouble("coefficient")
//                    );
//                    products.add(new Product(
//                            rs.getInt("id"),
//                            type,
//                            rs.getString("name"),
//                            rs.getString("article"),
//                            rs.getDouble("min_partner_price")
//                    ));
//                }
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Ошибка загрузки продукции: " + e.getMessage());
//        }
//        return products;
//    }
//
//    public double calculateRequiredMaterialQty(int materialId) {
//        String sql = "SELECT SUM(required_quantity) FROM material_product WHERE material_id=?";
//        try (Connection c = ConnectionManager.getConnection();
//             PreparedStatement ps = c.prepareStatement(sql)) {
//            ps.setInt(1, materialId);
//            try (ResultSet rs = ps.executeQuery()) {
//                return rs.next() ? rs.getDouble(1) : 0;
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Ошибка расчета: " + e.getMessage());
//            return 0;
//        }
//    }
//}
