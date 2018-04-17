package per.xxmall.web.service;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import per.xxmall.common.service.APIService;
import per.xxmall.common.service.RedisService;
import per.xxmall.manage.pojo.ItemDesc;
import per.xxmall.web.pojo.Item;

@Service
public class ItemService {
	
	@Autowired
	private APIService apiService;
	
	@Value("${MANAGE_URL}")
	private String MANAGE_URL;
	
	@Autowired
	private RedisService redisService;
	
	public static final String ITEM_KEY="XXMALL_WEB_ITEM_DETAIL_";
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	private static final Integer CACHE_TIME=60*60*24;
	
	public Item queryItemById(Long itemId) {
		
		try {
			//获取缓存
			String cacheData = redisService.get(ITEM_KEY+itemId);
			if(StringUtils.isNotEmpty(cacheData)){
				return MAPPER.readValue(cacheData, Item.class);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String url = MANAGE_URL+"/rest/api/item/"+itemId;
		try {
			String content = apiService.doGet(url);
			//设置缓存
			try {
				redisService.set(ITEM_KEY+itemId, content, CACHE_TIME);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return MAPPER.readValue(content, Item.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ItemDesc queryItemDescById(Long itemId) {
		String url = MANAGE_URL+"/rest/item/desc/"+itemId;
		try {
			String content = apiService.doGet(url);
			return MAPPER.readValue(content, ItemDesc.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
