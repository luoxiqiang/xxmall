package per.xxmall.manage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import per.xxmall.manage.pojo.ContentCategory;

@Service
public class ContentCategoryService extends BaseService<ContentCategory>{

	public ContentCategory addContentCat(ContentCategory contentCategory) {
		contentCategory.setId(null);
		contentCategory.setIsParent(false);
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(1);
		Integer save = save(contentCategory);
		ContentCategory parent = queryById(contentCategory.getParentId());
		if(!parent.getIsParent()) {
			parent.setIsParent(true);
			Integer integer = updateSelective(parent);
			if(save == 1 && integer == 1)
				return contentCategory;
		}
		if(save == 1)
			return contentCategory;
		return null;
	}

	public void delCat(ContentCategory cat, List<Object> list) {
		querySubCat(cat.getParentId(),list);
		deleteByIds(ContentCategory.class, "id", list);
		//查询父节点是否还有字节点
		ContentCategory record = new ContentCategory();
		record.setParentId(cat.getParentId());
		List<ContentCategory> sonList = queryListByWhere(record);
		if(sonList == null || sonList.isEmpty()) {
			ContentCategory cate = new ContentCategory();
			cate.setId(cat.getParentId());
			cate.setIsParent(false);
			updateSelective(cate);
		}
	}
	
	//获取所有子节点
	private void querySubCat(Long parentId,List<Object> list){
		ContentCategory category = new ContentCategory();
		category.setParentId(parentId);
		List<ContentCategory> queryList = queryListByWhere(category);
		for(ContentCategory contentCat:queryList) {
			list.add(contentCat.getId());
			if(contentCat.getIsParent()) {
				querySubCat(contentCat.getParentId(),list);
			}
		}
	}

}
