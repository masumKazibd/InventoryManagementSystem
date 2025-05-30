package com.inventorymanagementsystem.inventorymanagementsystem.dao;

import com.inventorymanagementsystem.inventorymanagementsystem.model.Product;
import com.inventorymanagementsystem.inventorymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class ProductDAO {
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                products.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

}
