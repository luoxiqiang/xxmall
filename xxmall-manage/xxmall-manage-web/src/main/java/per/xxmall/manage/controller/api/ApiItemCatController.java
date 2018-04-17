package per.xxmall.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import per.xxmall.common.bean.ItemCatResult;
import per.xxmall.manage.service.ItemCatService;

@Controller
@RequestMapping("/api/item/cat")
public class ApiItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ItemCatResult> getItemCat() {
		try {
			ItemCatResult catResult = itemCatService.queryAllToTree();
			if(catResult == null) 
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			return ResponseEntity.ok(catResult);
		} catch (Exception e) {
			
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
