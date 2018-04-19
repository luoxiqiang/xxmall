package per.xxmall.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import per.xxmall.web.pojo.Item;
import per.xxmall.web.pojo.Order;
import per.xxmall.web.service.ItemService;
import per.xxmall.web.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public ModelAndView toOrder(@PathVariable("itemId")Long itemId){
		ModelAndView mav = new ModelAndView("order");
		Item item = itemService.queryItemById(itemId);
		if(item != null) mav.addObject("item",item);
		return mav;
	}
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> submitOrder(Order order){
		Map<String,Object> result = new HashMap<String, Object>();
		String orderId =  orderService.createOrder(order);
		if(StringUtils.isEmpty(orderId)) result.put("status", 0); 
		else{
			result.put("status", 200);
			result.put("data", orderId);
		}
		return result;
	}
	
	@RequestMapping(value="/success", method = RequestMethod.GET)
	public ModelAndView success(String id) {
		ModelAndView mav = new ModelAndView("success");
		Order order = orderService.queryOrderById(id);
		mav.addObject("order",order);
		mav.addObject("date", new DateTime().plusDays(2).toString("yy年MM月dd天"));
		return mav;
	}
	
}
