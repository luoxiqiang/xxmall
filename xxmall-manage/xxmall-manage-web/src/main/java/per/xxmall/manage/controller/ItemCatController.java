package per.xxmall.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import per.xxmall.manage.pojo.ItemCat;
import per.xxmall.manage.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(
			@RequestParam(value="id",defaultValue="0") Long parentId){
		try {
			ItemCat record = new ItemCat();
			record.setParentId(parentId);
			List<ItemCat> list = itemCatService.queryListByWhere(record);
			if(list == null ||list.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(value = "/{itemCatId}", method = RequestMethod.GET)
	public ResponseEntity<ItemCat> getItemCatName(@PathVariable("itemCatId") Long itemCatId) {
		ItemCat itemCat = itemCatService.queryById(itemCatId);
		if(itemCat == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(itemCat);
	}
}
