package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class OAuthAuthenticationSuccessHandle implements AuthenticationSuccessHandler {

	
	@Autowired
	private UserRepo userRepo;
	
	Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandle.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		logger.info("OAuthAuthenticationSuccessHandle");
		//response.sendRedirect("/home");

		DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
		
		/*
		logger.info("User -> "+ user.getName());
		user.getAttributes().forEach( (key, value) -> {
			logger.info(key +" -> "+ value);
		}); 
		logger.info("Authorities -> "+ user.getAuthorities().toString()); 
		*/
		
		
		String email = user.getAttribute("email").toString();
		String name = user.getAttribute("name").toString();
		String picture = user.getAttribute("picture").toString();
		
		//create user
		User user1 = new User();
		user1.setEmail(email);
		user1.setName(name);
		user1.setProfilePic(picture);
		user1.setProviderUserId(user.getName());
		
		user1.setUserId(UUID.randomUUID().toString());
		user1.setProvider(Providers.GOOGLE);
		user1.setEnabled(true);
		user1.setEmailVerified(true);
		user1.setRoleList(List.of(AppConstants.ROLE_USER));
		user1.setAbout("This account is created using google");
		
		User user2 = userRepo.findByEmail(email).orElse(null);
		
		if(user2 == null) {
			//save user
			User savedUser = userRepo.save(user1);
			logger.info("user saved successfully -> "+ savedUser);
		}else {
			logger.info("user already registered");
		}
		

		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
	}

}
