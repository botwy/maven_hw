package com.spring_data;

        import java.net.URISyntaxException;
        import java.util.List;

        import org.springframework.context.support.ClassPathXmlApplicationContext;
        import repository.EmployeeRepository;


public class Main {

    public static void main(String[] args) throws URISyntaxException, Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("appContext.xml");

        EmployeeRepository repository = ctx.getBean(EmployeeRepository.class);
        Employee emp1 = new Employee("Richard", 32);
        Employee emp2 = new Employee("Satish", 30);
        Employee emp3 = new Employee("Priya", 16);
        Employee emp4 = new Employee("Rimi", 30);

        repository.save(emp1);
        repository.save(emp2);
        repository.save(emp3);
        repository.save(emp4);

        repository.findAll().forEach(System.out::println);

        List<Employee> empList = repository.findEmployeesBetweenAge(30, 32);
        System.out.println("Employee list: " + empList);
    }


}