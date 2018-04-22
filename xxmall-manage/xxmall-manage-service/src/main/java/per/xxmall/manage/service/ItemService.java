package per.xxmall.manage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import per.xxmall.manage.mapper.ItemMapper;
import per.xxmall.manage.pojo.Item;
import per.xxmall.manage.pojo.ItemDesc;
import per.xxmall.manage.pojo.ItemParamItem;

@Service
public class ItemService extends BaseService<Item>{

	@Autowired
	private ItemDescService itemDescService;
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@Value("${FORE_URL}")
	private String FORE_URL;
	
	@Autowired
	private RabbitTemplate itemTemplate;
	
	private static final ObjectMapper MAPPER= new ObjectMapper();
	
	public Boolean itemsave(Item item, String desc, String itemParams) {
		item.setStatus(1);
		item.setId(null);
		int status1 = save(item);
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		int status2 = itemDescService.save(itemDesc);
		ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setItemId(item.getId());
		itemParamItem.setParamData(itemParams);
		Integer status3 = itemParamItemService.save(itemParamItem);
		sendMQ(item.getId(),"insert");
		return status1 == 1 && status2 == 1 && status3 == 1;
	}

	public PageInfo<Item> getItemList(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		Example example = new Example(Item.class);
		example.setOrderByClause("updated DESC");
		List<Item> list = itemMapper.selectByExample(example);
		return new PageInfo<Item>(list);
	}

	public Boolean updatItem(Item item, String desc, String itemParams) {
		item.setCreated(null);
		item.setStatus(null);
		Integer integer = updateSelective(item);
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		Integer integer2 = itemDescService.updateSelective(itemDesc);
		Integer integer3 = itemParamItemService.updateBiItemId(item.getId(),itemParams);
		
		/*try {
			//通知前台更新缓存
			String url = FORE_URL+"/item/cache/"+item.getId()+".html";
			apiService.doPost(url,"");
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		
		//发送消息到前台
		sendMQ(item.getId(),"update");
		
		
		return integer == 1 && integer2 == 1 && integer3 ==1;
	}
	
	public void sendMQ(Long itemId,String type){
		try {
			Map<String,Object> map = new HashMap<>();
			map.put("itemId",itemId);
			map.put("type",type);
			map.put("date",System.currentTimeMillis());
			itemTemplate.convertAndSend("item."+type, MAPPER.writeValueAsString(map));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
