package per.xxmall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import per.xxmall.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String toRegister() {
		return "register";
	}
	
	@RequestMapping(value = "/check/{param}/{type}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> chechData(@PathVariable("param") String param,
			@PathVariable("type") Integer type){
		Boolean bool= userService.check(param,type);
		return null;
	}
}
