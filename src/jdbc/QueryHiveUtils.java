package jdbc;

import java.sql.*;

public class QueryHiveUtils {
    private static Connection conn = JDBCToHiveUtils.getConnection();
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static Integer num;

    public static void getAll(String tablename){
        String sql = "SELECT * FROM "+tablename;
        ps = JDBCToHiveUtils.prepare(conn, sql);
        try {
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int cols = rs.getMetaData().getColumnCount();
            while (rs.next()){
                for (int i = 1; i <= cols ; i++) {
                    System.out.print(rs.getString(i)+"\t | ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally {
//            if (ps != null){
//                try {
//                    ps.close();
//                } catch (SQLException e1) {
//                    e1.printStackTrace();
//                }
//            }
//            if (conn != null){
//                try {
//                    conn.close();
//                } catch (SQLException e1) {
//                    e1.printStackTrace();
//                }
//            }
        }
    }

    public static void getDesc(String tablename){
        String sql = "desc formatted "+tablename;
        ps = JDBCToHiveUtils.prepare(conn, sql);
        try {
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int cols = rs.getMetaData().getColumnCount();
            while (rs.next()){
                for (int i = 1; i <= cols ; i++) {
                    System.out.print(rs.getString(i)+"\t | ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally {
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    public static void addCol(String tablename){
        String sql = "alter table " +tablename+" add columns (score int)";
        ps = JDBCToHiveUtils.prepare(conn, sql);
        try {
            num = ps.executeUpdate(sql);

        } catch (SQLException e) {

//            System.out.println("事务回滚");
            e.printStackTrace();
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally {
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void alterCol(String tablename){
        String sql = "alter table " +tablename+" change score new_score int";
        ps = JDBCToHiveUtils.prepare(conn, sql);
        try {
            num = ps.executeUpdate(sql);

        } catch (SQLException e) {

//            System.out.println("事务回滚");
            e.printStackTrace();
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally {
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    public static void alterPartition(String tablename){
        String sql = "alter table " +tablename+" add partition(city=\"chongqing\")";
        ps = JDBCToHiveUtils.prepare(conn, sql);
        try {
            num = ps.executeUpdate(sql);

        } catch (SQLException e) {

//            System.out.println("事务回滚");
            e.printStackTrace();
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally {
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void loadData(String tablename){
        String sql = "load data inpath \"/input/student.txt\" into table student_ptn partition(city=\"beijing\")";
        ps = JDBCToHiveUtils.prepare(conn, sql);
        try {
            num = ps.executeUpdate(sql);

        } catch (SQLException e) {

//            System.out.println("事务回滚");
            e.printStackTrace();
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally {
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void createTable(String tablename){
        String sql = "create table "+tablename+" (id int,name string) row format delimited fields terminated by \",\"";
        ps = JDBCToHiveUtils.prepare(conn, sql);
        try {
            num = ps.executeUpdate(sql);

        } catch (SQLException e) {

//            System.out.println("事务回滚");
            e.printStackTrace();
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally {
            if (ps != null){
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
