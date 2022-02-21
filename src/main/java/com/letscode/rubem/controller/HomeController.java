package com.letscode.rubem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.letscode.rubem.model.About;

@RestController
@RequestMapping({ "/" })
public class HomeController {

	@GetMapping
	public String test() { 
		return "woking";
	}

	@GetMapping("/about")
	@ResponseStatus(HttpStatus.OK)
	public About getAbout() {
		return new About("Rubem Oliota", "Challenger for lets code");
	}

	
}
