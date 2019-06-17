package thym.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import thym.app.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/profile")
	public String userProfile(ModelMap map,Principal principal) {
		 map.addAttribute("loggedInUser",userService.findByEmail(principal.getName()));
		
		return "profile_admin";
	}
}
