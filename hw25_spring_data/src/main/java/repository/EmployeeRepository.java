package com.spring_data;

import java.util.List;

import com.spring_data.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {

        List<Employee> findEmployeesByAge(int age);
        List<Employee> findEmployeesByName(String name);

        @Query("select emp from Employee emp where emp.age >= ?1 and emp.age <= ?2")
        List<Employee> findEmployeesBetweenAge(int from, int to);

        Page<Employee> findEmployeesByAgeGreaterThan(int age, Pageable pageable);

}
