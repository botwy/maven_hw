package com.spring_data;

        import java.net.URISyntaxException;
        import java.util.List;

        import javax.sql.DataSource;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.context.support.ClassPathXmlApplicationContext;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.PageRequest;
        import org.springframework.data.domain.Pageable;
        import org.springframework.data.domain.Sort.Direction;
        import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
        import org.springframework.transaction.annotation.Transactional;
        import repository.EmployeeRepository2;



public class SpringDataJpaExampleUsingAnnotation {

    public static void main(String[] args) throws URISyntaxException, Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("appContext.xml");

        EmployeeRepository2 employeeRepository = ctx.getBean(EmployeeRepository2.class);
        Employee emp1 = new Employee("Richard", 32);
        Employee emp2 = new Employee("Satish", 30);
        Employee emp3 = new Employee("Priya", 16);
        Employee emp4 = new Employee("Rimi", 30);

        employeeRepository.save(emp1);
        employeeRepository.save(emp2);
        employeeRepository.save(emp3);
        employeeRepository.save(emp4);


    }


}