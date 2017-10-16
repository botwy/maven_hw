package com.salaryreport;

import java.sql.Connection;
import java.time.LocalDate;

public class ParametersByDepartment {
    private final Connection connection;
    private final String departmentId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String title;
    private String subject;


    public ParametersByDepartment(Connection connection, String departmentId) {
        this.connection = connection;
        this.departmentId = departmentId;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void usePeriod (LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom=dateFrom;
        this.dateTo = dateTo;
    }
}
