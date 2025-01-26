package in.sh.main.controllers;

import in.sh.main.entities.Employee;
import in.sh.main.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employeeManagement")
    public String openEmployeeManagementPage(Model model,
                                             @RequestParam(name="page", defaultValue = "0") int page,
                                             @RequestParam(name="size", defaultValue = "5") int size)
    {
        Pageable pageable = PageRequest.of(page, size);

        Page<Employee> employeePage = employeeService.getAllEmployeeDetailsByPagination(pageable);

        model.addAttribute("employeePage", employeePage);

        return "employee-management";
    }


    //---------------add employee starts-----------------------------

    @GetMapping("/addEmployee")
    public String openAddCoursePage(Model model)
    {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    @PostMapping("/addEmployeeForm")
    public String addEmployeeForm(@ModelAttribute("employee") Employee employee, Model model)
    {
        try
        {
            employeeService.addEmployee(employee);
            model.addAttribute("successMsg", "Employee added successfully");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            model.addAttribute("errorMsg", "Employee not added due to some error");
        }
        return "add-employee";
    }

    //---------------add employee ends-----------------------------
    @GetMapping("/editEmployee")
    public String openEditEmployeePage(@RequestParam("employeeEmail") String employeeEmail, Model model)
    {
        Employee employee = employeeService.getEmployeeDetails(employeeEmail);

        model.addAttribute("employee", employee);
        model.addAttribute("newEmployeeObj", new Employee());

        return "edit-employee";
    }

    @PostMapping("/updateEmployeeDetailsForm")
    public String updateEmployeeDetailsForm(@ModelAttribute("newEmployeeObj") Employee newEmployeeObj, RedirectAttributes redirectAttributes)
    {
        try
        {
            Employee oldEmployeeObj = employeeService.getEmployeeDetails(newEmployeeObj.getEmail());
            newEmployeeObj.setId(oldEmployeeObj.getId());

            employeeService.updateEmployeeDetails(newEmployeeObj);

            redirectAttributes.addFlashAttribute("successMsg", "Employee details updated successfully");
        }
        catch(Exception e)
        {
            redirectAttributes.addFlashAttribute("errorMsg", "Employee details not updated due to some error");
            e.printStackTrace();
        }

        return "redirect:/employeeManagement";
    }
    //---------------edit employee ends-----------------------------

    @GetMapping("/deleteEmployeeDetails")
    public String deleteEmployeeDetails(@RequestParam("employeeEmail") String employeeEmail, RedirectAttributes redirectAttributes)
    {
        try
        {
            employeeService.deleteEmployeeDetails(employeeEmail);
            redirectAttributes.addFlashAttribute("successMsg", "Employee deleted successfully");
        }
        catch(Exception e)
        {
            redirectAttributes.addFlashAttribute("errorMsg", "Employee not deleted due to some error");
            e.printStackTrace();
        }
        return "redirect:/employeeManagement";
    }


}
