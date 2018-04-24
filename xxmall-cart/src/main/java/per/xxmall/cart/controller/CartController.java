package per.xxmall.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String createCart(@PathVariable("itemId") Long itemId){
		User user = UserThreadLocal.get();
		if(null == user){
			
		}else{
			cartService.createCart(itemId);
		}
		return "redirect:/cart/list.html";
	}
	
	@RequestMapping(value="list", method = RequestMethod.GET)
	public ModelAndView queryCartList(){
		ModelAndView mav = new ModelAndView("cart");
		User user = UserThreadLocal.get();
		if(null == user){
			
		}else{
			List<Cart> cartList = cartService.queryCartList();
			mav.addObject("cartList",cartList);
		}
		return mav;
	}
}
