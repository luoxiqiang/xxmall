package per.xxmall.web.mq.handler;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import per.xxmall.common.service.RedisService;
import per.xxmall.web.service.ItemService;

public class ItemMQHandler {
	
	private static final ObjectMapper MAPPER= new ObjectMapper();
	
	@Autowired
	private RedisService redisService;
	//执行删除前台redis缓存
	public void execute(String msg){
		try {
			JsonNode node = MAPPER.readTree(msg);
			long itemId = node.get("itemId").asLong();
			String key = ItemService.ITEM_KEY+itemId;
			redisService.del(key);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
