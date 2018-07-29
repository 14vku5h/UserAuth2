package lkv.app.controller;

import java.security.Principal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lkv.app.model.UserCredential;
import lkv.app.model.Users;
import lkv.app.repository.UsersRepository;
import lkv.app.service.UsersService;
import lkv.app.zException.CustomeException;

@Controller
public class HomeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	@Autowired
    UsersRepository usrDao;
	@Autowired
	UsersService usrService;
	

	
	    @GetMapping("/")
	    String index(Principal principal) {
	        return principal != null ? "redirect:/user/dashboard" : "index";
	    }

	@GetMapping(value="/home")
	public String home(Model model, Principal principal) {
		if(principal !=null)
			return "redirect:/user/dashboard";
	    model.addAttribute("users",usrDao.findAll());	
		model.addAttribute("msg","Welcome to the home");
		return "home";
	}
	 @RequestMapping(value = "/login", method = RequestMethod.GET)
	 public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
	     LOGGER.debug("Getting login page, error={}", error);
	        return new ModelAndView("login", "error", error);
	    }
   
	 @PostMapping("/register")	
	 public String postSignUpPage(Users user,UserCredential cred,ModelMap map, BindingResult bindingResult,
				RedirectAttributes redirectAttributes) {
			LOGGER.debug("Processing Patient  ={}, bindingResult={}",user , bindingResult);
			if (bindingResult.hasErrors()) {
				return "redirect:/signup?error";
			}
			try {
				//cred.setUserName(user.getEmail());
				user.setUserCredential(cred);
				usrService.create(user);
				map.addAttribute(user.getEmail());
				redirectAttributes.addFlashAttribute("msg","Hi <strong>" +user.getFirstName() +" </strong> You have registered with us successfully");
				LOGGER.debug("your are able to save user");
			} catch (DataIntegrityViolationException e) {
				LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
				bindingResult.reject("email.exists", "Email already exists");
				return "redirect:/signup?userexists";
			} catch (CustomeException e) {
				bindingResult.reject(e.getCode(), e.getMessage());
				return "signup";
			}
			return "redirect:/login?sucess";
	 }
	 
	 @GetMapping("/register")
	 public String getSignUpPage( Model model, Principal principal) {
			if(principal !=null)
				return "redirect:/user/dashboard";
			return "signup";		 
	 }

}
