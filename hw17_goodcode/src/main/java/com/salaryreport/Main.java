package com.salaryreport;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;

public class Main {

    //Refactor
    //https://bitbucket.org/agoshkoviv/solid-homework/src/099989b0c762/src/main/java/ru/sbt/bit/ood/solid/homework/SalaryHtmlReportNotifier.java?at=master&fileviewer=file-view-default
    //generateAndSendHtmlSalaryReport

    /**
     * тот же функционал после рефакторинга с использованием новых классов и методов
     * @param connect
     */
    public static void generateAndSendHtmlSalaryReport(Connection connect) {
        String recipients="";
        Connection connection = connect;
        String departmentId="";

        ParametersByDepartment params = new ParametersByDepartment(connection,departmentId);
        params.setTitle("Employee");
        params.setSubject("Monthly department salary report");
        params.usePeriod(LocalDate.of(2017,9,1),LocalDate.now());

        HashMap<String,Double> nameSalaryMap = DBUtil.getNameSalaryReport(params);

        StringBuilder html = ReportUtil.generateHtmlStringDoubleReport(params.getTitle(),nameSalaryMap);

        ReportUtil.sendReportByEmail(html,recipients,params.getSubject());
    }

}
