package jdbc;

public class QueryHiveTest {
    public static void main(String[] args) {
        String tablename = "student_ptn";
//        QueryHiveUtils.getAll(tablename);
//        QueryHiveUtils.getDesc(tablename);
//        QueryHiveUtils.addCol(tablename);
//        QueryHiveUtils.alterCol(tablename);
//        QueryHiveUtils.alterPartition(tablename);
//        QueryHiveUtils.loadData(tablename);
        QueryHiveUtils.createTable("s1");
    }
}
