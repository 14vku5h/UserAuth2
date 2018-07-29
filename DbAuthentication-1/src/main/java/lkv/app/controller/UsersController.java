package lkv.app.controller;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import lkv.app.model.Role;
import lkv.app.model.Users;
import lkv.app.service.UsersService;


@Controller
@RequestMapping(value = "/user")
@SessionAttributes({"loggedInUser","userRole"})
public class UsersController {
	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
	@Autowired
	UsersService usrService;
	
	@RequestMapping("/dashboard")
	public String loginSuccess(ModelMap modelMap){	
		
		User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 GrantedAuthority grantedAuthority = user.getAuthorities().iterator().next();
		 
		 modelMap.addAttribute("userRole",grantedAuthority.getAuthority());
		 
		 if(Role.USER.toString().equals(grantedAuthority.getAuthority())){
			 Users loggedInUser  = usrService.getUserByEmail(user.getUsername());
			 if(loggedInUser.getImage() != null){
				 
				 String base64Str = Base64.getEncoder().encodeToString(loggedInUser.getImage());
				 loggedInUser.setBase64Image(base64Str);
			 }
			 modelMap.addAttribute("loggedInUser",loggedInUser);
		 }
		 
		return "dashboard";
	}
	
	@RequestMapping(value="/uploadImage",method=RequestMethod.POST)
	public String upload(@RequestParam("uploadFile") MultipartFile uploadfile, ModelMap modelMap) {
		Users loggedInUser = (Users) modelMap.get("loggedInUser");
		try {
			loggedInUser.setImage(uploadfile.getBytes());
			usrService.updateUser(loggedInUser);
			}catch(Exception e) {
				logger.error("Error occurred while uploading file{}",e.getMessage());
			}
		return "redirect:/user/dashboard";
	}
	
}
