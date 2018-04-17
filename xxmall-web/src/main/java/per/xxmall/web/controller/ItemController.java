package per.xxmall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import per.xxmall.manage.pojo.ItemDesc;
import per.xxmall.web.pojo.Item;
import per.xxmall.web.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value = "/{itemId}", method =RequestMethod.GET)
	public ModelAndView getItem(@PathVariable("itemId") Long itemId) {
		ModelAndView mav = new ModelAndView("item");
		Item item = itemService.queryItemById(itemId);
		mav.addObject("item",item);
		ItemDesc itemDesc = itemService.queryItemDescById(itemId);
		mav.addObject("itemDesc",itemDesc);
		return mav;
	}
}
