package com.hw9;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;

public class h2DBModeImpl extends AbstractMode {
    static final String CONNECT_URL = "jdbc:h2:tcp://localhost/C:\\TEMP\\test.db";

    @Override
    public Object exec(CacheHandler context, Method method, Object[] args) throws DbCacheException, SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(CONNECT_URL, "su", null);
            statement = connection.createStatement();
            StringBuilder key=new StringBuilder();
            key.append(method.getName());
            for (Object obj_arg:args)
                key.append("_").append(obj_arg.toString());

            ResultSet resultSet = statement.executeQuery("SELECT * FROM DATA.CACHE WHERE key="+key);
            int i = 0;
            Object result = null;
            while (resultSet.next()) {
                i++;
                if (i > 1) throw new DbCacheException("More than one row");
                result = resultSet.getString("result");
            }

            if (resultSet!=null) resultSet.close();
            return result;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            statement.close();
            connection.close();
        }

        return null;
    }
}
