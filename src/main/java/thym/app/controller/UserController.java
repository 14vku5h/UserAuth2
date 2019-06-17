package thym.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import thym.app.model.User;
import thym.app.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/profile")
	public String userProfile(ModelMap map,Principal principal) {
		 map.addAttribute("loggedInUser",userService.findByEmail(principal.getName()));		
		return "profile_user";
	}
	
	@RequestMapping(value="/uploadImage",method=RequestMethod.POST)
	public String upload(@RequestParam("uploadFile") MultipartFile uploadfile, Principal princiapl) {
		User loggedInUser = userService.findByEmail(princiapl.getName());
		try {
			loggedInUser.setImage(uploadfile.getBytes());
			userService.updateUser(loggedInUser);
			}catch(Exception e) {
				System.out.println("Error occurred while uploading file :"+e.getMessage());
			}
		return "redirect:/user/profile";
	}
	
	/*@RequestMapping(value="/uploadImage",method=RequestMethod.POST)
	public String upload(@RequestParam("uploadFile") MultipartFile uploadfile, ModelMap modelMap) {
		User loggedInUser = (User) modelMap.get("loggedInUser");
		try {
			loggedInUser.setImage(uploadfile.getBytes());
			userService.updateUser(loggedInUser);
			}catch(Exception e) {
				System.out.println("Error occurred while uploading file"+e.getMessage());
			}
		return "redirect:/user/profile";
	}*/
}
