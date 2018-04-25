package per.xxmall.cart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import per.xxmall.cart.bean.User;
import per.xxmall.cart.bean.UserThreadLocal;
import per.xxmall.cart.pojo.Cart;
import per.xxmall.cart.service.CartService;

@Controller
@RequestMapping("cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping(value="{itemId}", method = RequestMethod.GET)
	public String createCart(@PathVariable("itemId") Long itemId,HttpServletRequest request,HttpServletResponse response){
		User user = UserThreadLocal.get();
		if(null == user){
			cartService.createCookieCart(itemId,request,response);
		}else{
			cartService.createCart(itemId);
		}
		return "redirect:/cart/list.html";
	}
	
	@RequestMapping(value="list", method = RequestMethod.GET)
	public ModelAndView queryCartList(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("cart");
		User user = UserThreadLocal.get();
		List<Cart> cartList=null;
		if(null == user){
			cartList = cartService.getCookieList(request);
		}else{
			cartList = cartService.queryCartList();
		}
		mav.addObject("cartList",cartList);
		return mav;
	}
	
	@RequestMapping(value="update/num/{itemId}/{num}", method = RequestMethod.POST)
	public ResponseEntity<Void> updateNum(@PathVariable("itemId")Long itemId,@PathVariable("num")Integer num
			,HttpServletRequest request,HttpServletResponse response){
		User user = UserThreadLocal.get();
		if(null == user){
			cartService.updateCookieNum(itemId,request,num,response);
		}else{
			cartService.updateNum(itemId,num);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}







