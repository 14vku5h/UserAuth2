package thym.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService {

	 @Autowired
  private JavaMailSender mailSender;

  
  @Async
  public void sendEmail(SimpleMailMessage email) {
	  System.out.println("Sending email...");
	  mailSender.send(email);
	  System.out.println(".................done");
  }
}
