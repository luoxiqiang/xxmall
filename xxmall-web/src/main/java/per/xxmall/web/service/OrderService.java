package per.xxmall.web.service;

import java.io.IOException;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import per.xxmall.common.httpclient.HttpResult;
import per.xxmall.common.service.APIService;
import per.xxmall.web.pojo.Order;

@Service
public class OrderService {
	
	@Autowired
	private APIService apiService;
	
	public static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Value("${ORDER_URL}")
	private String ORDER_URL;
	
	public String createOrder(Order order) {
		String url = ORDER_URL+"/order/create";
		try {
			HttpResult result = apiService.doPost(url, MAPPER.writeValueAsString(order));
			if(result.getCode().intValue() == 200){
				JsonNode jsonNode = MAPPER.readTree(result.getData());
				if(jsonNode.get("status").intValue() == 200){
					return jsonNode.get("data").asText();
				}
			}
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
