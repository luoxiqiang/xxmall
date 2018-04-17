package per.xxmall.manage.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;

import per.xxmall.manage.mapper.ItemParamItemMapper;
import per.xxmall.manage.pojo.ItemParamItem;

@Service
public class ItemParamItemService extends BaseService<ItemParamItem>{
	
	@Autowired
	private ItemParamItemMapper itemParamItemMapper;
	
	public Integer updateBiItemId(Long id, String itemParams) {
		ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setUpdated(new Date());
		itemParamItem.setParamData(itemParams);
		Example example = new Example(ItemParamItem.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("itemId", id);
		int i = itemParamItemMapper.updateByExampleSelective(itemParamItem, example);
		return i;
	}

}
