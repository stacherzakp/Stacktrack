package com.staszkox.stacktrack.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController
{
	@GetMapping(value="/")
	public String start() 
	{
		return "index.html";
	}
}
