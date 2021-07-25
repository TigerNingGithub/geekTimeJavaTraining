package jdbcUse;


import java.sql.*;
import java.util.ArrayList;

/**
 * 原生的JDBC
 */
public class JDBCUnits {

    private Connection mDBConn;
    public void init(){
        JDBC_Config config=new JDBC_Config();
        String url = config.getUrl();
        String driverName = config.getDriverName();
        String userName = config.getUserName();
        String password = config.getPassword();
        try {
            Class.forName(driverName);
            mDBConn = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行
     *PreparedStatement  事务处理
     * @param strSQLText
     * @return
     */
    public int execute(String strSQLText, ArrayList<Object> objs) {
        //预编译SQL，减少sql执行
        PreparedStatement ptmt;
        int result = -1;
        try {
            ptmt = mDBConn.prepareStatement(strSQLText);
            if (objs != null) {
                setPreparedStatementParameters(ptmt, objs);
            }
            result = ptmt.executeUpdate();
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
        ResultSet rs = executeQuery(strSQLText,objs);
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        StringBuffer allData=new StringBuffer();
        while (rs.next()) {
            StringBuffer rowData = new StringBuffer();
            for (int n = 1; n <= columnCount; n++) {
                String name = meta.getColumnName(n);
                int types = meta.getColumnType(n);
                rowData.append(name + ":");
                if (java.sql.Types.INTEGER == types)
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
    public ResultSet executeQuery(String strSQLText, ArrayList<Object> objs) {
        if (mDBConn == null) return null;
        if (isClose()) return null;

        //预编译SQL，减少sql执行
        PreparedStatement ptmt;
        ResultSet resultSet = null;
        try {
            ptmt = mDBConn.prepareStatement(strSQLText);
            if (objs != null) {
                setPreparedStatementParameters(ptmt, objs);
            }
            resultSet = ptmt.executeQuery();
        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * 给PreparedStatement设置参数
     *
     * @param ptmt
     */
    private void setPreparedStatementParameters(PreparedStatement ptmt, ArrayList<Object> objs) throws SQLException {
        for (int n = 0; n < objs.size(); n++) {
            Object obj = objs.get(n);
            //PreparedStatement参数索引从1数起
            int index = n + 1;

            if (obj instanceof Integer) {
                ptmt.setInt(index, (Integer) obj);
            } else if (obj instanceof String) {
                ptmt.setString(index, obj.toString());
            } else if (obj instanceof Double) {
                ptmt.setDouble(index, (Double) obj);
            } else if (obj instanceof Float) {
                ptmt.setFloat(index, (Float) obj);
            } else if (obj instanceof Long) {
                ptmt.setLong(index, (Long) obj);
            } else if (obj instanceof Boolean) {
                ptmt.setBoolean(index, (Boolean) obj);
            } else if (obj instanceof Short) {
                ptmt.setShort(index, (Short) obj);
            } else if (obj instanceof Byte) {
                ptmt.setByte(index, (Byte) obj);
            } else if (obj instanceof byte[]) {
                ptmt.setBytes(index, (byte[]) obj);
            } else if (obj == null) {
                ptmt.setObject(index, obj);
            } else {
                throw new SQLException(String.format("未知参数类型:%s", obj.getClass().getName()));
            }
        }
    }

    /**
     * 断开数据库连接
     */
    public void close() {
        if (mDBConn == null) return;

        try {
            if (mDBConn.isClosed()) return;
            mDBConn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 事务模式（独占模式）
     */
    public void beginTransaction() {
        if (mDBConn != null) {
            try {
                if (mDBConn.getAutoCommit()) {
                    mDBConn.setAutoCommit(false);
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    /**
     * 提交事务
     */
    public void commitTransaction() {
        if (mDBConn != null) {
            try {
                if (!mDBConn.getAutoCommit()) {
                    mDBConn.commit();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 回滚事务
     */
    public void rollBackTransaction() {
        if (mDBConn != null) {
            try {
                if (!mDBConn.getAutoCommit()) {
                    mDBConn.rollback();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 数据库是否已关闭
     *
     * @return
     */
    public boolean isClose() {
        try {
            return mDBConn.isClosed();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }


}
