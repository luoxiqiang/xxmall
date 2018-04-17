package per.xxmall.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import per.xxmall.manage.pojo.ItemParamItem;
import per.xxmall.manage.service.ItemParamItemService;

@Controller
@RequestMapping("/item/param/item")
public class ItemParamItemController {
	
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public ResponseEntity<ItemParamItem> qureryItemPramItemById(@PathVariable("itemId") Long itemId){
		try {
			ItemParamItem itemParamItem = new ItemParamItem();
			itemParamItem.setItemId(itemId);
			ItemParamItem paramItem = itemParamItemService.queryOne(itemParamItem);
			if(paramItem == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			return ResponseEntity.ok(paramItem);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
