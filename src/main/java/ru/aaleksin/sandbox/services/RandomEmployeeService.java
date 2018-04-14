package ru.aaleksin.sandbox.services;

import org.jfairy.Fairy;
import org.jfairy.producer.company.Company;
import org.jfairy.producer.payment.CreditCard;
import org.jfairy.producer.person.Person;
import ru.aaleksin.sandbox.entities.Employee;
import ru.aaleksin.sandbox.interfaces.IEmployeeService;

import java.util.Date;
import java.util.Random;

/**
 * Created by aaleksin on 03.04.2018.
 */
public class RandomEmployeeService implements IEmployeeService {
    public Employee getEmployee() {
        Employee employee = new Employee();
        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        Random random = new Random();

        employee.setFirstName(person.firstName());
        employee.setLastName(person.lastName());
        employee.setCommissionPct(random.nextDouble());
        employee.setDepartmentId(random.nextInt(200));
        employee.setEmail(person.companyEmail());
        employee.setHireDate(new Date());
        employee.setJobId(String.valueOf( random.nextInt(500)));
        employee.setPhone(person.telephoneNumber());
        employee.setSalary(random.nextDouble());
        employee.setManagerId(random.nextInt(25));

        return employee;
    }
}
