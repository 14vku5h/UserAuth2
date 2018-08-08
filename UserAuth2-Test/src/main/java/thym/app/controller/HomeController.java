package thym.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import thym.app.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	
	
}
