package per.xxmall.solr.mq.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import per.xxmall.solr.bean.Item;
import per.xxmall.solr.service.ItemService;

public class ItemMQHandler {
	
	private static final ObjectMapper MAPPER= new ObjectMapper();
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private HttpSolrServer httpsolrServer;
	
	public void execute(String msg){
		try {
			JsonNode node = MAPPER.readTree(msg);
			long itemId = node.get("itemId").asLong();
			String type = node.get("type").asText();
			if(StringUtils.equals(type, "update")||StringUtils.equals(type, "insert")){
			Item item = itemService.getItemById(itemId);
			if(item != null){
				httpsolrServer.addBean(item);
				httpsolrServer.commit();
			}
			}else if (StringUtils.equals(type, "delete")){
				httpsolrServer.deleteById(itemId+"");
				httpsolrServer.commit();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
