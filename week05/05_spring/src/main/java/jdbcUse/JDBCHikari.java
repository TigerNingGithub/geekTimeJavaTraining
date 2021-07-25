package jdbcUse;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;

/**
 * Hikari 连接池
 */
public class JDBCHikari {

    private Statement mStatement;
    public void init(){
        JDBC_Config config=new JDBC_Config();
        String url = config.getUrl();
        String driverName = config.getDriverName();
        String userName = config.getUserName();
        String password = config.getPassword();

        HikariConfig conf =new HikariConfig();
        conf.setUsername(userName);
        conf.setPassword(password);
        conf.setJdbcUrl(url);
        HikariDataSource ds =new HikariDataSource(conf);
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            mStatement=statement;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 执行
     *PreparedStatement  事务处理
     * @param strSQLText
     * @return
     */
    public int execute(String strSQLText) {
        //预编译SQL，减少sql执行
        int result = -1;
        try {
            result = mStatement.executeUpdate(strSQLText);
        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * @param strSQLText
     * @return ResultSet
     */
    public String executeQuery2(String strSQLText, ArrayList<Object> objs) throws SQLException {
        ResultSet rs = executeQuery(strSQLText);
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        StringBuffer allData=new StringBuffer();
        while (rs.next()) {
            StringBuffer rowData = new StringBuffer();
            for (int n = 1; n <= columnCount; n++) {
                String name = meta.getColumnName(n);
                int types = meta.getColumnType(n);
                rowData.append(name + ":");
                if (Types.INTEGER == types)
                    rowData.append(rs.getInt(n));
                else
                    rowData.append(rs.getString(n));
                rowData.append("  ");
            }
            allData.append(rowData+"\n\r");
        }
        return allData.toString();
    }

    /**
     * @param strSQLText
     * @return ResultSet
     */
    public ResultSet executeQuery(String strSQLText) {
        if (mStatement == null) return null;
        if (isClose()) return null;
        ResultSet resultSet = null;
        try {
            resultSet =  mStatement.executeQuery(strSQLText);
        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }


    /**
     * 断开数据库连接
     */
    public void close() {
        if (mStatement == null) return;

        try {
            if (mStatement.isClosed()) return;
            mStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * 数据库是否已关闭
     *
     * @return
     */
    public boolean isClose() {
        try {
            return mStatement.isClosed();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
}
