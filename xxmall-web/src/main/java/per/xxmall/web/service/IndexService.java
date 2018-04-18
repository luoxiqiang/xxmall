package per.xxmall.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import per.xxmall.common.service.APIService;

@Service
public class IndexService {

	@Autowired
	private APIService apiService;
	
	@Value("${MANAGE_URL}")
	private String MANAGE_URL;
	
	@Value("${SROLLAD_PARAM}")
	private String SROLLAD_PARAM;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public String getRollAd() {
		String uri = MANAGE_URL+SROLLAD_PARAM;
		try {
			String doGet = apiService.doGet(uri);
			if(doGet == null ||doGet.isEmpty())
				return null;
			JsonNode jsonNode = MAPPER.readTree(doGet);
			ArrayNode arrayNode = (ArrayNode)jsonNode.get("rows");
			List<Map<String,Object>> list = new ArrayList<>();
			for(JsonNode node:arrayNode){
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put("srCB", node.get("pic").asText());
				map.put("height", 240);
				map.put("alt", node.get("title").asText());
				map.put("width", 670);
				map.put("src", node.get("pic").asText());
				map.put("widthB", 550);
				map.put("href", node.get("url").asText());
				map.put("heightB", 240);
				list.add(map);
			}
			return MAPPER.writeValueAsString(list);
		} catch (IOException e) {
		}
		return null;
	}

}
