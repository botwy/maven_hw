package com.salaryreport;

//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
import java.util.HashMap;

//Refactor
//https://bitbucket.org/agoshkoviv/solid-homework/src/099989b0c762/src/main/java/ru/sbt/bit/ood/solid/homework/SalaryHtmlReportNotifier.java?at=master&fileviewer=file-view-default
public class ReportUtil {


    public void generateAndSendHtmlSalaryReport(ParametersByDepartment params, String recipients) {


    }

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