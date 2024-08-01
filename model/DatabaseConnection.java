package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() throws SQLException// xử lý ngoại lệ xảy ra khi có vấn đề liên quan đến cơ sở dữ liệu
    {
        try {
            String url = "jdbc:sqlserver://hynhphuc:1433;databaseName=ThuVienSach;encrypt=true;trustServerCertificate=true;trustStore=\"C:\\Users\\ASUS\\truststore.jks\";trustStorePassword=070999;";
            String username = "sa";
            String password = "070999";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            throw new SQLException("Error connecting to database", ex);
        }
    }

    // Singleton Sử dụng cho kết nối cơ sở dữ liệu, đảm bảo chỉ có một kết nối duy
    // nhất được sử dụng trong suốt thời gian hoạt động của ứng dụng
    public static DatabaseConnection getInstance() throws SQLException
    // xử lý ngoại lệ xảy ra khi có vấn đề liên quan đến cơ sở dữ liệu
    {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed())// Kiểm tra xem kết nối cơ sở dữ liệu trong đối tượng instance có
                                                       // bị đóng hay không.
        {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
