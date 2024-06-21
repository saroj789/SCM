package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.services.UserService;

@Controller
public class PageController {

	@Autowired
	private UserService userService;

	@RequestMapping(path = "/")
	public String home(Model m) {
		return "home";
	}

	@RequestMapping(path = "/about")
	public String about() {
		return "about";
	}

	@RequestMapping(path = "/service")
	public String service() {
		return "service";
	}

	@GetMapping(path = "/contact")
	public String contact() {
		return "contact";
	}

	@GetMapping(path = "/signup")
	public String signup(Model model) {
		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);
		return "signup";
	}

	@GetMapping(path = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/do-register", method = RequestMethod.POST)
	public String ProcessRegister(@ModelAttribute UserForm userForm) {
		// fetch form data
		User user = new User();

		System.out.println("userForm  " + userForm);
		user.setAbout(userForm.getAbout());
		user.setEmail(userForm.getEmail());
		user.setName(userForm.getName());
		user.setPassword(userForm.getPassword());
		user.setPhoneNumber(userForm.getPhoneNumber());

		// validate form data
		// save to db
		// message= "registration successfull"
		// redirect to login
		User savedUser = userService.saveUser(user);
		System.out.println("user : " + savedUser);
		return "redirect:/signup";
	}

}
