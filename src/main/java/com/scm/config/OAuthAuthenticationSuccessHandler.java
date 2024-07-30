package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	
	@Autowired
	private UserRepo userRepo;
	
	Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		logger.info("OAuthAuthenticationSuccessHandler");
		//response.sendRedirect("/home");

		DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
		
		oauthUser.getAttributes().forEach( (key, value) -> {
			logger.info(key +" -> "+ value);
		}); 
		logger.info("Authorities -> "+ oauthUser.getAuthorities().toString()); 
		
		
		var oauth2AuthenticationToken= (OAuth2AuthenticationToken) authentication;
		
		String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
		logger.info("authorizedClientRegistrationId -> "+ authorizedClientRegistrationId ); 

		
		User user1 = new User();
		user1.setUserId(UUID.randomUUID().toString());
		user1.setEnabled(true);
		user1.setEmailVerified(true);
		user1.setRoleList(List.of(AppConstants.ROLE_USER));
		
		User user2 = null;

		if("google".equalsIgnoreCase(authorizedClientRegistrationId)) {
			String email = oauthUser.getAttribute("email").toString();
			String name = oauthUser.getAttribute("name").toString();
			String picture = oauthUser.getAttribute("picture").toString();
			
			user1.setEmail(email);
			user1.setName(name);
			user1.setProfilePic(picture);
			user1.setProviderUserId(oauthUser.getName());
			user1.setProvider(Providers.GOOGLE);
			user1.setAbout("This account is created using google");
		}else if ("github".equalsIgnoreCase(authorizedClientRegistrationId)) {
			String email = oauthUser.getAttribute("email");  // if email is private it will be null
			if(email != null) {
				user1.setEmailVerified(true);
			}else {
				email = oauthUser.getAttribute("login").toString() + "@gmail.com";
				user1.setEmailVerified(false);
			}
			
			String picture = oauthUser.getAttribute("avatar_url").toString();
			String name = oauthUser.getAttribute("login").toString();
			
			user1.setEmail(email);
			user1.setName(name);
			user1.setProfilePic(picture);
			user1.setProviderUserId(oauthUser.getName());
			
			user1.setProvider(Providers.GULHUB);
			user1.setAbout("This account is created using github");
		}
		else {
			logger.info("OAuthAuthenticationSuccessHandler : Unknown provider -> "+authorizedClientRegistrationId);	
		}
		

		user2 = userRepo.findByEmail(user1.getEmail()).orElse(null);
		
		if(user2 == null) {
			User savedUser = userRepo.save(user1);
			logger.info("user saved successfully -> "+ savedUser);
		}else {
			logger.info("user already registered");
		}
		

		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
	}

}
