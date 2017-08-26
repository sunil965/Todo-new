package com.bridgeit.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
	 @RequestMapping("/home")
	    public String helloFacebook(Model model) {
		 System.out.println("Hello");
	      /*  if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
	            return "redirect:/connect/facebook";
	            return "redirect:/#!/login";
	        }
	        model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
	        PagedList<Post> feed = facebook.feedOperations().getFeed();
	        model.addAttribute("feed", feed);*/
		 	return "redirect:/#!/login";
	        //return "redirect:/#!/notes";
	        
	    }
}
