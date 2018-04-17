package per.xxmall.manage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;

import per.xxmall.common.bean.EasyUIResult;
import per.xxmall.manage.pojo.Item;
import per.xxmall.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> addItem(Item item,String desc,String itemParams){
		try {
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("正在新增商品:{}",item);
			}
			if(StringUtils.isEmpty(desc)||item.getTitle() == "") {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			Boolean status = itemService.itemsave(item, desc,itemParams);
			if(!status) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			LOGGER.error("添加商品: ["+item.getTitle()+"] 失败",e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIResult> getItemList(
			@RequestParam(value = "page",defaultValue = "1") Integer page
			,@RequestParam(value = "rows", defaultValue = "30")Integer rows){
		try {
			PageInfo<Item> itemList = itemService.getItemList(page,rows);
			EasyUIResult easyUIResult = new EasyUIResult(itemList.getTotal(), itemList.getList());
			return ResponseEntity.ok(easyUIResult);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);	
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item,String desc,String itemParams){
		Boolean bool = itemService.updatItem(item,desc,itemParams);
		if(bool) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
}
