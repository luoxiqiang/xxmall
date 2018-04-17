package per.xxmall.sso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.xxmall.sso.pojo.User;
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
		try {
			Boolean bool= userService.check(param,type);
			if(bool ==null){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			//为配合前端逻辑
			return ResponseEntity.ok(!bool);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> register(@Valid User user,BindingResult br){
		Map<String,Object> map = new HashMap<>();
		if(br.hasErrors()){
			List<String> errors = new ArrayList<>();
			List<ObjectError> allErrors = br.getAllErrors();
			for (ObjectError error : allErrors) {
				errors.add(error.getDefaultMessage());
			}
			map.put("data",StringUtils.join(errors,"|"));
			return map;
		}
		Boolean bool = userService.register(user);
		
		if(bool){
			map.put("status", "200");
		}else{
			map.put("data", " ");
		}
		return map;
	}
	
}
