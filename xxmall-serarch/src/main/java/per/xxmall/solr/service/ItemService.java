package per.xxmall.solr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import per.xxmall.common.service.APIService;
import per.xxmall.solr.bean.Item;

@Service
public class ItemService {

	@Autowired
	private APIService apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Value("${MANAGE_URL}")
	public String MANAGE_URL;
	public Item getItemById(Long itemId){
		try {
			String url = MANAGE_URL+"/rest/item/"+itemId;
			String jsonData = apiService.doGet(url);
			Item item = MAPPER.readValue(jsonData,Item.class);
			if(item != null){
				return item;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
