package per.xxmall.cart.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import per.xxmall.cart.bean.Item;
import per.xxmall.common.service.APIService;

@Service
public class ItemService {
	
	@Autowired
	private APIService apiService;
	
	@Value("${MANAGE_URL}")
	private String MANAGE_URL;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public Item queryItemById(Long itemId) {
		
		String url = MANAGE_URL+"/rest/api/item/"+itemId;
		try {
			String content = apiService.doGet(url);
		
			return MAPPER.readValue(content, Item.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



}
