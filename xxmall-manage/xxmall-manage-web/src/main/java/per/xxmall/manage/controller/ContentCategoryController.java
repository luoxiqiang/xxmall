package per.xxmall.manage.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import per.xxmall.manage.pojo.ContentCategory;
import per.xxmall.manage.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCatService;
	//根据parentId查询
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ContentCategory>> queryCatById(
			@RequestParam(value = "id", defaultValue = "0") Long parentId){
		
		try {
			ContentCategory category = new ContentCategory();
			category.setParentId(parentId);
			List<ContentCategory> list = contentCatService.queryListByWhere(category);
			if(list == null || list.isEmpty())
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	//新增
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ContentCategory> addContentCat(ContentCategory contentCategory){
		try {
			ContentCategory contentCat = contentCatService.addContentCat(contentCategory);
			if(contentCat == null) 
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			return ResponseEntity.ok(contentCat);
		} catch (Exception e) {
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	//重命名
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> renameCat(ContentCategory contentCategory){
		try {
			Integer status = contentCatService.updateSelective(contentCategory);
			if (status == 0)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	//删除
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> delCat(ContentCategory contentCategory){
		try {
			List<Object> list = new ArrayList<>();
			list.add(contentCategory.getId());
			contentCatService.delCat(contentCategory, list);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} catch (Exception e) {
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
