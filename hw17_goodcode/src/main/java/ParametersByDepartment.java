import java.sql.Connection;
import java.time.LocalDate;

public class ParametersByDepartment {
    private final Connection connection;
    private final String departmentId;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    public ParametersByDepartment(Connection connection, String departmentId, LocalDate dateFrom, LocalDate dateTo) {
        this.connection = connection;
        this.departmentId = departmentId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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
}
