package com.gdu.ditestweb.cashbook1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
	@GetMapping("index")
	public String index(){
		return "index";
	}

}
