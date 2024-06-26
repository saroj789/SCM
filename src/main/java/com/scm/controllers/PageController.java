package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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
	public String ProcessRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult,
			HttpSession httpSession) {
		System.out.println("userForm  " + userForm);

		if (bindingResult.hasErrors()) {
			System.err.println("invalid form");
			return "signup";
		}

		// fetch form data
		User user = new User();
		user.setAbout(userForm.getAbout());
		user.setEmail(userForm.getEmail());
		user.setName(userForm.getName());
		user.setPassword(userForm.getPassword());
		user.setPhoneNumber(userForm.getPhoneNumber());
		user.setProfilePic("https://avatar.iran.liara.run/public");

		// validate form data
		// save to db
		User savedUser = userService.saveUser(user);

		// message set
		// message= "registration successfull"
		Message message = Message.builder().content("registration successfull").type(MessageType.green).build();
		httpSession.setAttribute("message", message);

		// redirect to login
		System.out.println("user : " + savedUser);
		return "redirect:/signup";
	}

}
