package per.xxmall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import per.xxmall.common.service.RedisService;
import per.xxmall.web.service.ItemService;

@Controller
@RequestMapping("/item/cache")
public class ItemCacheController {

	@Autowired
	private RedisService redisService;
	
	@RequestMapping(value = "/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> delItemCache(@PathVariable("itemId")Long itemId){
		try {
			String key = ItemService.ITEM_KEY+itemId;
			redisService.del(key);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
