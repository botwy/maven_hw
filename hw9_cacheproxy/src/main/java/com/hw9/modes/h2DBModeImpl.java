package com.hw9.modes;


import com.hw9.CacheHandler;
import com.hw9.exception.DbCacheException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;


/**
 * Если есть аннотация Cachable и её поле persistent true, то кеширование осуществляем через базу данных h2
 */
public class h2DBModeImpl extends AbstractMode {
    static final String CONNECT_URL = "jdbc:h2:tcp://localhost/~/test";

    @Override
    public Object exec(CacheHandler context, Method method, Object[] args) throws DbCacheException, SQLException, IllegalAccessException, InvocationTargetException {
        Object result = null;
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Get driver exception");
            e.printStackTrace();
            throw new DbCacheException("Get driver exception", e);
        }
        try (Connection connection = DriverManager.getConnection(CONNECT_URL, "sa", null);
             Statement statement = connection.createStatement();) {


            StringBuilder multiplekey=new StringBuilder();
            multiplekey.append(method.getName());
            for (Object obj_arg:args)
                multiplekey.append("_").append(obj_arg.toString());

            ResultSet resultSet = statement.executeQuery("SELECT * FROM HW9_DATA.CACHE WHERE multiplekey='"+multiplekey+"'");
            int i = 0;

            while (resultSet.next()) {
                i++;
                if (i > 1) throw new DbCacheException("More than one row");
                result = resultSet.getString("result");
            }

            if (resultSet!=null) resultSet.close();

            if (result==null) {
                result = method.invoke(context.getDelegate(),args);
                statement.execute("INSERT INTO HW9_DATA.CACHE (multiplekey,result) values ('"+multiplekey+"', '"+result+"')");
            }
            else{result=Double.valueOf(result.toString());}

            return result;

        }  catch (SQLException e) {
            e.printStackTrace();
            throw new DbCacheException("Access cache database error", e);
        }

    }
}
