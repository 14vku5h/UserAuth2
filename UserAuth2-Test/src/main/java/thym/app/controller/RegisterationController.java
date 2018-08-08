package thym.app.controller;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

import thym.app.model.User;
import thym.app.model.UserCredential;
import thym.app.service.EmailService;
import thym.app.service.UserService;

@Controller
public class RegisterationController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;	
	@Autowired
	private EmailService emailService;
	

	@RequestMapping(value="/register", method = RequestMethod.GET)
	public ModelAndView showRegistrationPage(ModelAndView mv,Principal principal){
		  if(principal!=null) {
			  mv.setViewName("redirect:/profile");
			  return mv;
		  }
		  
		mv.addObject("user", new User());
		mv.setViewName("register");
		return mv;
	}
	
	
	// Process form input data
		@RequestMapping(value = "/register", method = RequestMethod.POST)
		public String processRegistrationForm(ModelMap modelMap, @Valid User user,@Valid UserCredential cred, BindingResult bindingResult
				, HttpServletRequest request) {		
			
			// Lookup user in database by e-mail
			User userExists = userService.findByEmail(user.getEmail());
			
			System.out.println(userExists);
			
			if (userExists != null) {
				modelMap.addAttribute("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");		
				bindingResult.reject("email");
				 return "register";
			}			
			if (bindingResult.hasErrors()) { 
				return "register";
			} else { 
				user.setCredentials(cred);
				
			    user.setVerified(false);
			   // user.setOtp(1234);
			    user.setConfirmationToken(UUID.randomUUID().toString());
			    user.setCredentials(new UserCredential());
			    userService.saveUser(user);
					
				String appUrl = request.getScheme() + "://" + request.getServerName()+":"+request.getServerPort();
				
				SimpleMailMessage registrationEmail = new SimpleMailMessage();
				registrationEmail.setTo(user.getEmail());
				registrationEmail.setSubject("Registration Confirmation");
				registrationEmail.setText("Hi "+user.getFirstname()+", To confirm your e-mail address and continue registration, please  click to the belo link:\n"
						        + appUrl+"/confirm?token=" + user.getConfirmationToken());
				//registrationEmail.setFrom("noreply@domain.com");
				
				emailService.sendEmail(registrationEmail);
				
				modelMap.addAttribute("confirmationMessage", "A confirmation e-mail has been sent to " + user.getEmail());
				modelMap.addAttribute("email",user.getEmail());
				return "/register";
				
			}
		
		}
		// Process confirmation link
		@RequestMapping(value="/confirm", method = RequestMethod.GET)
		public ModelAndView showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {
				
			User user = userService.findByConfirmationToken(token);
				
			if (user == null) { // No token found in DB
				modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
			} else { // Token found
				modelAndView.addObject("confirmationToken", user.getConfirmationToken());
				modelAndView.addObject("setPassword",new UserCredential());
			}
				
			modelAndView.setViewName("confirm");
			return modelAndView;		
		}
		
		// Process confirmation link
		@RequestMapping(value="/confirm", method = RequestMethod.POST)
		public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult,@RequestParam Map requestParams, RedirectAttributes redir) {
					
			modelAndView.setViewName("confirm");
			
			Zxcvbn passwordCheck = new Zxcvbn();
			String p1= (String) requestParams.get("password");
			String p2= (String) requestParams.get("confirmPassword");
			Strength strength = passwordCheck.measure(p1);
			
		    	
			if (strength.getScore() < 3) {
				bindingResult.reject("password");
				
				redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");

				modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
				System.out.println(requestParams.get("token"));
				return modelAndView;
			}
			if(!p1.equals(p2)) {
				redir.addFlashAttribute("errorMessage", "Both passwords must be identical");
				modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
				System.out.println(requestParams.get("token"));
				return modelAndView;
			}
		
			// Find the user associated with the reset token
			User user = userService.findByConfirmationToken(requestParams.get("token").toString());

			// Set new password
			user.getCredentials().setPassword(bCryptPasswordEncoder.encode((CharSequence) requestParams.get("password")));
			//user.getCredentials().setPassword(requestParams.get("password").toString());

			// Set user to enabled
			user.setVerified(true);
			user.getCredentials().setActive(true);
			
			// Save user
			userService.saveUser(user);
			
			modelAndView.addObject("successMessage", "Your password has been set!");
			modelAndView.addObject("role",user.getCredentials().getRole().toString().toLowerCase());
			return modelAndView;		
		}
}
