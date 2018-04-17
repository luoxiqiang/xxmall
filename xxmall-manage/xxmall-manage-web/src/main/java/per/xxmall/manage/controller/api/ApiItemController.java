package per.xxmall.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import per.xxmall.manage.pojo.Item;
import per.xxmall.manage.service.ItemService;

@Controller
@RequestMapping("/api/item")
public class ApiItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value = "/{itemId}", method =RequestMethod.GET)
	public ResponseEntity<Item> getItem(@PathVariable("itemId") Long itemId) {
		try {
			Item item = itemService.queryById(itemId);
			if (item == null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			return ResponseEntity.ok(item);
		} catch (Exception e) {
			
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
