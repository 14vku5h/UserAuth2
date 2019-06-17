package thym.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import thym.app.service.CredentialService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{


	@Autowired
	CredentialService credService;
	@Bean
	    BCryptPasswordEncoder passwordEncoder() {
	    	return new BCryptPasswordEncoder();
	    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/","/login","/register","/confirm").permitAll()
			.antMatchers("/user/**").hasAuthority("USER")
			.antMatchers("/admin/**").hasAuthority("ADMIN")
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login")
			.defaultSuccessUrl("/profile");
		
		http.csrf().disable();
		//super.configure(http);
	}
	
	
	 @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(credService).passwordEncoder(passwordEncoder());
		super.configure(auth);
	}

	

}
