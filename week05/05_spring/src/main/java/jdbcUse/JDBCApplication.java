package jdbcUse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import starterUse.DemoApplication;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * 研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
 * 1）使用 JDBC 原生接口，实现数据库的增删改查操作。
 * 2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
 * 3）配置 Hikari 连接池，改进上述操作。提交代码到 GitHub。
 */
public class JDBCApplication {
    public static void main(String[] args) throws SQLException {

        String sql = "";
        String allData = "";
        int resultInt=-1;

        //原生JDBC+事务提交
        JDBCUnits jdbcUnits = new JDBCUnits();
        jdbcUnits.init();
        //新增
        ArrayList<Object> objParam = new ArrayList<>();
        sql = "INSERT INTO user(name,age) VALUES(?,?)";
        objParam.add("zhang");
        objParam.add(4);
        resultInt = jdbcUnits.execute(sql, objParam);
        if (resultInt == -1)
            System.out.println("插入失败");
        sql = "select * from user";
        allData = jdbcUnits.executeQuery2(sql, null);
        System.out.println("插入后，查询结果：" + allData);
        //更新
        sql = "update user set name=? where id=?";
        objParam.clear();
        objParam.add("zhangshan2");
        objParam.add(1);
        resultInt = jdbcUnits.execute(sql, objParam);
        if (resultInt == -1)
            System.out.println("更新失败");
        sql = "select * from user";
        allData = jdbcUnits.executeQuery2(sql, null);
        System.out.println("更新后，查询结果：" + allData);

        //删除
        sql = "delete from user where id=?";
        objParam.clear();
        objParam.add(1);
        resultInt = jdbcUnits.execute(sql, objParam);
        if (resultInt == -1)
            System.out.println("删除失败");
        sql = "select * from user";
        allData = jdbcUnits.executeQuery2(sql, null);
        System.out.println("删除后，查询结果：" + allData);
        jdbcUnits.close();

///////////////////////////////////////////////////////////////////////////////////////////
        int updateDetelteInt=3;
        //Hikari 操作
        JDBCHikari jdbcHikari = new JDBCHikari();
        jdbcHikari.init();
        //新增
        sql = "INSERT INTO user(name,age) VALUES('zhangshan555',23)";
        resultInt = jdbcHikari.execute(sql);
        if (resultInt == -1)
            System.out.println("Hikari插入失败");
        sql = "select * from user";
        allData = jdbcHikari.executeQuery2(sql, null);
        System.out.println("Hikari插入后，查询结果：" + allData);
        //更新
        sql = String.format("update user set name='zhangshan6_%s' where id=%d",updateDetelteInt,updateDetelteInt) ;
        resultInt = jdbcHikari.execute(sql);
        if (resultInt == -1)
            System.out.println("更新失败");
        sql = "select * from user";
        allData = jdbcHikari.executeQuery2(sql, null);
        System.out.println("Hikari更新后，查询结果：" + allData);

        //删除
        sql = String.format("delete from user where id=%d",updateDetelteInt) ;
        resultInt = jdbcHikari.execute(sql);
        if (resultInt == -1)
            System.out.println("删除失败");
        sql = "select * from user";
        allData = jdbcHikari.executeQuery2(sql, null);
        System.out.println("Hikari删除后，查询结果：" + allData);
        jdbcHikari.close();
    }
}
