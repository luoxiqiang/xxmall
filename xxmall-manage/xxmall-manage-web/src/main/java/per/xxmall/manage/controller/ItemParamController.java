package per.xxmall.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import per.xxmall.manage.pojo.ItemParam;
import per.xxmall.manage.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping(value = "/{itemCatId}", method = RequestMethod.GET)
	public ResponseEntity<ItemParam> queryByItemCatId(
			@PathVariable("itemCatId") Long itemCatId){
		try {
			ItemParam itemParam = new ItemParam();
			itemParam.setItemCatId(itemCatId);
			ItemParam one = itemParamService.queryOne(itemParam);
			if(one == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			return ResponseEntity.ok(one);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(value = "/{itemCatId}", method = RequestMethod.POST)
	public ResponseEntity<Void> addItemParam(
			@PathVariable("itemCatId") Long itemCatId,String paramData){
		try {
			ItemParam itemParam = new ItemParam();
			itemParam.setItemCatId(itemCatId);
			itemParam.setParamData(paramData);
			Integer save = itemParamService.save(itemParam);
			if(save == 0) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
