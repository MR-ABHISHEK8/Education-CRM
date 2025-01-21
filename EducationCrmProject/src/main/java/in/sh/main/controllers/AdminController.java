package in.sh.main.controllers;

import in.sh.main.entities.Course;
import in.sh.main.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class AdminController {

    private String UPLOAD_DIR ="src/main/resources/static/uploads/";
    private String IMAGE_URL="http://localhost:8080/uploads/";
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
    @GetMapping("/addCourse")
    public String openAddCoursePage(Model model){
        model.addAttribute("course",new Course());
        return "add-course";
    }

// ----------------   add course start-----------------------------
    @PostMapping("/addCourseForm")
    public String addCourseForm(@ModelAttribute("course") Course course,@RequestParam("courseImg") MultipartFile courseImg ,Model model){

        try{
             courseService.addCourse(course,courseImg);
             model.addAttribute("successMsg","Course added successfully");
         }

         catch (Exception e){
             e.printStackTrace();
             model.addAttribute("errorMsg","Course not added due to some error");
         }
        return "add-course";
    }
    // ---------------------add course end---------------------------------------

          //---------------Edit course start---------------------------
            @GetMapping("/editCourse")
            public String openEditCoursePage(@RequestParam("courseName") String courseName , Model model){
           Course course=  courseService.getCourseDetails(courseName);
                  model.addAttribute("course",course);
                  model.addAttribute("newCourseObj" ,new Course());
                return "edit-course";
           }
            @PostMapping("/updateCourseDetailsForm")
             public String updateCourseDetailsForm(@ModelAttribute("newCourseObj") Course newCourseObj , @RequestParam("courseImg") MultipartFile courseImg , RedirectAttributes redirectAttributes){
             try {
                 Course oldCourseObj= courseService.getCourseDetails(newCourseObj.getName());
                 newCourseObj.setId(oldCourseObj.getId());

                 if (!courseImg.isEmpty()){
                     String imgName= courseImg.getOriginalFilename();
                     Path imgPath= Paths.get(UPLOAD_DIR+imgName);
                     Files.write(imgPath,courseImg.getBytes());

                     String imgUrl=IMAGE_URL+imgName;
                     newCourseObj.setImageUrl(imgUrl);
                 }
                 else {
                     newCourseObj.setImageUrl(oldCourseObj.getImageUrl());
                 }

                 courseService.updateCourseDetails(newCourseObj);
                 redirectAttributes.addFlashAttribute("successMsg","Course details updated successfully");
             }
             catch (Exception e){
                 redirectAttributes.addFlashAttribute("errorMsg","Course details not updated due to some error");
                 e.printStackTrace();
             }

               return "redirect:/courseManagement";
            }

           //---------------Edit course end---------------------------

        //----------------delete course start---------------
    @GetMapping("/deleteCourseDetails")
    public String deleteCourseDetails(@RequestParam("courseName") String courseName ,RedirectAttributes redirectAttributes){
        try {

           courseService.deleteCourseDetails(courseName);
            redirectAttributes.addFlashAttribute("successMsg","Course deleted successfully");
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMsg","Course not deleted  due to some error");
            e.printStackTrace();
        }
        return "redirect:/courseManagement";
    }
}
