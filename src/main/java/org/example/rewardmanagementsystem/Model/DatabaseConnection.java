package org.example.rewardmanagementsystem.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnection {

    // Thông tin kết nối Database
    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/qlhsdb";
    private static final String DB_USER = "root"; // Tài khoản MySQL
    private static final String DB_PASSWORD = "Hoanganh@21"; // Mật khẩu MySQL

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Kết nối với Database
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            System.out.println("Kết nối thành công!");

            // Thực hiện một truy vấn mẫu
            String query = "SELECT * FROM child"; // Thay 'table_name' bằng bảng trong database của bạn
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Lặp qua kết quả
            while (resultSet.next()) {
                System.out.println("Dữ liệu: " + resultSet.getString("ChildID")); // Thay 'column_name' bằng cột trong bảng
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close(); // Đóng kết nối
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}