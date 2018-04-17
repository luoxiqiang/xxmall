package per.xxmall.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import per.xxmall.manage.pojo.ItemDesc;
import per.xxmall.manage.service.ItemDescService;

@Controller
@RequestMapping("/item/desc")
public class ItemDescController {

	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public ResponseEntity<ItemDesc> getItemDesc(@PathVariable("itemId") Long itemId){
		try {
			ItemDesc desc = itemDescService.queryById(itemId);
			if(desc == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(desc);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
