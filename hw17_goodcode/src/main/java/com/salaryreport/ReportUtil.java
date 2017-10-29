package com.salaryreport;

//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.HashMap;

//Refactor
//https://bitbucket.org/agoshkoviv/solid-homework/src/099989b0c762/src/main/java/ru/sbt/bit/ood/solid/homework/SalaryHtmlReportNotifier.java?at=master&fileviewer=file-view-default

/**
 * Универсальная утилита для генерации и отправки отчетов
 */
public class ReportUtil {

   /* //Deprecated
    public class SalaryHtmlReportNotifier {
    private Connection connection;

    public SalaryHtmlReportNotifier(Connection databaseConnection) {
        this.connection = databaseConnection;
    }*/


    @Deprecated
    public void generateAndSendHtmlSalaryReport(String departmentId, LocalDate dateFrom, LocalDate dateTo, String recipients) {
        /*try {
            // prepare statement with sql
            PreparedStatement ps = connection.prepareStatement("select emp.id as emp_id, emp.name as amp_name, sum(salary) as salary from employee emp left join" +
                    "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and" +
                    " sp.date >= ? and sp.date <= ? group by emp.id, emp.name");
            // inject parameters to sql
            ps.setString(0, departmentId);
            ps.setDate(1, new java.sql.Date(dateFrom.toEpochDay()));
            ps.setDate(2, new java.sql.Date(dateTo.toEpochDay()));
            // execute query and get the results
            ResultSet results = ps.executeQuery();
            // create a StringBuilder holding a resulting html
            StringBuilder resultingHtml = new StringBuilder();
            resultingHtml.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
            double totals = 0;
            while (results.next()) {
                // process each row of query results
                resultingHtml.append("<tr>"); // add row start tag
                resultingHtml.append("<td>").append(results.getString("emp_name")).append("</td>"); // appending employee name
                resultingHtml.append("<td>").append(results.getDouble("salary")).append("</td>"); // appending employee salary for period
                resultingHtml.append("</tr>"); // add row end tag
                totals += results.getDouble("salary"); // add salary to totals
            }
            resultingHtml.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
            resultingHtml.append("</table></body></html>");
            // now when the report is built we need to send it to the recipients list
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            // we're going to use google mail to send this message
            mailSender.setHost("mail.google.com");
            // construct the message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipients);
            // setting message text, last parameter 'true' says that it is HTML format
            helper.setText(resultingHtml.toString(), true);
            helper.setSubject("Monthly department salary report");
            // send the message
            mailSender.send(message);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }*/
    }


    public void generateAndSendHtmlSalaryReport(ParametersByDepartment params, String recipients) {


    }

    /**
     * статический универсальный метод (не только для зарплатного отчета)
     * Генерирует любой отчет в html формате на основе входящих данных (HashMap)
     * @param title
     * @param stringDoubleMap
     * @return html-код
     */
    public static StringBuilder generateHtmlStringDoubleReport(String title, HashMap<String,Double> stringDoubleMap) {
        // create a StringBuilder holding a resulting html
        StringBuilder resultingHtml = new StringBuilder();
        resultingHtml.append("<html><body><table><tr><td>").append(title).append("</td><td>Salary</td></tr>");
        double totals = 0;
        for (String str:stringDoubleMap.keySet()) {
            // process each row of query results
            Double dbl = stringDoubleMap.get(str);
            resultingHtml.append("<tr>"); // add row start tag
            resultingHtml.append("<td>").append(str).append("</td>"); // appending str
            resultingHtml.append("<td>").append(dbl).append("</td>"); // appending dbl for period
            resultingHtml.append("</tr>"); // add row end tag
            totals += dbl; // add dbl to totals
        }
        resultingHtml.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
        resultingHtml.append("</table></body></html>");

        return resultingHtml;
    }

    /**
     * статический универсальный метод (не только для зарплатного отчета)
     * Отправляет любой отчет в формате html по e-mail
     * @param resultingHtml
     * @param recipients получатель письма
     * @param subject тема письма
     */
    public static void sendReportByEmail(StringBuilder resultingHtml, String recipients, String subject) {
       /* try {
            // now when the report is built we need to send it to the recipients list
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            // we're going to use google mail to send this message
            mailSender.setHost("mail.google.com");
            // construct the message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipients);
            // setting message text, last parameter 'true' says that it is HTML format
            helper.setText(resultingHtml.toString(), true);
            helper.setSubject(subject);
            // send the message
            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }*/
    }
}