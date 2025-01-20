package in.sh.main.controllers;

import in.sh.main.entities.Course;
import in.sh.main.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private CourseService  courseService;
    @GetMapping("/adminLogin")
    public String openAdminLoginPage(){
        return "admin-login";
    }
    @PostMapping("/adminLoginForm")
    public String adminLoginForm(@RequestParam("adminemail") String aemail, @RequestParam("adminpass") String apass , Model model){
        if (aemail.equals("admin@gmail.com") && apass.equals("admin123")){
            return "admin-profile";
        }
        else {
            model.addAttribute("errorMsg" , "Oops! Incorrect email or password.");
            return "admin-login";
        }
    }
    @GetMapping("/adminProfile")
    public String openAdminProfilePage(){
        return "admin-profile";
    }
    @GetMapping("/courseManagement")
    public String openCourseManagementPage( Model model){
       List<Course> courseList= courseService.getAllCourseDetails();
       model.addAttribute("courseList",courseList);
        return "course-management";
    }
}
