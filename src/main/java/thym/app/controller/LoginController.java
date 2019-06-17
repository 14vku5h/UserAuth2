package thym.app.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	 @RequestMapping(value = "/login", method = RequestMethod.GET)
	 public ModelAndView getLoginPage(@RequestParam Optional<String> error,Principal principal) {
		 if(principal!=null) {			  
			  return new ModelAndView("redirect:/profile");
		  }
	        return new ModelAndView("login", "error", error);
	    }
	
	@RequestMapping("/profile")
	public ModelAndView loginsuccess(ModelMap modelMap) {
		User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 GrantedAuthority grantedAuthority = user.getAuthorities().iterator().next();
		 String role = grantedAuthority.getAuthority();		 
		return new ModelAndView("redirect:/"+role.toLowerCase()+"/profile");
	}
}
