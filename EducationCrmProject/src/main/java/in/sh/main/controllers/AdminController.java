package in.sh.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @GetMapping("/adminLogin")
    public String openAdminLoginPage(){
        return "admin-login";
    }
    @PostMapping("/adminLoginForm")
    public String adminLoginForm(@RequestParam("adminemail") String aemail, @RequestParam("adminpass") String apass , Model model){
        if (aemail.equals("admin@gmail.com") && apass.equals("admin123")){
            model.addAttribute("errorMsg" , "Invalid email id or password");
            return "admin-profile";
        }
        else {
            return "admin-login";
        }

    }
}
