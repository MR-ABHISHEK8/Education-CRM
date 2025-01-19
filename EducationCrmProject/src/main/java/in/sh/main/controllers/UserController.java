package in.sh.main.controllers;

import in.sh.main.entities.Course;
import in.sh.main.entities.User;
import in.sh.main.repositories.UserRepository;
import in.sh.main.services.CourseService;
import in.sh.main.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@SessionAttributes("sessionUser")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseService courseService;


    @GetMapping({"/", "/index"})
    public String openProfilePage(Model model) {
        List<Course> coursesList = courseService.getAllCourseDetails();
        model.addAttribute("coursesList", coursesList);
        return "index";
    }


    /*------------------------------ Register start --------------------------------------*/

    @GetMapping("/register")
    public String openRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/regForm")
    public String handleRegForm(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        } else {
            try {
                userService.registerUserService(user);

                model.addAttribute("successMsg", "Registered successfully");
                //  return "redirect:/register";  error occurred then use this
                return "register";
            } catch (Exception e) {
                e.printStackTrace();

                model.addAttribute("errorMsg", "Registered Failed");
                return "error";
            }

        }
    }

    /*------------------------------ Register End--------------------------------------*/



    /*------------------------------ Login start --------------------------------------*/

    @GetMapping("/login") //url link
    public String openLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/loginForm")
    public String handleLoginForm(@ModelAttribute("user") User user, Model model) {
        boolean isAuthenticated = userService.loginUserService(user.getEmail(), user.getPassword());
        if (isAuthenticated) {
            User authenticatedUser = userRepository.findByEmail(user.getEmail());
            model.addAttribute("sessionUser", authenticatedUser);

            return "user-profile";
        } else {
            model.addAttribute("errorMsg", "Please, check your email and password.");
            return "login";
        }

    }
    /*------------------------------ Login End --------------------------------------*/

    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "login";
    }


    @GetMapping("/userProfile")
    public String openUserProfile() {
        return "user-profile";
    }

    @GetMapping("/myCourses")
    public String myCoursesPage() {
        return "my-courses";
    }
}