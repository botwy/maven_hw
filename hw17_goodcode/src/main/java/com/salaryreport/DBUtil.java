package com.salaryreport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Отдельная утилита для работы с базой данных
 */
public class DBUtil {

    /**
     * статический метод для получения из базы данных информации по зарплате сотрудников
     * @param params ParametersByDepartment
     * @return HashMap<String,Double>
     */
    public static HashMap<String,Double> getNameSalaryReport(ParametersByDepartment params) {
        HashMap<String,Double> map = new HashMap<>();
        try {
            // prepare statement with sql
            PreparedStatement ps = params.getConnection().prepareStatement("select emp.id as emp_id, emp.name as amp_name, sum(salary) as salary from employee emp left join" +
                    "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and" +
                    " sp.date >= ? and sp.date <= ? group by emp.id, emp.name");
            // inject parameters to sql
            ps.setString(0, params.getDepartmentId());
            ps.setDate(1, new java.sql.Date(params.getDateFrom().toEpochDay()));
            ps.setDate(2, new java.sql.Date(params.getDateTo().toEpochDay()));
            // execute query and get the results
            ResultSet results = ps.executeQuery();

            while (results.next())
            map.put(results.getString("emp_name"),results.getDouble("salary"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
return map;
    }
}
