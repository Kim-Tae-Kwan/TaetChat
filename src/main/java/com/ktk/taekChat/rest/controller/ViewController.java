package com.ktk.taekChat.rest.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktk.taekChat.rest.model.dto.MemberDto;
import com.ktk.taekChat.rest.model.dto.SignupRequestDto;
import com.ktk.taekChat.rest.service.AuthService;
import com.ktk.taekChat.rest.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ViewController {
	
	private final AuthService authService;
	private final MemberService memberService;
	
	@GetMapping("/")
	public String index(@AuthenticationPrincipal User user, Model model) throws Exception {
		
		MemberDto member = memberService.getMemberById(Long.valueOf(user.getUsername()));
		model.addAttribute("member", member);
		
		return "index";
	}
	
	@GetMapping("/login")
	public ModelAndView login(@RequestParam(required = false) String error, ModelAndView mav) throws Exception {
		String viewName = "";
		
		if(authService.getCurrentMember() == null) {
			viewName = "login";
			mav.addObject("error", error);
		}else {
			viewName = "redirect:/";
		}
		
		mav.setViewName(viewName);
		return mav;
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	@PostMapping("/signup")
	public ModelAndView signup(SignupRequestDto signupRequestDto, ModelAndView mav) {
		try {
			authService.signup(signupRequestDto);
			mav.setViewName("redirect:/login");
		}catch(Exception ex) {
			mav.setViewName("redirect:/signup?error=duplicate");
		}
		
		return mav;
	}
}
