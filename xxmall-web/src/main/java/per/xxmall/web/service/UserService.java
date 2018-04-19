package per.xxmall.web.service;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import per.xxmall.common.service.RedisService;
import per.xxmall.web.pojo.User;

	
@Service
public class UserService {

	@Autowired
	private RedisService redisService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Value("${SSO_URL}")
	public String SSO_URL;

	public User queryUser(String token) {
		try {
			String user = redisService.get(token);
			if(StringUtils.isNotEmpty(user)) {
				return MAPPER.readValue(user, User.class);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
