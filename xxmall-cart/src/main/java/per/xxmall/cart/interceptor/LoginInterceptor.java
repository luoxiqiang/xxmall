package per.xxmall.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import per.xxmall.cart.bean.User;
import per.xxmall.cart.bean.UserThreadLocal;
import per.xxmall.cart.service.UserService;
import per.xxmall.common.utils.CookieUtils;

public class LoginInterceptor implements HandlerInterceptor{
	
	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = CookieUtils.getCookieValue(request, "XX_TOKEN");
		if(StringUtils.isEmpty(token)) {
			return true;
		}
		User user = userService.queryUser(token);
		if(user == null) {
			return true;
		}
		UserThreadLocal.set(user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
