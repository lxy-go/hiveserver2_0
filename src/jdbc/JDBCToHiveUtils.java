package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCToHiveUtils {
    private static String DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";
    private static String URL = "jdbc:hive2://192.168.179.101:10000/default";
    private static Connection conn;

    public static Connection getConnection(){
        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(URL, "root", "Welcome_1");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static PreparedStatement prepare(Connection conn,String sql){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }
}
