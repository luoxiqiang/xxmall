package per.xxmall.sso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import per.xxmall.common.utils.CookieUtils;
import per.xxmall.sso.pojo.User;
import per.xxmall.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	public static final String COOKIE_TOKEN="XX_TOKEN";

	@RequestMapping(value="/{pageName}", method = RequestMethod.GET)
	public String toRegister(@PathVariable("pageName") String pageName) {
		return pageName;
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
	@RequestMapping(value="/doLogin",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doLogin(String username,String password,
			HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<>();
		String token;
		try {
			token = userService.doLogin(username,password);
			if(token == null) {
				map.put("status",400);
			}else {
				map.put("status", 200);
				CookieUtils.setCookie(request, response, COOKIE_TOKEN, token);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status",500);
		}
		return map;
	}
	
	@RequestMapping(value="/query/{token}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkLogin(@PathVariable("token") String token){
		Map<String, Object> map = new HashMap<>();
		try {
			User user = userService.checkLogin(token);
			if(user == null) map.put("status", 400);
			else {
				map.put("status", 200);
				map.put("data", user);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", 500);
		}
		return map;
	}
}
