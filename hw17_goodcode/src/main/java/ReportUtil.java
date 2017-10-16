import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

//Refactor
//https://bitbucket.org/agoshkoviv/solid-homework/src/099989b0c762/src/main/java/ru/sbt/bit/ood/solid/homework/SalaryHtmlReportNotifier.java?at=master&fileviewer=file-view-default
public class ReportUtil {


    public void generateAndSendHtmlSalaryReport(ParametersByDepartment params, String recipients) {
       StringBuilder html = generateHtmlSalaryReport(params);

       String subject = "Monthly department salary report";
       sendReportByEmail(html,recipients,subject);


    }

    public static StringBuilder generateHtmlSalaryReport(ParametersByDepartment params) {
        HashMap<String,Double> nameSalaryMap = DBUtil.getNameSalaryReport(params);
        // create a StringBuilder holding a resulting html
        StringBuilder resultingHtml = new StringBuilder();
        resultingHtml.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
        double totals = 0;
        for (String name:nameSalaryMap.keySet()) {
            // process each row of query results
            Double salary = nameSalaryMap.get(name);
            resultingHtml.append("<tr>"); // add row start tag
            resultingHtml.append("<td>").append(name).append("</td>"); // appending employee name
            resultingHtml.append("<td>").append(salary).append("</td>"); // appending employee salary for period
            resultingHtml.append("</tr>"); // add row end tag
            totals += salary; // add salary to totals
        }
        resultingHtml.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
        resultingHtml.append("</table></body></html>");

        return resultingHtml;
    }

    public static void sendReportByEmail(StringBuilder resultingHtml, String recipients, String subject) {
        try {
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
        }
    }
}