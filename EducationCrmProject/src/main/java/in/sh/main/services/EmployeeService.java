package in.sh.main.services;

import in.sh.main.entities.Employee;
import in.sh.main.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService
{
    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Employee> getAllEmployeeDetailsByPagination(Pageable pageable)
    {
        return employeeRepository.findAll(pageable);
    }

    public void addEmployee(Employee employee)
    {
        employeeRepository.save(employee);
    }

    public Employee getEmployeeDetails(String employeeEmail)
    {
        return employeeRepository.findByEmail(employeeEmail);
    }

    public void updateEmployeeDetails(Employee employee)
    {
        employeeRepository.save(employee);
    }

    public void deleteEmployeeDetails(String employeeEmail)
    {
        Employee employee = employeeRepository.findByEmail(employeeEmail);
        if(employee != null)
        {
            employeeRepository.delete(employee);
        }
        else
        {
            throw new RuntimeException("Employee not found with email : "+employeeEmail);
        }
    }
}