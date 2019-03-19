1.启动HDFS 和 Yarn

```shell
start-all.sh//不建议用这个,建议分别启动hdfs和yarn
```

2.修改`hive-site.xml`配置


```shell
<property>
  <name>hive.metastore.warehouse.dir</name>
  <value>/usr/hive/warehouse</value>               //（hive中的数据库和表在HDFS中存放的文件夹的位置）
  <description>location of default database for the warehouse</description>
</property>
<property>
  <name>hive.server2.thrift.port</name>
  <value>10000</value>                               //（HiveServer2远程连接的端口，默认为10000）
  <description>Port number of HiveServer2 Thrift interface.
  Can be overridden by setting $HIVE_SERVER2_THRIFT_PORT</description>
</property>
<property>
  <name>hive.server2.thrift.bind.host</name>
  <value>node001</value>                          //（hive所在集群的IP地址）
  <description>Bind host on which to run the HiveServer2 Thrift interface.
  Can be overridden by setting $HIVE_SERVER2_THRIFT_BIND_HOST</description>
</property>
<property>
  <name>hive.server2.long.polling.timeout</name>
  <value>5000</value>                                // (默认为5000L,此处修改为5000，不然程序会报错)
  <description>Time in milliseconds that HiveServer2 will wait, before responding to asynchronous calls that use long polling</description>
</property>
<property>
  <name>javax.jdo.option.ConnectionURL</name>
  <value>jdbc:mysql://localhost:3306/hive?createDatabaseIfNotExist=true</value>  //（Hive的元数据库，我采用的是本地Mysql作为元数据库）
  <description>JDBC connect string for a JDBC metastore</description>
</property>
 
<property>                         
  <name>javax.jdo.option.ConnectionDriverName</name>          //（连接元数据的驱动名）
  <value>com.mysql.jdbc.Driver</value>
  <description>Driver class name for a JDBC metastore</description>
</property>
<property>
  <name>javax.jdo.option.ConnectionUserName</name>             //（连接元数据库用户名）
  <value>hive</value>
  <description>username to use against metastore database</description>
</property>
<property>
  <name>javax.jdo.option.ConnectionPassword</name>             // （连接元数据库密码）
  <value>hive</value>
  <description>password to use against metastore database</description>
</property>
```

3.启动metastore和hiveserver2

3.1 配置日志，方便查看

编辑`$HIVE_HOME/conf/hive-log4j.properties`

修改日志输出路径
```shell
hive.log.threshold=ALL
hive.root.logger=INFO,DRFA
//日志输出路径，自己建立目录即可
hive.log.dir=/home/hadoop/app/hive-1.1.0-cdh5.7.0/log
hive.log.file=hive.log
```

3.2 启动进程


```shell
hive --service metastore &

hive --service hiveserver2 &
```
3.3 检查日志

检查`$HIVE_HOME/log/hive.log`下的日志文件，确保成功启动

4.导出jar包

java代码需要的jar包

```shell
$HIVE_HOME\commons-logging-1.1.3.jar
$HIVE_HOME\hadoop-common-2.3.0-cdh5.1.3.jar
$HIVE_HOME\hive-common-0.12.0-cdh5.1.3.jar
$HIVE_HOME\hive-exec-0.12.0-cdh5.1.3.jar
$HIVE_HOME\hive-jdbc-0.12.0-cdh5.1.3.jar
$HIVE_HOME\hive-metastore-0.12.0-cdh5.1.3.jar
$HIVE_HOME\hive-service-0.12.0-cdh5.1.3.jar
$HIVE_HOME\libfb303-0.9.0.jar
$HIVE_HOME\log4j-1.2.16.jar
$HIVE_HOME\slf4j-api-1.7.5.jar
$HIVE_HOME\slf4j-log4j12-1.7.5.jar
$HIVE_HOME\httpclient-4.2.5.jar
$HIVE_HOME\httpcore-4.2.5.jar
```
如果没添加最后两个，可能会出现下面的报错

```shell
Exception in thread "main" java.lang.NoClassDefFoundError: org/apache/http/HttpRequestInterceptor
     at org.apache.hive.jdbc.HiveDriver.connect(HiveDriver.java:105)
     at java.sql.DriverManager.getConnection(DriverManager.java:571)
     at java.sql.DriverManager.getConnection(DriverManager.java:215)
     at com.simon.HiveJDBCTest.main(HiveJDBCTest.java:33)
Caused by: java.lang.ClassNotFoundException: org.apache.http.HttpRequestInterceptor
     at java.net.URLClassLoader$1.run(URLClassLoader.java:366)
     at java.net.URLClassLoader$1.run(URLClassLoader.java:355)
     at java.security.AccessController.doPrivileged(Native Method)
     at java.net.URLClassLoader.findClass(URLClassLoader.java:354)
     at java.lang.ClassLoader.loadClass(ClassLoader.java:425)
     at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:308)
     at java.lang.ClassLoader.loadClass(ClassLoader.java:358)
     ... 4 more
```

5.编写Java代码

5.1 JDBC注册


```
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
```

5.2 查询工具类


```
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryHiveUtils {
    private static Connection conn = JDBCToHiveUtils.getConnection();
    private static PreparedStatement ps;
    private static ResultSet rs;
    public static void getAll(String tablename){
        String sql = "SELECT * FROM "+tablename;
        ps = JDBCToHiveUtils.prepare(conn, sql);
        try {
            rs = ps.executeQuery();
            int cols = rs.getMetaData().getColumnCount();
            while (rs.next()){
                for (int i = 1; i <= cols ; i++) {
                    System.out.println(rs.getString(i));
                    System.out.println("\t\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```


5.3 测试


```
public class QueryHiveTest {
    public static void main(String[] args) {
        String tablename = "t1";
        QueryHiveUtils.getAll(tablename);
    }
}
```

注意：如果下面还有其他查询，不要关闭数据库连接

报错：


```
SASL authentication not complete
```


6.运行


